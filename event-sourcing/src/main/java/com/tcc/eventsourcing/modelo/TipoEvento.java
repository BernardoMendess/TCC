package com.tcc.eventsourcing.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoEvento {
    SAQUE("Saque"),
    DEPOSITO("Depósito");

    private final String descricao;
}

