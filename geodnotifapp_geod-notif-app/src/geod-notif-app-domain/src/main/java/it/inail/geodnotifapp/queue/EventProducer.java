package it.inail.geodnotifapp.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import it.inail.geodnotifapp.dto.StoricoMessage;

/**
 * Classe che implementa un Producer di messaggi utilizzando una coda del broker.
 */
@Component
public class EventProducer {

    private static final Logger log = LoggerFactory.getLogger(EventConsumer.class);

    @Value("${spring.artemis.embedded.queues}")
    private String queue;

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMessage(long praticaId, String description, String username) {
        StoricoMessage storicoMessage = new StoricoMessage(praticaId, description, username);
        jmsTemplate.convertAndSend(queue, storicoMessage);
        log.info("message sent to queue.");
    }
}