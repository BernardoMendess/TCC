package com.tcc.crud.controller;

import com.tcc.crud.dto.TransacaoDTO;
import com.tcc.crud.modelo.Carteira;
import com.tcc.crud.service.CarteiraService;
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

    @PostMapping
    public ResponseEntity<Carteira> criarCarteira(@RequestParam Long clienteId) {
        return ResponseEntity.ok(carteiraService.criarCarteira(clienteId));
    }

    @PostMapping("/{id}/transacoes")
    public ResponseEntity<Carteira> atualizarSaldo(
            @PathVariable Long id,
            @RequestBody TransacaoDTO transacaoDTO) {
        return ResponseEntity.ok(carteiraService.atualizarSaldo(id, transacaoDTO.getValor(), transacaoDTO.getTipo()));
    }
}
