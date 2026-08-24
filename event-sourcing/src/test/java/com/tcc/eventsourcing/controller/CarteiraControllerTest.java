package com.tcc.eventsourcing.controller;

import com.tcc.eventsourcing.dto.CarteiraView;
import com.tcc.eventsourcing.dto.EventoDTO;
import com.tcc.eventsourcing.modelo.Carteira;
import com.tcc.eventsourcing.modelo.Cliente;
import com.tcc.eventsourcing.modelo.Evento;
import com.tcc.eventsourcing.modelo.TipoEvento;
import com.tcc.eventsourcing.service.CarteiraService;
import com.tcc.eventsourcing.service.EventoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarteiraControllerTest {

    @Mock
    private CarteiraService carteiraService;

    @Mock
    private EventoService eventoService;

    @InjectMocks
    private CarteiraController carteiraController;

    @Test
    @DisplayName("registrarEvento deve associar o ID da URL ao DTO e delegar ao EventoService")
    void deveRegistrarEvento() {
        Long carteiraId = 1L;
        EventoDTO dto = new EventoDTO(new BigDecimal("200.00"), "DEPOSITO", null);
        Evento eventoSalvo = new Evento(10L, new BigDecimal("200.00"), TipoEvento.DEPOSITO, LocalDateTime.now(), carteiraId, 1);

        when(eventoService.save(any(EventoDTO.class))).thenReturn(eventoSalvo);

        ResponseEntity<Evento> response = carteiraController.registrarEvento(carteiraId, dto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(eventoSalvo, response.getBody());
        assertEquals(carteiraId, dto.getCarteiraId());
        verify(eventoService).save(dto);
    }

    @Test
    @DisplayName("getCarteiraView deve delegar ao CarteiraService e retornar a projeção")
    void deveRetornarCarteiraView() {
        Long carteiraId = 1L;
        Evento evento = new Evento(10L, new BigDecimal("200.00"), TipoEvento.DEPOSITO, LocalDateTime.now(), carteiraId, 1);
        CarteiraView view = new CarteiraView(carteiraId, new BigDecimal("200.00"), 1, List.of(evento));

        when(carteiraService.projetarCarteira(carteiraId)).thenReturn(view);

        ResponseEntity<CarteiraView> response = carteiraController.getCarteiraView(carteiraId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(view, response.getBody());
        verify(carteiraService).projetarCarteira(carteiraId);
    }

    @Test
    @DisplayName("getCarteira deve retornar os dados da carteira")
    void deveRetornarCarteiraPorId() {
        Long carteiraId = 1L;
        Carteira carteira = new Carteira(carteiraId, new Cliente());

        when(carteiraService.getCarteira(carteiraId)).thenReturn(carteira);

        ResponseEntity<Carteira> response = carteiraController.getCarteira(carteiraId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(carteira, response.getBody());
        verify(carteiraService).getCarteira(carteiraId);
    }
}
