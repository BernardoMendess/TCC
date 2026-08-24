package com.tcc.eventsourcing.service;

import com.tcc.eventsourcing.dto.EventoDTO;
import com.tcc.eventsourcing.modelo.Evento;
import com.tcc.eventsourcing.modelo.TipoEvento;
import com.tcc.eventsourcing.modelo.dao.EventoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventoServiceTest {

    @Mock
    private EventoRepository eventoRepository;

    @InjectMocks
    private EventoService eventoService;

    @Test
    @DisplayName("Deve gravar evento com sequência incremental e tipo correto")
    void deveGravarEventoComSequenciaCorreta() {
        Long carteiraId = 1L;
        EventoDTO dto = new EventoDTO(new BigDecimal("150.00"), "DEPOSITO", carteiraId);

        when(eventoRepository.findNextSequencia(carteiraId)).thenReturn(1);
        when(eventoRepository.save(any(Evento.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Evento resultado = eventoService.save(dto);

        assertNotNull(resultado);
        assertEquals(new BigDecimal("150.00"), resultado.getValor());
        assertEquals(TipoEvento.DEPOSITO, resultado.getTipo());
        assertEquals(carteiraId, resultado.getCarteiraId());
        assertEquals(1, resultado.getSequencia());
        assertNotNull(resultado.getDataHora());

        ArgumentCaptor<Evento> captor = ArgumentCaptor.forClass(Evento.class);
        verify(eventoRepository).save(captor.capture());
        Evento salvo = captor.getValue();
        assertEquals(1, salvo.getSequencia());
        assertEquals(TipoEvento.DEPOSITO, salvo.getTipo());
    }
}
