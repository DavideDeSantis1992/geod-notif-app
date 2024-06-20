package it.inail.geodnotifapp.repositories;

import it.inail.geodnotifapp.models.Storico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoricoRepository extends JpaRepository<Storico, Long> {

    List<Storico> findByPraticaId(long praticaId);
}
