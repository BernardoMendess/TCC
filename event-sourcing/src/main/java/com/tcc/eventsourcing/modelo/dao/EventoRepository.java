package com.tcc.eventsourcing.modelo.dao;

import com.tcc.eventsourcing.modelo.Evento;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoRepository extends CrudRepository<Evento, Long> {

    List<Evento> findByCarteiraIdOrderBySequenciaAsc(Long carteiraId);

    @Query("SELECT COALESCE(MAX(e.sequencia), 0) + 1 FROM Evento e WHERE e.carteiraId = :carteiraId")
    Integer findNextSequencia(@Param("carteiraId") Long carteiraId);
}

