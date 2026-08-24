package com.tcc.eventsourcing.modelo.dao;

import com.tcc.eventsourcing.modelo.Evento;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends CrudRepository<Evento, Long> {

    @Query("SELECT MAX(sequencia) FROM tabela_eventos WHERE conta_id = :contaId;")
    Integer findMaxSequencia(Long contaId);
}
