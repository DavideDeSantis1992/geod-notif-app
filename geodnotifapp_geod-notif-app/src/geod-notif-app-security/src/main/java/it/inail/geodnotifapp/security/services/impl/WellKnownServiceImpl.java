package it.inail.geodnotifapp.security.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.inail.geodnotifapp.security.configuration.AuthorizationProperties;
// import it.inail.geodnotifapp.security.configuration.SSLProperties;
import it.inail.geodnotifapp.security.dto.B2BConfiguration;
import it.inail.geodnotifapp.security.dto.WellknownEndpoint;
import it.inail.geodnotifapp.security.exceptions.ConfigurationException;
import it.inail.geodnotifapp.security.services.WellknownService;
import it.inail.geodnotifapp.security.utils.HttpUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * Classe Service che implementa la chiamata alla URL di Wellknown del Gateway
 * per recuperare le url e le informazioni di sicurezza. I dati recuperati
 * vengono inseriti in cache locale in modo che la chiamata sia effettuata una
 * sola volta.
 */
@Service
public class WellKnownServiceImpl implements WellknownService {

	private static final Logger log = LoggerFactory.getLogger(WellKnownServiceImpl.class);

	@Autowired
	private AuthorizationProperties authorizationProperties;

//    @Autowired
//    private SSLProperties sslProperties;

	@Autowired
	private ObjectMapper objectMapper;

	private volatile WellknownEndpoint wellknownEndpoint;

	private volatile B2BConfiguration b2BConfiguration;

	@Override
	public WellknownEndpoint getWellKnownUrls() {

		try {

			if (wellknownEndpoint == null) {
				synchronized (this) {
					wellknownEndpoint = getWellknownEndpoint0();
				}
			}
			return wellknownEndpoint;

		} catch (IOException | KeyManagementException | NoSuchAlgorithmException e) {
			log.error(e.getMessage(), e);
			throw new ConfigurationException(e.getMessage(), e);
		}
	}

	private WellknownEndpoint getWellknownEndpoint0()
			throws IOException, KeyManagementException, NoSuchAlgorithmException {

		URL url = new URL(authorizationProperties.getWellKnownUrl());

		HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

		urlConnection.connect();
		HttpUtils.setDummyTLS(urlConnection);
		WellknownEndpoint wellknownEndpoint = null;
		try (InputStream inputStream = urlConnection.getInputStream()) {
			wellknownEndpoint = objectMapper.readValue(inputStream, WellknownEndpoint.class);
		}

		return wellknownEndpoint;
	}

	@Override
	public B2BConfiguration getB2BConfiguration() throws ConfigurationException {

		try {
			if (b2BConfiguration == null) {
				synchronized (this) {
					b2BConfiguration = getB2BConfiguration0();
				}
			}
			return b2BConfiguration;

		} catch (IOException | KeyManagementException | NoSuchAlgorithmException e) {
			log.error(e.getMessage(), e);
			throw new ConfigurationException(e.getMessage(), e);
		}
	}

	private B2BConfiguration getB2BConfiguration0()
			throws IOException, KeyManagementException, NoSuchAlgorithmException {

		WellknownEndpoint wellknownEndopoint = getWellKnownUrls();

		URL url = new URL(wellknownEndopoint.getB2bEndpointUrl());

		HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
		HttpUtils.setDummyTLS(urlConnection);
		urlConnection.connect();

		B2BConfiguration b2BConfiguration = null;
		try (InputStream inputStream = urlConnection.getInputStream()) {
			b2BConfiguration = objectMapper.readValue(inputStream, B2BConfiguration.class);
		}

		return b2BConfiguration;
	}
}
