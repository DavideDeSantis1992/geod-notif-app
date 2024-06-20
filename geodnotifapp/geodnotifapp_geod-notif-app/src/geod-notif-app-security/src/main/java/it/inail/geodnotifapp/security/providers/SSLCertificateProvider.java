package it.inail.geodnotifapp.security.providers;

import it.inail.geodnotifapp.security.configuration.SSLProperties;
import it.inail.geodnotifapp.security.services.CertificateService;
import it.inail.geodnotifapp.security.dto.CertificateResponse;
import it.inail.geodnotifapp.security.utils.CertUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.stream.Stream;

/**
 * Classe che ha lo scopo di caricare i certificati SSL nel TrustStore corrente.
 * L''ordine di caricamento dei certificati è il seguente:
 *  - carica tutti i certificati di default dal keystore
 *  - carica i certificati dall''Api Gateway
 *  - aggiunge certificati custom presi del keystore esterno
 *  - aggiunge certificati custom presi da una cartella nel sistema
 * Al termine crea un nuovo trust store java e configura SSLContext di default.
 */
@Component("SSLCertificateProvider")
public class SSLCertificateProvider {

    private static final Logger log = LoggerFactory.getLogger(SSLCertificateProvider.class);

    public static final int REFRESH_ORDER = 10;

    private static final int MAX_TOTAL_CONNECTIONS = 50;

    @Autowired
    private SSLProperties sslProperties;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private CertificateService certificateService;

    @Autowired(required = false)
    private List<RestTemplate> restTemplates;

    @EventListener(ContextRefreshedEvent.class)
    @Order(REFRESH_ORDER)
    public void setupCertificate() {
        try {
            doSetupCertificate();
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException | KeyManagementException e) {
            log.error("No certificate has bean loaded: "+ e.getMessage(), e);
            throw new IllegalStateException("No certificate has bean loaded:" +e.getMessage(), e);
        }
    }

    private List<Certificate> getCurrentCertificates() throws NoSuchAlgorithmException, KeyStoreException {
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        List<Certificate> x509Certificates = new ArrayList<>();
        trustManagerFactory.init((KeyStore) null);
        Arrays.asList(trustManagerFactory.getTrustManagers()).stream()
                .forEach(t -> x509Certificates.addAll(Arrays.asList(((X509TrustManager) t).getAcceptedIssuers())));
        return x509Certificates;
    }

    private void loadDefaultCertificate(KeyStore keyStore) throws KeyStoreException, NoSuchAlgorithmException {
        List<Certificate> currentCertificates = getCurrentCertificates();
        for (Certificate certificate : currentCertificates) {
            keyStore.setCertificateEntry(UUID.randomUUID().toString(), certificate); //si potrebbe caricare l'alias giusto al posto del random UUID
        }
        if (log.isDebugEnabled()) {
            log.debug("caricati {} certificati", currentCertificates.size());
            for (Certificate acceptedIssuer : currentCertificates) {
                log.debug("certificato caricato : subject={} issuer={}", ((X509Certificate)acceptedIssuer).getSubjectX500Principal(),
                        ((X509Certificate)acceptedIssuer).getIssuerX500Principal());
            }
        }

    }

