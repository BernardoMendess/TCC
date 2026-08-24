package com.tcc.eventsourcing.dto;

import com.tcc.eventsourcing.modelo.Evento;

import java.math.BigDecimal;
import java.util.List;

public record CarteiraView(
        Long carteiraId,
        BigDecimal saldoAtual,
        int totalEventos,
        List<Evento> historico
) {}
