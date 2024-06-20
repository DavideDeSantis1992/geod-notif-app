package it.inail.geodnotifapp.security.services;

import java.util.Date;

public interface ProfileService {

    public boolean checkUserProfile(String jti, String role, String office, Date expirationTime);
}
