package it.inail.geodnotifapp.configurations;

import it.inail.geodnotifapp.queue.EventConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.ConnectionFactory;

/**
 * Classe di configurazione per la connessione del message broker
 */
@Configuration
@EnableJms
public class MessagingConfig {
  @Autowired
  private ConnectionFactory connectionFactory;

  @Value("${spring.artemis.embedded.queues}")
  private String queue;

  @Autowired
  private EventConsumer eventConsumer;
  public MessagingConfig() {
	super();
  }

  @Bean
  public DefaultMessageListenerContainer messageListener() {
    DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
    container.setConnectionFactory(this.connectionFactory);
    container.setDestinationName(queue);
    container.setMessageListener(eventConsumer);
    return container;
  }
}