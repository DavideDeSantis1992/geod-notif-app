package it.inail.geodnotifapp.security.services;

import it.inail.geodnotifapp.security.dto.B2BConfiguration;
import it.inail.geodnotifapp.security.dto.WellknownEndpoint;
import it.inail.geodnotifapp.security.exceptions.ConfigurationException;

public interface WellknownService {

    WellknownEndpoint getWellKnownUrls();

    B2BConfiguration getB2BConfiguration() throws ConfigurationException;

}
