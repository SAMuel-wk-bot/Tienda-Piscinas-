package com.piscinas.gestion_piscinas.repository;

import com.piscinas.gestion_piscinas.domain.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}