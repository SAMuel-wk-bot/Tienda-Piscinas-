package com.piscinas.gestion_piscinas.repository;

import com.piscinas.gestion_piscinas.domain.Servicio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {

    List<Servicio> findByActivoTrueOrderByNombreAsc();

    List<Servicio> findAllByOrderByNombreAsc();
}
