package it.inail.geodnotifapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.inail.geodnotifapp.dto.AnagraficaDipendente;
import it.inail.geodnotifapp.services.AnagraficaService;

@Tag(name="anagrafiche")
@RestController
@RequestMapping("/anagrafiche")
public class AnagraficaController {

  @Autowired
  AnagraficaService anagraficaService;

  @Operation(summary = "Recupera Le informazioni del dipendente", description = "Get Dipendente", tags = { "getDipendente" })
  @GetMapping("/dipendenti/{matricola}")
  public ResponseEntity<AnagraficaDipendente> getDipendente(@PathVariable("matricola") String matricola) {

    AnagraficaDipendente dipendente = anagraficaService.searchDipendente(matricola);

    return ResponseEntity.ok(dipendente);
  }
}
