package it.inail.geodnotifapp.security.services.impl;

import it.inail.geodnotifapp.security.configuration.AuthorizationProperties;
import it.inail.geodnotifapp.security.dto.Attributo;
import it.inail.geodnotifapp.security.dto.GetAttributiRequest;
import it.inail.geodnotifapp.security.dto.GetAttributiResponse;
import it.inail.geodnotifapp.security.dto.IstanzeUtente;
import it.inail.geodnotifapp.security.models.Profile;
import it.inail.geodnotifapp.security.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public abstract class AbstractProfileService implements ProfileService {

    private final static int DEFAULT_EXPIRATION_TIME = 2; //2 hours

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AuthorizationProperties authorizationProperties;

    @Override
    public boolean checkUserProfile(String jti, String role, String office, Date expirationTime) {

        Profile profile = getProfile(jti, role, office);

        if (profile != null && !profile.isExpired()) {
            return profile.getValue();
        }

        Date expirationDate = getExpireDate(expirationTime);

        List<String> headOffices = getHeadOffices(role);
        for (String headOffice : headOffices) {
            if (headOffice.equals(office)) {
                String key = getKey(jti, role, office);
                storeValue(key, true, expirationDate);
                return true;
            }
        }
        storeValue(getKey(jti, role, office), false, expirationDate);
        return false;

    }

    protected abstract Profile getProfile(String jti, String role, String office);

    protected abstract void storeValue(String key, boolean enabled, Date expirationDate);

    protected List<String> getHeadOffices(String role) {

        List<String> headOffices = new ArrayList<>();

        GetAttributiRequest request = new GetAttributiRequest(role);

        GetAttributiResponse response = restTemplate.postForObject(
                authorizationProperties.getGetAttributiBEUrl(),
                request,
                GetAttributiResponse.class);

        if (response != null && response.getIstanzeUtente() != null) {
            for (IstanzeUtente istanzeUtente : response.getIstanzeUtente()) {
                for (Attributo attributo : istanzeUtente.getValoriAttributo()) {
                    headOffices.add(attributo.getValore());
                }
            }
        }
        return headOffices;
    }

    protected Date getExpireDate(Date jwtExpirationTime) {
        if (jwtExpirationTime != null) return jwtExpirationTime;
        Calendar exp = Calendar.getInstance();
        exp.add(Calendar.HOUR, DEFAULT_EXPIRATION_TIME);
        return exp.getTime();
    }

    protected String getKey(String jti, String role, String office) {
        return String.format("%s-%s-%s", jti, role, office);
    }
}
