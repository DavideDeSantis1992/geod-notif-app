package it.inail.geodnotifapp.controllers;

import it.inail.geodnotifapp.services.NotificareService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

public class NotificareController {
    @Autowired
    private NotificareService notificareService;
    private Logger logger = LoggerFactory.getLogger(NotificareService.class);
    @PostMapping("/notificare")
    public ResponseEntity<Void> notificare(){
        try{
            logger.info("Notifica ricevuta");// TODO CHIAMARE SERVICE
            notificareService.elaborazioneGenerale();
            return ResponseEntity.ok().build();
        }catch(Exception err){
            logger.error(err.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
