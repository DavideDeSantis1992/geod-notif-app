package it.inail.geodnotifapp.security.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.inail.geodnotifapp.security.dto.CertificateResponse;
import it.inail.geodnotifapp.security.exceptions.ServiceGenericException;
import it.inail.geodnotifapp.security.services.CertificateService;
import it.inail.geodnotifapp.security.services.WellknownService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Classe Service che implementa la chiamata alla URL del Gateway al fine di scaricare il certificato SSL
 * utile per effettuare chiamate HTTPS B2b. La URL da richiamare è recuperata dagli indirizzi presenti nel wellknown.
 */
@Service
public class CertificateServiceImpl implements CertificateService {

    private static final Logger log = LoggerFactory.getLogger(CertificateServiceImpl.class);

    @Autowired
    private WellknownService wellknownService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public CertificateResponse getCertificate() {

        try {

            URL url = new URL(wellknownService.getB2BConfiguration().getJwksCA());

            HttpsURLConnection urlConnection = (HttpsURLConnection)url.openConnection();

            urlConnection.connect();

            try (InputStream inputStream = urlConnection.getInputStream()) {
                CertificateResponse certificateResponse = objectMapper.readValue(inputStream, CertificateResponse.class);
                return certificateResponse;
            }

        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new ServiceGenericException(e.getMessage(), e);
        }
    }

}
