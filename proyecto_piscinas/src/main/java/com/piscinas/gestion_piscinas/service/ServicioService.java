package com.piscinas.gestion_piscinas.service;

import com.piscinas.gestion_piscinas.domain.Servicio;
import java.util.List;

public interface ServicioService {

    List<Servicio> listarServicios();

    List<Servicio> listarServiciosActivos();

    Servicio obtenerServicioPorId(Long id);

    Servicio guardarServicio(Servicio servicio);

    void eliminarServicio(Long id);
}
