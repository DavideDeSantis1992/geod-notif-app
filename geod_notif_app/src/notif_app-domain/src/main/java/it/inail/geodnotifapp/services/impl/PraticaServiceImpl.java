package it.inail.geodnotifapp.services.impl;

import it.inail.geodnotifapp.queue.EventProducer;
import it.inail.geodnotifapp.dto.CreatePraticaResponse;
import it.inail.geodnotifapp.dto.PraticaDto;
import it.inail.geodnotifapp.exceptions.DataNotFoundException;
import it.inail.geodnotifapp.mappers.PraticaMapper;
import it.inail.geodnotifapp.models.Pratica;
import it.inail.geodnotifapp.repositories.PraticaRepository;
import it.inail.geodnotifapp.services.PraticaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class PraticaServiceImpl implements PraticaService {

    private static final Logger log = LoggerFactory.getLogger(PraticaServiceImpl.class);

    private final PraticaRepository praticaRepository;

    private final PraticaMapper praticaMapper;

    private final EventProducer eventProducer;

    public PraticaServiceImpl(PraticaRepository praticaRepository, PraticaMapper praticaMapper, EventProducer eventProducer) {
        this.praticaRepository = praticaRepository;
        this.praticaMapper = praticaMapper;
        this.eventProducer = eventProducer;
    }

    @Override
    public CreatePraticaResponse create(PraticaDto praticaDto, String username) {

        Pratica pratica = praticaMapper.map(praticaDto);
        pratica.setCreationDate(new Date());
        pratica.setCreator(username);
        pratica = praticaRepository.save(pratica);

        log.info("Pratica {} creata con successo.", pratica.getId());
        eventProducer.sendMessage(pratica.getId(), "Pratica "+pratica.getId()+" creata con successo", username);
        CreatePraticaResponse createPraticaResponse = new CreatePraticaResponse();
        createPraticaResponse.setId(pratica.getId());
        return createPraticaResponse;
    }

    @Override
    public boolean update(Long id, PraticaDto praticaDto, String username) {
        Pratica pratica = praticaRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(String.format("Pratica con id %s non trovata", id)));
        pratica.setName(praticaDto.getName());
        pratica.setDescription(praticaDto.getDescription());
        praticaRepository.save(pratica);

        eventProducer.sendMessage(pratica.getId(), "Pratica "+pratica.getId()+" aggiornata con successo", username);

        return true;
    }

    @Override
    public void delete(Long id, String username) {
        Pratica pratica = praticaRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(String.format("Pratica con id %s non trovata", id)));
        praticaRepository.delete(pratica);

        eventProducer.sendMessage(pratica.getId(), "Pratica "+pratica.getId()+" cancellata con successo", username);
    }

    @Override
    public PraticaDto find(Long id, String username) {
        Pratica pratica = praticaRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(String.format("Pratica con id %s non trovata", id)));
        PraticaDto praticaDto = praticaMapper.map(pratica);
        return praticaDto;
    }
}
