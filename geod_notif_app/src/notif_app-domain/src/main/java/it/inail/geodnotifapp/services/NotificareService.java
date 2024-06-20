package it.inail.geodnotifapp.services;

import org.springframework.http.ResponseEntity;

public interface NotificareService {
    ResponseEntity<Void> elaborazioneGenerale();
}
