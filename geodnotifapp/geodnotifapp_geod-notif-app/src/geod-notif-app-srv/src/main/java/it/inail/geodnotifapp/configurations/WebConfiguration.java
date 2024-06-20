package it.inail.geodnotifapp.configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Classe di configurazione per il componente web. Lo scopo della classe è solo definire il package di base utilizzato
 * da Spring per ricercare i servizi e componenti del sistema.
 */
@ComponentScan(basePackages = "it.inail.geodnotifapp")
@Configuration
public class WebConfiguration {

	public WebConfiguration() {
		super();
	}

}
