package it.inail.geodnotifapp.controllers;

import it.inail.geodnotifapp.dto.MessageDto;
import it.inail.geodnotifapp.services.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Semplice controller di esempio che implementa un endpoint che restituisce un messaggio di testo.
 */
@Tag(name="message")
@Controller
@RequestMapping(path = "/message")
public class MessageController {

	private static final Logger log = LoggerFactory.getLogger(MessageController.class);

    private MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Operation(summary = "Recupera un messaggio da una coda", description = "Get Message", tags = { "getMessage" })
    @GetMapping
    @ResponseBody
    public ResponseEntity<MessageDto> getMessage() {
        log.debug("GetMessage");
        return ResponseEntity.ok(messageService.getMessage());
    }
}
