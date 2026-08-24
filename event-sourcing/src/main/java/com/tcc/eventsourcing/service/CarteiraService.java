package com.tcc.eventsourcing.service;

import com.tcc.eventsourcing.dto.CarteiraView;
import com.tcc.eventsourcing.modelo.Carteira;
import com.tcc.eventsourcing.modelo.Cliente;
import com.tcc.eventsourcing.modelo.Evento;
import com.tcc.eventsourcing.modelo.TipoEvento;
import com.tcc.eventsourcing.modelo.dao.CarteiraRepository;
import com.tcc.eventsourcing.modelo.dao.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CarteiraService {

    @Autowired
    private CarteiraRepository carteiraRepository;

    @Autowired
    private EventoRepository eventoRepository;

    public Carteira criarCarteira(Cliente cliente) {
        Carteira carteira = new Carteira();
        carteira.setCliente(cliente);
        return carteiraRepository.save(carteira);
    }

    public Carteira getCarteira(Long id) {
        return carteiraRepository.findById(id).orElseThrow();
    }

    public CarteiraView projetarCarteira(Long carteiraId) {
        carteiraRepository.findById(carteiraId).orElseThrow();

        List<Evento> eventos = eventoRepository.findByCarteiraIdOrderBySequenciaAsc(carteiraId);

        BigDecimal saldo = eventos.stream()
                .map(e -> e.getTipo() == TipoEvento.DEPOSITO
                        ? e.getValor()
                        : e.getValor().negate())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CarteiraView(carteiraId, saldo, eventos.size(), eventos);
    }
}

