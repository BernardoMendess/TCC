package com.tcc.eventsourcing.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventoDTO {
    private BigDecimal valor;
    private String tipo;
    private Long carteiraId;
}
