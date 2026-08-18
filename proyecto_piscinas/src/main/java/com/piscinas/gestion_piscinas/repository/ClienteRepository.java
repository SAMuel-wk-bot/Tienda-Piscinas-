package com.piscinas.gestion_piscinas.repository;

import com.piscinas.gestion_piscinas.domain.Cliente;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByUsuarioEmailUsuario(String emailUsuario);
}
