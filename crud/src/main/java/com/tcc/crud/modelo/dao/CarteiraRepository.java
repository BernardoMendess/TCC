package com.tcc.crud.modelo.dao;

import com.tcc.crud.modelo.Carteira;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarteiraRepository extends CrudRepository<Carteira, Long> {

}
