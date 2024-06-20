package it.inail.geodnotifapp.security.utils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class DummyHostnameVerifier implements HostnameVerifier {

    @Override
    public boolean verify(String hostname, SSLSession sslSession) {
        return true;
    }

}