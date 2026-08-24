package com.tcc.eventsourcing.modelo;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum TipoEvento {
    SAQUE, DEPOSITO;

    private String descricao;
}
