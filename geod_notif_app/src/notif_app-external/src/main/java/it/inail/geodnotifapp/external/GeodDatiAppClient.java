package it.inail.geodnotifapp.external;

import it.inail.geodnotifapp.models.Frequenza;
import it.inail.geodnotifapp.models.Notificare;
import it.inail.geodnotifapp.models.Tipo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GeodDatiAppClient {
    private Logger logger = LoggerFactory.getLogger(GeodDatiAppClient.class);
    public List<Notificare> listaProcessi(){
        String url = "http://localhost:8085/processo";
        logger.info("ho preso l'url:"+url);
        // TODO creo i vari TIPI
        Tipo infortunio = new Tipo();
        infortunio.setId("1");
        infortunio.setDescrizione("infortunio");

        Tipo malattia = new Tipo();
        malattia.setId("2");
        malattia.setDescrizione("malattia");
        // TODO creo le varie FREQUENZE

        Frequenza mensile = new Frequenza();
        mensile.setId("1");
        mensile.setDescrizione("mensile");

        Frequenza semestrale = new Frequenza();
        semestrale.setId("2");
        semestrale.setDescrizione("semestrale");


        // TODO creo nuovi processi di NotificareDTO
        Notificare processo1 = new Notificare();
        processo1.setIdIstanzaProcesso("1");
        processo1.setIdTipoCd(infortunio);
        processo1.setIdFrequenzaCd(mensile);
        processo1.setIdStatoArtifact(null);
        processo1.setIdStatoNotifica(null);

        Notificare processo2 = new Notificare();
        processo2.setIdIstanzaProcesso("2");
        processo2.setIdTipoCd(malattia);
        processo2.setIdFrequenzaCd(semestrale);
        processo2.setIdStatoArtifact(null);
        processo2.setIdStatoNotifica(null);

        List<Notificare> listaProcessi = List.of(processo1, processo2);

        //fai un ciclo sulla listaProcessi e fai   un logger.info per stampare ogni listaProcessi
        for (Notificare not : listaProcessi) {
            logger.info("processo: "+not.toString());
        }

        logger.info("ho preso la lista dei processi dall'endpoint: "+listaProcessi.toString());

        return listaProcessi;
    }
}
