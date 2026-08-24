package com.tcc.eventsourcing.service;

import com.tcc.eventsourcing.modelo.Cliente;
import com.tcc.eventsourcing.modelo.dao.ClienteRepository;
import jakarta.transaction.Transactional;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CarteiraService carteiraService;

    @Transactional
    public Cliente salvarCliente(Cliente cliente) {
        val clienteSalvo = clienteRepository.save(cliente);
        val carteira = carteiraService.criarCarteira(clienteSalvo);
        clienteRepository.save(clienteSalvo.withCarteira(carteira));
        return clienteSalvo;
    }

    public Cliente getCliente(Long id) {
        return clienteRepository.findById(id).orElseThrow();
    }
}
