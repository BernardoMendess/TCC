package com.tcc.crud.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initializeDatabase() {
        String dbUrl = System.getenv("DB_URL");
        String username = System.getenv("DB_USERNAME");
        String password = System.getenv("DB_PASSWORD");

        if (username == null || username.isBlank()) {
            username = "postgres";
        }
        if (password == null) {
            password = "postgres";
        }

        // Se for PostgreSQL (padrão do projeto)
        if (dbUrl == null || dbUrl.contains("postgresql")) {
            String maintenanceUrl = "jdbc:postgresql://localhost:5432/postgres";
            String targetDb = "crud_tcc";

            try (Connection conn = DriverManager.getConnection(maintenanceUrl, username, password);
                 Statement stmt = conn.createStatement()) {

                ResultSet rs = stmt.executeQuery("SELECT 1 FROM pg_database WHERE datname = '" + targetDb + "'");
                if (!rs.next()) {
                    stmt.executeUpdate("CREATE DATABASE " + targetDb);
                    System.out.println("==========================================================");
                    System.out.println(">>> Banco de dados PostgreSQL '" + targetDb + "' criado com sucesso! <<<");
                    System.out.println("==========================================================");
                }
            } catch (Exception e) {
                System.out.println(">>> [DatabaseInitializer] Informação: " + e.getMessage());
            }
        }
    }
}
