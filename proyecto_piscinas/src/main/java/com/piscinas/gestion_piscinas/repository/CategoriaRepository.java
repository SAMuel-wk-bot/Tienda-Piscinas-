package com.piscinas.gestion_piscinas.repository;

import com.piscinas.gestion_piscinas.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}