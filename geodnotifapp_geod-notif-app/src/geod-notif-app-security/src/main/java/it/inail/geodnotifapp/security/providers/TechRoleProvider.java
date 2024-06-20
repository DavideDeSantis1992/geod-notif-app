package it.inail.geodnotifapp.security.providers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.inail.geodnotifapp.security.configuration.AuthorizationProperties;
import it.inail.geodnotifapp.security.models.TechRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
/**
 * Classe per il caricamento dinamico dei ruoli tecnici.
 * Ogni ruolo tecnico viene mappato su un package del sistema secondo il seguente schema:
 * {
 *   "GROUOID": [
 *     {
 *       "id": "ruolo",
 *       "description": "descrizione ruolo"
 *     }
 *   ]
 * }
 */
public class TechRoleProvider {

    private static final Logger log = LoggerFactory.getLogger(TechRoleProvider.class);

    private static final String TECH_ROLE_FILES = "classpath:tech-roles.json";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private AuthorizationProperties authorizationProperties;

    private Map<String, List<TechRole>> techRoles;

    @EventListener(ApplicationReadyEvent.class)
    public synchronized void loadTechRoles() {
        if (authorizationProperties.getLoadTechnicalRoles()) {
            try {
                if (techRoles == null) {
                    Resource resource = resourceLoader.getResource(TECH_ROLE_FILES);
                    techRoles = objectMapper.readValue(resource.getInputStream(),
                            new TypeReference<Map<String, List<TechRole>>>() {});

                    log.info("Technical roles has been loaded");
                }
            } catch (IOException e) {
                log.error("Technical roles files has not been found: " + e.getMessage(), e);
            }
        } else {
            techRoles = new HashMap<>();
        }
    }

    public Map<String, List<TechRole>> getTechRoles() {
        return techRoles;
    }

    public List<TechRole> getTechRole(String role) {
        if (this.techRoles == null) return new ArrayList<>();
        List<TechRole> techRoles = this.techRoles.get(role);
        if (techRoles == null) techRoles = new ArrayList<>();
        return techRoles;
    }
}
