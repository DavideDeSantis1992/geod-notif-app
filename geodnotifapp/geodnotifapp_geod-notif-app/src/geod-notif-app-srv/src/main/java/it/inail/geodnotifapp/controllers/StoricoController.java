package it.inail.geodnotifapp.controllers;

import it.inail.geodnotifapp.dto.StoricoDto;
import it.inail.geodnotifapp.services.StoricoService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.constraints.NotNull;
import java.util.List;

@Tag(name="storico")
@RestController
@RequestMapping("/storico")
public class StoricoController {

    private final StoricoService storicoService;

    public StoricoController(StoricoService storicoService) {
        this.storicoService = storicoService;
    }
    
	@Operation(summary = "Recupera lo storico di una Pratica", description = "Get Storico", tags = { "getStorico" })
    @GetMapping(value= "/{praticaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StoricoDto>> getStorico(@PathVariable("praticaId") @NotNull Long praticaId) {

        return ResponseEntity.ok(storicoService.getStorico(praticaId));
    }
}
