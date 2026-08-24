package com.tcc.crud;

import com.tcc.crud.config.DatabaseInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CrudApplication {

    public static void main(String[] args) {
        DatabaseInitializer.initializeDatabase();
        SpringApplication.run(CrudApplication.class, args);
    }

}
