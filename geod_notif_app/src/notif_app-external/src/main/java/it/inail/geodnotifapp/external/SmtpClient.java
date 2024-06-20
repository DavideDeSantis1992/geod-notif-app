package it.inail.geodnotifapp.external;
import it.inail.geodnotifapp.models.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

@Component
public class SmtpClient {

    private Logger logger = LoggerFactory.getLogger(SmtpClient.class);

    public Email sendEmail(Email email){
        String url = "url-di-invio-email-per-SMTP";
        logger.info("ho preso l'url:"+url);

        Email emailDaPassare = new Email();
        emailDaPassare.setDestinatari(email.getDestinatari());
        emailDaPassare.setOggetto(email.getOggetto());
        emailDaPassare.setBody(email.getBody());
        logger.info("ho preso l'email da passare:"+emailDaPassare);
        return emailDaPassare;
    }
}