    private void loadGatewayCertificate(KeyStore keyStore) throws CertificateException, IOException, KeyStoreException {

        if (sslProperties.getLoadCertificateFromGateway()) {

            CertificateResponse certResponse = certificateService.getCertificate();

            if (certResponse.getCertificateBackeEndBody() != null) {
                X509Certificate certificate = CertUtils.buildCertificateFromString(certResponse.getCertificateBackeEndBody());
                keyStore.setCertificateEntry(UUID.randomUUID().toString(), certificate);
                log.info("caricato certificato per BE {}", certificate.getSubjectX500Principal());
            } else {
                log.warn("nessun certificato di BE recuperato da servizio esposto su Api Gateway");
            }

            if (certResponse.getCertificateTokenBody() != null) {
                X509Certificate certificate = CertUtils.buildCertificateFromString(certResponse.getCertificateTokenBody());
                keyStore.setCertificateEntry(UUID.randomUUID().toString(), certificate);
                log.info("caricato certificato per AS {}", certificate.getSubjectX500Principal());
            } else {
                log.warn("nessun certificato per AS recuperato da servizio esposto su Api Gateway");
            }

            if (certResponse.getCertificateJWT() != null) {
                X509Certificate certificate = CertUtils.buildCertificateFromString(certResponse.getCertificateJWT());
                keyStore.setCertificateEntry(UUID.randomUUID().toString(), certificate);
                log.info("caricato certificato per JWT {}", certificate.getSubjectX500Principal());
            } else {
                log.warn("nessun certificato per JWT recuperato da servizio esposto su Api Gateway");
            }
        }
        else {
            log.warn("caricamento dei certificati da Gateway non eseguito");
        }
    }

    private void loadCustomJksCertificates(KeyStore keyStore) {
        if (sslProperties.getCustomCaCerts().getEnabled() &&
                StringUtils.hasText(sslProperties.getCustomCaCerts().getPath())) {

            Map<String, Certificate> customCerts = getCustomCertificates(sslProperties.getCustomCaCerts().getPath(),
                    sslProperties.getCustomCaCerts().getPassword());
            List<String> loaded = new ArrayList<>(customCerts.size());
            customCerts.forEach((alias, certificato) -> {
                try {
                    keyStore.setCertificateEntry(alias, certificato);
                    loaded.add(alias);
                } catch (KeyStoreException e) {
                    log.error("impossibile caricare il certificato con alias " + alias, e);
                }
            });
            if (log.isDebugEnabled()) {
                log.debug("caricati certificati custom da keystore {} : {}", sslProperties.getCustomCaCerts().getPath(),
                        StringUtils.collectionToCommaDelimitedString(loaded));
            }
        } else {
            log.warn("nessun keystore custom caricato");
        }
    }

