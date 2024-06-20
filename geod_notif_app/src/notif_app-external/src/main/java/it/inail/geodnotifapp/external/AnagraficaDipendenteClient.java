package it.inail.geodnotifapp.external;

import it.inail.geodnotifapp.dto.AnagraficaDipedentiResponse;
import it.inail.geodnotifapp.dto.DipendenteRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Classe che implementa il client di un servizio esterno al prodotto.
 */
@Service
public class AnagraficaDipendenteClient {

  /**
   * The Constant log.
   */
  private static final Logger log = LoggerFactory.getLogger(AnagraficaDipendenteClient.class);

  /**
   * The base url.
   */
  @Value("${external.base-url}")
  private String BASE_URL;

  /**
   * The base path.
   */
  @Value("${external.base-path}")
  private String BASE_PATH;

  /**
   * The rest template.
   */
  @Autowired
  RestTemplate restTemplate;

  /**
   * Search anagrafiche.
   *
   * @param matricolaDipendente the matricola dipendente
   * @return the anagrafica dipedenti response
   */
  public AnagraficaDipedentiResponse searchAnagrafiche(String matricolaDipendente) {

    String uri = BASE_URL + BASE_PATH;

    DipendenteRequest requestBody = new DipendenteRequest();
    requestBody.setMatricola(matricolaDipendente);

    try {
      ResponseEntity<AnagraficaDipedentiResponse> anagraficaDipendente =
          restTemplate.postForEntity(uri, requestBody, AnagraficaDipedentiResponse.class);
      return anagraficaDipendente.getBody();
    } catch (RestClientException e) {
      log.error(e.getMessage());
      throw new RestClientException("Errore nel recupero dei dati.");
    }
  }
}
