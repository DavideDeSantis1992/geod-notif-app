package it.inail.geodnotifapp.security.client;

import it.inail.geodnotifapp.security.configuration.AuthorizationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.web.client.RestTemplate;

/*

 */
public class AuthorizedRestTemplateCustomizer implements RestTemplateCustomizer {

    @Autowired
    private AuthorizationProperties authorizationProperties;

    @Override
    public void customize(RestTemplate restTemplate) {
        restTemplate.getInterceptors().add(new AuthorizedClientHttpRequestInterceptor(authorizationProperties));
    }
}