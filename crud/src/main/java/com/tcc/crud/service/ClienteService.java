package com.tcc.crud.service;

import com.tcc.crud.modelo.Cliente;
import com.tcc.crud.modelo.dao.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente salvarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente getCliente(Long id) {
        return clienteRepository.findById(id).orElseThrow();
    }
}
