package com.tcc.eventsourcing.modelo.dao;

import com.tcc.eventsourcing.modelo.Carteira;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarteiraRepository extends CrudRepository<Carteira, Long> {

}
