package com.tcc.eventsourcing.modelo.dao;

import com.tcc.eventsourcing.modelo.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Long> {
}
