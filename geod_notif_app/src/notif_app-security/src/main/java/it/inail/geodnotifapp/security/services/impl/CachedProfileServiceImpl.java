package it.inail.geodnotifapp.security.services.impl;

import it.inail.geodnotifapp.security.models.Profile;
import it.inail.geodnotifapp.security.repositories.ProfileRepository;
import it.inail.geodnotifapp.security.services.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Classe Service che implementa la chiamata HTTP al servizio di profilazione per recuperare i dati relativi alle sedi degli utenti.
 * Tale servizio utilizza una cache distribuita (REDIS) per ottimizzare il numero di chiamate al servizio target.
 */
@Service
@ConditionalOnProperty(prefix = "inail.cache", name = "redisConfiguration.enabled", havingValue = "true")
public class CachedProfileServiceImpl extends AbstractProfileService implements ProfileService
{

    private static final Logger log = LoggerFactory.getLogger(CachedProfileServiceImpl.class);

    private ProfileRepository profileRepository;

    public CachedProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
        log.info("Cached ProfileService is started");
    }

    protected void storeValue(String key, boolean enabled, Date expirationDate) {
        Profile profile = new Profile(key, enabled, expirationDate);
        profileRepository.save(profile);
    }

    protected Profile getProfile(String jti, String role, String office) {
        String key = getKey(jti, role, office);
        return profileRepository.findById(key).orElse(null);
    }
}
