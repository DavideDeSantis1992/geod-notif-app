package it.inail.geodnotifapp.security.utils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class DummyX509TrustManager implements X509TrustManager {

    private static final TrustManager[] TRUST_MANAGERS = new TrustManager[]{new DummyX509TrustManager()};


    private static final X509Certificate[] X_509_CERTIFICATES = new X509Certificate[]{};


    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    public boolean isClientTrusted(X509Certificate[] chain) {
        return true;
    }

    public boolean isServerTrusted(X509Certificate[] chain) {
        return true;
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return X_509_CERTIFICATES;
    }

    public static void allowAllSSL() throws NoSuchAlgorithmException, KeyManagementException {
        HttpsURLConnection.setDefaultHostnameVerifier(new DummyHostnameVerifier());

        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, TRUST_MANAGERS, new SecureRandom());

        HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
    }
}
