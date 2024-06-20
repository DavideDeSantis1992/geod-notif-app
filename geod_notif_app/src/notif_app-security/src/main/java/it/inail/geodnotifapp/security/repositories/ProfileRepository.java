package it.inail.geodnotifapp.security.repositories;

import it.inail.geodnotifapp.security.models.Profile;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository Redis che consente di salvare e recuperare i profili cachati su Redis.
 * Utilizzata solo se la cache è abilitata
 */
@Repository
@ConditionalOnProperty(prefix = "inail.cache", name = "redisConfiguration.enabled", havingValue = "true")
public interface ProfileRepository extends CrudRepository<Profile, String> {

}
