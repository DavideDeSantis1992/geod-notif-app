package it.inail.geodnotifapp.security.utils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public abstract class HttpUtils {

	private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);
//    private static final int PERMANENT_REDIRECT = 308;

    public static String getError(final HttpURLConnection connection) {
        try (InputStream stream = connection.getErrorStream()) {
            StringBuilder buffer = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
                for (int ch = reader.read(); ch >= 0; ch = reader.read()) {
                    buffer.append((char) ch);
                }
            }
            return buffer.toString();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return e.getMessage();
        }
    }



    public static void setDummyTLS(HttpsURLConnection connection) throws KeyManagementException, NoSuchAlgorithmException {
        setDummyTLS(connection, false, true);
    }

    public static void setDummyTLS(HttpsURLConnection connection, boolean hostnameVerify, boolean trustAll) throws NoSuchAlgorithmException, KeyManagementException {
        if (!hostnameVerify) {
            connection.setHostnameVerifier(new DummyHostnameVerifier());
        }

        if (trustAll) {
            connection.setSSLSocketFactory(getDummySocketFactory());
        }
    }

    private static SSLSocketFactory getDummySocketFactory() throws KeyManagementException, NoSuchAlgorithmException {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[]{ new DummyX509TrustManager() }, new SecureRandom());
        return sslContext.getSocketFactory();
    }

}
