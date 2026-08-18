package com.piscinas.gestion_piscinas.service;

import com.piscinas.gestion_piscinas.domain.EstadoSolicitudServicio;
import com.piscinas.gestion_piscinas.domain.SolicitudServicio;
import com.piscinas.gestion_piscinas.domain.SolicitudServicioForm;
import java.util.List;

public interface SolicitudServicioService {

    SolicitudServicio crearSolicitud(SolicitudServicioForm formulario, String correoUsuario);

    List<SolicitudServicio> listarSolicitudesDelCliente(String correoUsuario);

    List<SolicitudServicio> listarSolicitudes(EstadoSolicitudServicio estado);

    SolicitudServicio actualizarEstado(Long idSolicitud, EstadoSolicitudServicio estado);
}
