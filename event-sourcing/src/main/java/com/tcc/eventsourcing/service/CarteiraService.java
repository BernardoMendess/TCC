package com.tcc.eventsourcing.service;

import com.tcc.eventsourcing.modelo.Carteira;
import com.tcc.eventsourcing.modelo.Cliente;
import com.tcc.eventsourcing.modelo.dao.CarteiraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CarteiraService {

    @Autowired
    private CarteiraRepository carteiraRepository;

    public Carteira criarCarteira(Cliente cliente) {
        Carteira carteira = new Carteira();
        carteira.setCliente(cliente);
        carteira.setSaldo(BigDecimal.ZERO);
        return carteiraRepository.save(carteira);
    }

    public Carteira getCarteira(Long id) {
        return carteiraRepository.findById(id).orElseThrow();
    }

}
