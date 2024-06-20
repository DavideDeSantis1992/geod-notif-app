package it.inail.geodnotifapp.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe di configurazione per lo swagger in formato OpenAPI 3
 */
@Configuration
public class SwaggerConfiguration {

    @Value(value = "${swagger.enable: true}")
    private boolean enableSwagger;

    @Value("${swagger.server.url: ''}")
    private String serverUrl;

    @Value(value = "${swagger.doc.title:notif_app}")
    private String docTitle;

    @Value(value = "${swagger.doc.description:notif_app}")
    private String docDescription;

    @Value(value = "${swagger.doc.version:1.0.2}")
    private String docVersion;

    public SwaggerConfiguration() {
    	super();
    }
    @Bean
    public OpenAPI openAPI() {
		return new OpenAPI()
				.info(new Info()
						.contact(createContact())
						.title(docTitle != null ? docTitle : "")
						.termsOfService("https://wwwinail.it/terms/")
						.description(docDescription != null ? docDescription : "")
						.version(docVersion != null ? docVersion : "")
						.extensions(createInfoExtensions())
				);
    }
	
	private Contact createContact() {

		Contact contact = new Contact();
		contact.name("INAIL");
		contact.url("https://www.inail.it");
		contact.email("info@inail.it");

		return contact;
	};

	private Map<String, Object> createInfoExtensions() {
		String INFO_EXTRA_SUMMARY_KEY = "x-summary";
		String INFO_EXTRA_X_API_ID_KEY = "x-api-id";

		Map<String, Object> extensions = new HashMap<>();
		extensions.put(INFO_EXTRA_SUMMARY_KEY, "BluePrint INAIL for Spring Boot");
		extensions.put(INFO_EXTRA_X_API_ID_KEY, "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx");

		return extensions;
	};
}