    private Map<String, Certificate> getCustomCertificates(String jksPath, String jksPassword) {
        Map<String, Certificate> certificati = new HashMap<>();
        Resource res = null;
        if (jksPath.startsWith("/")) {
            res = resourceLoader.getResource("file://" + jksPath);
        } else {
            res = resourceLoader.getResource(jksPath);
        }
        if (res.exists()) {
            try (InputStream is = res.getInputStream()) {
                KeyStore keyStore = KeyStore.getInstance("JKS");
                keyStore.load(is, jksPassword.toCharArray());
                Enumeration<String> es = keyStore.aliases();
                while (es.hasMoreElements()) {
                    String alias = es.nextElement();
                    Certificate certificato = keyStore.getCertificate(alias);
                    certificati.put(alias, certificato);
                }
            } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
                log.error("impossibile caricare i certificati custom al path " + jksPath, e);
            }
        } else {
            log.error("impossibile caricare la risorsa al path {}", jksPath);
        }
        return certificati;

    }

    private void loadCertificatesFromFolder(KeyStore keyStore) {
        if (sslProperties.getCertsFromFolder().getEnabled() &&
                StringUtils.hasText(sslProperties.getCertsFromFolder().getPath())) {

            Map<String, Certificate> customCerts = getCustomCertifictesFromFolder(
                    sslProperties.getCertsFromFolder().getPath());

            List<String> loaded = new ArrayList<>(customCerts.size());
            customCerts.forEach((alias, certificato) -> {
                try {
                    keyStore.setCertificateEntry(alias, certificato);
                    loaded.add(alias);
                } catch (KeyStoreException e) {
                    log.error("impossibile caricare il certificato nel file {}" + alias, e);
                }
            });
            if (log.isDebugEnabled()) {
                log.debug("caricati certificati custom da cartella {} : {}", sslProperties.getCertsFromFolder().getPath(),
                        StringUtils.collectionToCommaDelimitedString(loaded));
            }
        } else {
            log.warn("nessun keystore custom caricato");
        }
    }

    private Map<String, Certificate> getCustomCertifictesFromFolder(String folderPath) {
        Map<String, Certificate> certificati = new HashMap<>();
        Resource res = null;
        if (folderPath.startsWith("/")) {
            res = resourceLoader.getResource("file://" + folderPath);
        } else {
            res = resourceLoader.getResource(folderPath);
        }
        if (res.exists()) {
            try (Stream<Path> filePathStream = Files.walk(Paths.get(res.getFile().getAbsolutePath()))) {
                filePathStream.forEach(filePath -> {
                    File currentFile = filePath.toFile();
                    if (currentFile.isFile()) {
                        extractCertificateFromFile(certificati, currentFile);
                    }
                });
            } catch (IOException e) {
                log.error("impossibile accedere al path {}", folderPath, e);
            }
        } else {
            log.error("impossibile accedere alla cartella dei certificati {}", folderPath);
        }
        return certificati;

    }

    /**
     * @param certificati
     * @param currentFile
     */
    private void extractCertificateFromFile(Map<String, Certificate> certificati, File currentFile) {
        try (FileInputStream stream = new FileInputStream(currentFile)) {
            X509Certificate currentCertificate = CertUtils.getCertificateFromFile(stream);
            certificati.put(currentFile.getName(), currentCertificate);
        } catch (IOException | CertificateException e) {
            log.error("impossibile leggere il file " + currentFile.getAbsolutePath(), e);
        }
    }


    private void doSetupCertificate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException, CertificateException, IOException {

        log.info("Starting to load certificate...");

        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null);

        //carica tutti i certificati di default dal keystore
        loadDefaultCertificate(keyStore);

        //carica i certificati dall'Api Gateway
        loadGatewayCertificate(keyStore);

        // aggiungi certificati custom presi del keystore esterno
        loadCustomJksCertificates(keyStore);

        // aggiungi certificati custom presi dalla cartella
        loadCertificatesFromFolder(keyStore);

        //crea un nuovo trust store
        String defaultAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory trustManagerFactoryNew = TrustManagerFactory.getInstance(defaultAlgorithm);
        trustManagerFactoryNew.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactoryNew.getTrustManagers();
        //crea un nuovo sslcontext
        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        sslContext.init(null, trustManagers, null);

        //settalo come default
        SSLContext.setDefault(sslContext);
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

        if (log.isDebugEnabled()) {
            X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
            X509Certificate[] acceptedIssuers = trustManager.getAcceptedIssuers();
            for (X509Certificate acceptedIssuer : acceptedIssuers) {
                log.debug("certificato installato : subject={} issuer={}", acceptedIssuer.getSubjectX500Principal(),
                        acceptedIssuer.getIssuerX500Principal());
            }
        }

        log.info("Certificate has been loaded.");

        //update all rest template with security context
        updateRestTemplateInstances(keyStore);

    }

    private void updateRestTemplateInstances(KeyStore keyStore) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        //get default context
        SSLContext sslContext = SSLContext.getDefault();

        //Create new SSLConnectionSocketFactory
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext,
                        SSLConnectionSocketFactory.getDefaultHostnameVerifier());

        //Create new socketFactoryRegistry
        Registry<ConnectionSocketFactory> socketFactoryRegistry =
                RegistryBuilder.<ConnectionSocketFactory> create()
                        .register("https", socketFactory)
                        .register("http", new PlainConnectionSocketFactory())
                        .build();

        PoolingHttpClientConnectionManager connectionManager =
                new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        connectionManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);

        //build httpClient
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(socketFactory)
                .setConnectionManager(connectionManager)
                .build();

        // Aggiorna la request factory di tutti i bean
        for (RestTemplate restTemplate : restTemplates) {
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
        }

    }

}
