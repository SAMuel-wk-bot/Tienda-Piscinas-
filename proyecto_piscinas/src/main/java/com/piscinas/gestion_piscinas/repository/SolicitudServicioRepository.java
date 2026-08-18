package com.piscinas.gestion_piscinas.repository;

import com.piscinas.gestion_piscinas.domain.EstadoSolicitudServicio;
import com.piscinas.gestion_piscinas.domain.SolicitudServicio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudServicioRepository extends JpaRepository<SolicitudServicio, Long> {

    List<SolicitudServicio> findByClienteUsuarioEmailUsuarioOrderByFechaSolicitudDesc(String emailUsuario);

    List<SolicitudServicio> findByEstadoOrderByFechaSolicitudDesc(EstadoSolicitudServicio estado);
}
