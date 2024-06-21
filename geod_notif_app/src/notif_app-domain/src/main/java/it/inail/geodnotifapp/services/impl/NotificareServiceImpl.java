package it.inail.geodnotifapp.services.impl;

import it.inail.geodnotifapp.external.GeodDatiAppClient;
import it.inail.geodnotifapp.external.SmtpClient;
import it.inail.geodnotifapp.models.Email;
import it.inail.geodnotifapp.models.Notificare;
import it.inail.geodnotifapp.services.NotificareService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class NotificareServiceImpl implements NotificareService {

    @Autowired
    private Environment environment;

    private Logger logger = LoggerFactory.getLogger(NotificareServiceImpl.class);

    @Autowired
    private GeodDatiAppClient geodDatiAppClient;

    @Autowired
    private SmtpClient smtpClient;

    private void listaProcessiDaElaborare() {
        try {
            logger.info("INIZIO PROCESSO----");
            List<Notificare> lista = geodDatiAppClient.listaProcessi();
            logger.info("Lista dei processi recuperata: " + lista);

            for (Notificare not : lista) {
                String artifact = not.getIdIstanzaProcesso();
                String frequenza = not.getIdFrequenzaCd().getDescrizione().toLowerCase();
                String tipo = not.getIdTipoCd().getDescrizione().toLowerCase();
                logger.info("Artifact: " + artifact);
                logger.info("Frequenza: " + frequenza);
                logger.info("Tipo: " + tipo);

                String oggettoEmail = environment.getProperty("oggetto-email-successo");

                String bodyEmail = environment.getProperty("body-email-successo") + ", abbiamo notificato il completamento del processo " + artifact +
                        " con frequenza " + frequenza + " e tipo " + tipo;

                List<String> destinatari = new ArrayList<>();
                switch (frequenza) {
                    case "mensile":
                        switch (tipo) {
                            case "infortunio":
                                String gruppo1 = environment.getProperty("email-groups.gruppo1");
                                if (gruppo1 != null) {
                                    logger.info("Gruppo 1: " + gruppo1);
                                    destinatari.addAll(Arrays.asList(gruppo1.split(",\\s*")));
                                } else {
                                    logger.warn("Gruppo 1 non configurato correttamente nel file YAML");
                                }
                                break;
                            case "malattia":
                                String gruppo3 = environment.getProperty("email-groups.gruppo3");
                                if (gruppo3 != null) {
                                    logger.info("Gruppo 3: " + gruppo3);
                                    destinatari.addAll(Arrays.asList(gruppo3.split(",\\s*")));
                                } else {
                                    logger.warn("Gruppo 3 non configurato correttamente nel file YAML");
                                }
                                break;
                        }
                        break;
                    case "semestrale":
                        switch (tipo) {
                            case "infortunio":
                                String gruppo2 = environment.getProperty("email-groups.gruppo2");
                                if (gruppo2 != null) {
                                    logger.info("Gruppo 2: " + gruppo2);
                                    destinatari.addAll(Arrays.asList(gruppo2.split(",\\s*")));
                                } else {
                                    logger.warn("Gruppo 2 non configurato correttamente nel file YAML");
                                }
                                break;
                            case "malattia":
                                String gruppo4 = environment.getProperty("email-groups.gruppo4");
                                if (gruppo4 != null) {
                                    logger.info("Gruppo 4: " + gruppo4);
                                    destinatari.addAll(Arrays.asList(gruppo4.split(",\\s*")));
                                } else {
                                    logger.warn("Gruppo 4 non configurato correttamente nel file YAML");
                                }
                                break;
                        }
                        break;
                }

                logger.info("Destinatari: " + destinatari);

// Verifica se ci sono destinatari prima di inviare l'email
                if (!destinatari.isEmpty()) {
                    Email email = new Email(destinatari, oggettoEmail, bodyEmail);
                    try {
                        smtpClient.sendEmail(email);
                        logger.info("Email inviata con successo: " + email);
                    } catch (Exception e) {
                        logger.error("Errore durante l'invio dell'email", e);
                    }
                } else {
                    logger.warn("Nessun destinatario trovato per inviare l'email");
                }


                logger.info("PROCESSO TERMINATO----");
            }
        } catch (Exception err) {
            logger.error("Errore durante l'elaborazione dei processi", err);
        }
    }

    @Override
    public ResponseEntity<Void> elaborazioneGenerale() {
        listaProcessiDaElaborare();
        return null;
    }
}




