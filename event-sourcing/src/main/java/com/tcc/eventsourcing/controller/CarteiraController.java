package com.tcc.eventsourcing.controller;

import com.tcc.eventsourcing.dto.EventoDTO;
import com.tcc.eventsourcing.modelo.Carteira;
import com.tcc.eventsourcing.modelo.TipoEvento;
import com.tcc.eventsourcing.service.CarteiraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carteira")
public class CarteiraController {

    @Autowired
    private CarteiraService carteiraService;

    @GetMapping("/{id}")
    public ResponseEntity<Carteira> getCarteira(@PathVariable Long id) {
        return ResponseEntity.ok(carteiraService.getCarteira(id));
    }

//    @PostMapping("/{id}/transacoes")
//    public ResponseEntity<Carteira> atualizarSaldo(
//            @PathVariable Long id,
//            @RequestBody EventoDTO eventoDTO) {
//        return ResponseEntity.ok(carteiraService.atualizarSaldo(id, eventoDTO.getValor(), TipoEvento.valueOf(eventoDTO.getTipo())));
//    }
}
