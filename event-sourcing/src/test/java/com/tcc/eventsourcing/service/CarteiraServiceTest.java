package com.tcc.eventsourcing.service;

import com.tcc.eventsourcing.dto.CarteiraView;
import com.tcc.eventsourcing.modelo.Carteira;
import com.tcc.eventsourcing.modelo.Cliente;
import com.tcc.eventsourcing.modelo.Evento;
import com.tcc.eventsourcing.modelo.TipoEvento;
import com.tcc.eventsourcing.modelo.dao.CarteiraRepository;
import com.tcc.eventsourcing.modelo.dao.EventoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarteiraServiceTest {

    @Mock
    private CarteiraRepository carteiraRepository;

    @Mock
    private EventoRepository eventoRepository;

    @InjectMocks
    private CarteiraService carteiraService;

    @Test
    @DisplayName("Deve criar carteira vinculada ao cliente sem saldo inicial gravado")
    void deveCriarCarteiraSemSaldoInicial() {
        Cliente cliente = new Cliente(1L, "João Silva", "12345678900", "joao@email.com", null);
        Carteira carteiraSalva = new Carteira(10L, cliente);

        when(carteiraRepository.save(any(Carteira.class))).thenReturn(carteiraSalva);

        Carteira resultado = carteiraService.criarCarteira(cliente);

        assertNotNull(resultado);
        assertEquals(10L, resultado.getId());
        assertEquals(cliente, resultado.getCliente());
        verify(carteiraRepository).save(any(Carteira.class));
    }

    @Test
    @DisplayName("Deve projetar saldo zero quando a carteira não possui eventos")
    void deveProjetarSaldoZeroSemEventos() {
        Long carteiraId = 1L;
        Carteira carteira = new Carteira(carteiraId, new Cliente());

        when(carteiraRepository.findById(carteiraId)).thenReturn(Optional.of(carteira));
        when(eventoRepository.findByCarteiraIdOrderBySequenciaAsc(carteiraId)).thenReturn(Collections.emptyList());

        CarteiraView view = carteiraService.projetarCarteira(carteiraId);

        assertNotNull(view);
        assertEquals(carteiraId, view.carteiraId());
        assertEquals(BigDecimal.ZERO, view.saldoAtual());
        assertEquals(0, view.totalEventos());
        assertEquals(0, view.historico().size());
    }

    @Test
    @DisplayName("Deve projetar saldo correto fazendo o replay dos eventos de depósito e saque")
    void deveProjetarSaldoPorReplayDeEventos() {
        Long carteiraId = 1L;
        Carteira carteira = new Carteira(carteiraId, new Cliente());

        Evento evento1 = new Evento(1L, new BigDecimal("100.00"), TipoEvento.DEPOSITO, LocalDateTime.now().minusHours(2), carteiraId, 1);
        Evento evento2 = new Evento(2L, new BigDecimal("30.00"), TipoEvento.SAQUE, LocalDateTime.now().minusHours(1), carteiraId, 2);
        Evento evento3 = new Evento(3L, new BigDecimal("50.00"), TipoEvento.DEPOSITO, LocalDateTime.now(), carteiraId, 3);

        List<Evento> streamEventos = List.of(evento1, evento2, evento3);

        when(carteiraRepository.findById(carteiraId)).thenReturn(Optional.of(carteira));
        when(eventoRepository.findByCarteiraIdOrderBySequenciaAsc(carteiraId)).thenReturn(streamEventos);

        CarteiraView view = carteiraService.projetarCarteira(carteiraId);

        assertNotNull(view);
        assertEquals(carteiraId, view.carteiraId());
        assertEquals(new BigDecimal("120.00"), view.saldoAtual()); // 100 - 30 + 50 = 120
        assertEquals(3, view.totalEventos());
        assertEquals(streamEventos, view.historico());
    }
}
