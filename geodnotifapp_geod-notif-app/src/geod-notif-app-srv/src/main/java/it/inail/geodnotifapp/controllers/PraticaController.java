package it.inail.geodnotifapp.controllers;

import it.inail.geodnotifapp.dto.CreatePraticaResponse;
import it.inail.geodnotifapp.dto.PraticaDto;
import it.inail.geodnotifapp.security.utils.AuthenticationUtils;
import it.inail.geodnotifapp.services.PraticaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.Valid;

/**
 * Classe che implementa un controller di esempio esponendo le servizi CRUD sull'entitità pratica.
 */
@Tag(name="pratiche")
@RestController
@RequestMapping("/pratiche")
public class PraticaController {

    private static final Logger log = LoggerFactory.getLogger(PraticaController.class);
	
    private final PraticaService praticaService;

    public PraticaController(PraticaService praticaService) {
        this.praticaService = praticaService;
    }

    @Operation(summary = "Crea una nuova Pratica", description = "Create Pratica", tags = { "create" })
    @PostMapping
    public ResponseEntity<CreatePraticaResponse> create(@RequestBody @Valid PraticaDto praticaDto,
                                                        Authentication auth) {

        /* Recupera il nome utente dal contesto di autenticazione. Il nome utente è il subject del token JWT */
        String username = AuthenticationUtils.getUsername(auth);
		
		/* Esempio di come recuperare i ruoli, il ruolo selezionato e la lista delle Authorities  */
		String selectedRole = AuthenticationUtils.getSelectedRole(auth);
        List<String> userGroupList = AuthenticationUtils.getUserGroups(auth);
        List<String> AuthoritiesList = AuthenticationUtils.getAuthorities(auth);
        log.debug(String.format("UserName: %s", username));
        log.debug(String.format("Ruolo Selezionato: %s", selectedRole));
        log.debug(String.format("UserGroupList: %s", String.join(", ", userGroupList)));
        log.debug(String.format("AuthoritiesList: %s", String.join(", ", AuthoritiesList)));

        CreatePraticaResponse response = praticaService.create(praticaDto, username);
        return ResponseEntity.ok(response);
    }
    
	@Operation(summary = "Ricerca una Pratica dato l'Id della Pratica", description = "Find Pratica", tags = { "find" })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PraticaDto> find(@PathVariable("id") Long id,
                                           Authentication auth) {

        /* Recupera il nome utente dal contesto di autenticazione. Il nome utente è il subject del token JWT */
        String username = AuthenticationUtils.getUsername(auth);
        PraticaDto praticaDto = praticaService.find(id, username);
        return ResponseEntity.ok(praticaDto);
    }

    @Operation(summary = "Aggiorna una Pratica", description = "Update Pratica", tags = { "update" })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                 @RequestBody @Valid PraticaDto praticaDto,
                                 Authentication auth) {

        /* Recupera il nome utente dal contesto di autenticazione. Il nome utente è il subject del token JWT */
        String username = AuthenticationUtils.getUsername(auth);

        praticaService.update(id, praticaDto, username);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Elimina una Pratica dato l'Id della Pratica", description = "Delete Pratica", tags = { "delete" })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id,
                                 Authentication auth) {

        /* Recupera il nome utente dal contesto di autenticazione. Il nome utente è il subject del token JWT */
        String username = AuthenticationUtils.getUsername(auth);

        praticaService.delete(id, username);
        return ResponseEntity.ok().build();
    }

}
