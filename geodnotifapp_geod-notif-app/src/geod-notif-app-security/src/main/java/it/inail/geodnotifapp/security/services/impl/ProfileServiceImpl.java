package it.inail.geodnotifapp.security.services.impl;

import it.inail.geodnotifapp.security.models.Profile;
import it.inail.geodnotifapp.security.services.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Classe Service che implementa la chiamata HTTP al servizio di profilazione per recuperare i dati relativi alle sedi degli utenti.
 * Tale servizio utilizza una cache locale per ottimizzare il numero di chiamate al servizio target.
 * La cache viene cancellata ogni ora, ma il valore è modificabile nel metodo <metodo>it.inail.geodnotifapp.security.services.impl.clearCache</metodo>.
 */
@Service
@ConditionalOnProperty(prefix = "inail.cache", name = "redisConfiguration.enabled", havingValue = "true", matchIfMissing = true)
public class ProfileServiceImpl extends AbstractProfileService implements ProfileService {

    private static final Logger log = LoggerFactory.getLogger(ProfileServiceImpl.class);

    private ConcurrentHashMap<String, Profile> profiles;

    public ProfileServiceImpl() {
        this.profiles = new ConcurrentHashMap<>();
        log.info("Simple ProfileService is started");
    }

    protected void storeValue(String key, boolean enabled, Date expirationDate) {
        profiles.put(key, new Profile(key, true, expirationDate));
    }

    protected Profile getProfile(String jti, String role, String office) {
        return profiles.get(getKey(jti, role, office));
    }


    @Scheduled(fixedRate = 30 * 1000 * 60) //every hour
    protected void clearCache() {

        profiles.forEachValue(1, profile -> {
            if (profile.isExpired()) {
                profiles.remove(profile.getKey());
            }
        });

        log.debug("Cache headOffices cleared");
    }
}
