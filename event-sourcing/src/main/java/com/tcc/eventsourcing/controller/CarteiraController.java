package com.tcc.eventsourcing.controller;

import com.tcc.eventsourcing.dto.CarteiraView;
import com.tcc.eventsourcing.dto.EventoDTO;
import com.tcc.eventsourcing.modelo.Carteira;
import com.tcc.eventsourcing.modelo.Evento;
import com.tcc.eventsourcing.service.CarteiraService;
import com.tcc.eventsourcing.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carteira")
public class CarteiraController {

    @Autowired
    private CarteiraService carteiraService;

    @Autowired
    private EventoService eventoService;

    @GetMapping("/{id}")
    public ResponseEntity<Carteira> getCarteira(@PathVariable Long id) {
        return ResponseEntity.ok(carteiraService.getCarteira(id));
    }

    @PostMapping("/{id}/eventos")
    public ResponseEntity<Evento> registrarEvento(
            @PathVariable Long id,
            @RequestBody EventoDTO eventoDTO) {
        eventoDTO.setCarteiraId(id);
        return ResponseEntity.ok(eventoService.save(eventoDTO));
    }

    @GetMapping("/{id}/view")
    public ResponseEntity<CarteiraView> getCarteiraView(@PathVariable Long id) {
        return ResponseEntity.ok(carteiraService.projetarCarteira(id));
    }
}

