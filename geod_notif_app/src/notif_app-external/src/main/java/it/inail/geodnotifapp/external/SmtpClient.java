package it.inail.geodnotifapp.external;
import it.inail.geodnotifapp.models.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SmtpClient {

    private Logger logger = LoggerFactory.getLogger(SmtpClient.class);

    public Email sendEmail(Email email){
        String url = "url-di-invio-email-per-SMTP";
        logger.info("ho preso l'url:"+url);
        List<String> destinatari = email.getDestinatari();
        //String destinatari = email.getDestinatari();
        logger.info("ho preso i destinatari:"+destinatari);
        String oggetto = email.getOggetto();
        logger.info("ho preso l'oggetto:"+oggetto);
        String body = email.getBody();
        logger.info("ho preso il body:"+body);

        Email emailDaPassare = new Email(destinatari, oggetto, body);
        logger.info("ho preso l'email da passare:"+emailDaPassare.toString());
        return emailDaPassare;
    }
}
