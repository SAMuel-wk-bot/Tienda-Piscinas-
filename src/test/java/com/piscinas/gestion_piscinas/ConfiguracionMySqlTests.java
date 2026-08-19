package com.piscinas.gestion_piscinas;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

class ConfiguracionMySqlTests {

    private static final String HASH_ADMIN =
            "$2a$10$vQvJ0TwYKZgldp9KE6oUpegAbKmJ6/cew8X56DGFyhPFaRhTtj6Oi";
    private static final String HASH_CLIENTE =
            "$2a$10$Hcx7qW8tBKax01nkjbwDqeFctBN/NIG7pHmq9PMsqcoZAzBPcVgB2";

    @Test
    void propiedadesUsanVariablesYNoIncluyenClaveMySqlFija() throws IOException {
        String propiedades = Files.readString(
                Path.of("src/main/resources/application.properties"), StandardCharsets.UTF_8);

        assertThat(propiedades)
                .contains("spring.datasource.url=${DB_URL:")
                .contains("spring.datasource.username=${DB_USER:root}")
                .contains("spring.datasource.password=${DB_PASSWORD:}")
                .contains("server.port=${SERVER_PORT:8080}")
                .doesNotContain("spring.datasource.password=contrasenna");
    }

    @Test
    void scriptOficialCreaTreceTablasSinCrearCredencialesMySql() throws IOException {
        String script = Files.readString(Path.of("piscinas_script.sql"));

        assertThat(script).contains("CREATE DATABASE IF NOT EXISTS gestion_piscinas");
        assertThat(script.split("CREATE TABLE", -1)).hasSize(14);
        assertThat(script.toUpperCase()).doesNotContain("CREATE USER", "IDENTIFIED BY");
    }

    @Test
    void usuariosDemostrativosDelScriptTienenHashesBCryptValidos() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        assertThat(encoder.matches("AdminPiscinas2026!", HASH_ADMIN)).isTrue();
        assertThat(encoder.matches("ClientePiscinas2026!", HASH_CLIENTE)).isTrue();
    }
}
