package it.inail.geodnotifapp.services.impl;

import it.inail.geodnotifapp.dto.StoricoDto;
import it.inail.geodnotifapp.mappers.StoricoMapper;
import it.inail.geodnotifapp.models.Storico;
import it.inail.geodnotifapp.repositories.StoricoRepository;
import it.inail.geodnotifapp.services.StoricoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class StoricoServiceImpl implements StoricoService {

    @Autowired
    private StoricoRepository storicoRepository;

    @Autowired
    private StoricoMapper storicoMapper;

    @Transactional
    public void save(long praticaId, String description, String username) {

        Storico storico = new Storico();
        storico.setPraticaId(praticaId);
        storico.setDescription(description);
        storico.setCreationDate(new Date());
        storico.setUsername(username);
        storicoRepository.save(storico);
    }

    @Override
    public List<StoricoDto> getStorico(long praticaId) {

        List<Storico> praticheList = storicoRepository.findByPraticaId(praticaId);

        return storicoMapper.map(praticheList);
    }
}
