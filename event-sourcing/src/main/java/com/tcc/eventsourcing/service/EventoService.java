package com.tcc.eventsourcing.service;

import com.tcc.eventsourcing.dto.EventoDTO;
import com.tcc.eventsourcing.modelo.Evento;
import com.tcc.eventsourcing.modelo.TipoEvento;
import com.tcc.eventsourcing.modelo.dao.EventoRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    public Evento save(EventoDTO evento){
        val eventoEntity = new Evento();

        eventoEntity.setCarteiraId(evento.getCarteiraId());
        eventoEntity.setDataHora(LocalDateTime.now());
        eventoEntity.setTipo(TipoEvento.valueOf(evento.getTipo()));
        eventoEntity.setValor(evento.getValor());
        eventoEntity.setSequencia(eventoRepository.findMaxSequencia(eventoEntity.getCarteiraId()));

        return eventoRepository.save(eventoEntity);
    }
}
