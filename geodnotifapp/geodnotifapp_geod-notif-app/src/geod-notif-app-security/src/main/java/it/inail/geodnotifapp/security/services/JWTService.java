package it.inail.geodnotifapp.security.services;

import it.inail.geodnotifapp.security.dto.JWTResponse;

public interface JWTService {

    JWTResponse createJWT(String username);

    String getAccessToken();

}
