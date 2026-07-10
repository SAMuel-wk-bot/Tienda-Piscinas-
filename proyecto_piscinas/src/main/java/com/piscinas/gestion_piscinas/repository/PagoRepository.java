package com.piscinas.gestion_piscinas.repository;

import com.piscinas.gestion_piscinas.domain.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
}