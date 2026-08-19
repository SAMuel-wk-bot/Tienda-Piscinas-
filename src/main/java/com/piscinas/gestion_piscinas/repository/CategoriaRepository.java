package com.piscinas.gestion_piscinas.repository;

import com.piscinas.gestion_piscinas.domain.Categoria;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    boolean existsByNombreCategoriaIgnoreCase(String nombreCategoria);

    Optional<Categoria> findByNombreCategoriaIgnoreCase(String nombreCategoria);

    boolean existsByNombreCategoriaIgnoreCaseAndIdCategoriaNot(
            String nombreCategoria, Long idCategoria);
}
