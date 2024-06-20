package it.inail.geodnotifapp.queue;

import it.inail.geodnotifapp.dto.StoricoMessage;
import it.inail.geodnotifapp.services.StoricoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Classe che implementa un Consumer di esempio riceve un semplice messaggio i cui valori vengono salvati
 * sul database.
 */
@Component
public class EventConsumer implements MessageListener {

    private static final Logger log = LoggerFactory.getLogger(EventConsumer.class);

//    @Autowired
//    private JmsTemplate jmsTemplate;

    @Autowired
    private StoricoService storicoService;

    @Override
    public void onMessage(Message message) {
        try {

            log.info("Message received {} ", message.getJMSMessageID());

            StoricoMessage msg = message.getBody(StoricoMessage.class);

            storicoService.save(msg.getPraticaId(), msg.getDescription(), msg.getUsername());

        } catch (JMSException e) {
           log.error(e.getMessage(), e);
        }
    }

}
