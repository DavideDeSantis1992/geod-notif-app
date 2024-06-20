package it.inail.geodnotifapp.services.impl;

import it.inail.geodnotifapp.external.GeodDatiAppClient;
import it.inail.geodnotifapp.external.SmtpClient;
import it.inail.geodnotifapp.models.Email;
import it.inail.geodnotifapp.models.Notificare;
import it.inail.geodnotifapp.services.NotificareService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificareServiceImpl implements NotificareService {

    @Value("${bodyEmailSuccesso}")
    private String bodyEmailSuccesso;

    @Value("${oggettoEmailSuccesso}")
    private String oggettoEmailSuccesso;

    @Value("${bodyEmailErrore}")
    private String bodyEmailErrore;

    @Value("${oggettoEmailErrore}")
    private String oggettoEmailErrore;

    @Value("${gruppo1}")
    private List<String> gruppo1;

    @Value("${gruppo2}")
    private List<String> gruppo2;

    @Value("${gruppo3}")
    private List<String> gruppo3;

    @Value("${gruppo4}")
    private List<String> gruppo4;

    private Logger logger = LoggerFactory.getLogger(NotificareServiceImpl.class);

    @Autowired
    private GeodDatiAppClient geodDatiAppClient;
    @Autowired
    private SmtpClient smtpClient;

    private void listaProcessiDaElaborare() {
        try {
            logger.info("INIZIO PROCESSO----");
            List<Notificare> lista = geodDatiAppClient.listaProcessi();
            logger.info("ho preso la lista dei processi per elaborarla: " + lista);

            for (Notificare not : lista) {
                String artifact = not.getIdIstanzaProcesso();
                String frequenza = not.getIdFrequenzaCd().getDescrizione().toLowerCase();
                String tipo = not.getIdTipoCd().getDescrizione().toLowerCase();
                logger.info("artifact: " + artifact);
                logger.info("frequenza: " + frequenza);
                logger.info("tipo: " + tipo);

                String oggettoEmail = oggettoEmailSuccesso;
                String bodyEmail = bodyEmailSuccesso + ", abbiamo notificato il completamento del processo " + artifact +
                        " con frequenza " + frequenza + " e tipo " + tipo;

                List<String> destinatari = new ArrayList<>();
                switch (frequenza) {
                    case "mensile":
                        switch (tipo) {
                            case "infortunio":
                                destinatari = gruppo1;
                                break;
                            case "malattia":
                                destinatari = gruppo3;
                                break;
                        }
                        break;
                    case "semestrale":
                        switch (tipo) {
                            case "infortunio":
                                destinatari = gruppo2;
                                break;
                            case "malattia":
                                destinatari = gruppo4;
                                break;
                        }
                        break;
                }
                logger.info("destinatari: " + destinatari);
                Email email = new Email();
                email.setBody(bodyEmail);
                email.setOggetto(oggettoEmail);
                email.setDestinatari(destinatari);

                smtpClient.sendEmail(email);

                logger.info("PROCESSO TERMINATO----");

            }
        } catch (Exception err) {
            logger.error("chiamata client fallita", err);

        }
    }

    @Override
    public ResponseEntity<Void> elaborazioneGenerale() {
        listaProcessiDaElaborare();
        return null;
    }
}

