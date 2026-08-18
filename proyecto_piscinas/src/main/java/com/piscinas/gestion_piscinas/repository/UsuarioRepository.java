package com.piscinas.gestion_piscinas.repository;

import com.piscinas.gestion_piscinas.domain.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmailUsuarioIgnoreCase(String emailUsuario);

    boolean existsByEmailUsuarioIgnoreCase(String emailUsuario);
}
