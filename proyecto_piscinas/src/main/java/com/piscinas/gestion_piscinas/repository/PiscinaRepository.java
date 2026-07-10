package com.piscinas.gestion_piscinas.repository;

import com.piscinas.gestion_piscinas.domain.Piscina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PiscinaRepository extends JpaRepository<Piscina, Long> {
}