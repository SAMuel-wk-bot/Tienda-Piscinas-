package com.piscinas.gestion_piscinas.service;

import com.piscinas.gestion_piscinas.domain.Cliente;
import com.piscinas.gestion_piscinas.domain.EstadoSolicitudServicio;
import com.piscinas.gestion_piscinas.domain.Servicio;
import com.piscinas.gestion_piscinas.domain.SolicitudServicio;
import com.piscinas.gestion_piscinas.domain.SolicitudServicioForm;
import com.piscinas.gestion_piscinas.repository.ClienteRepository;
import com.piscinas.gestion_piscinas.repository.ServicioRepository;
import com.piscinas.gestion_piscinas.repository.SolicitudServicioRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SolicitudServicioServiceImpl implements SolicitudServicioService {

    private final SolicitudServicioRepository solicitudRepository;
    private final ClienteRepository clienteRepository;
    private final ServicioRepository servicioRepository;

    public SolicitudServicioServiceImpl(SolicitudServicioRepository solicitudRepository,
            ClienteRepository clienteRepository, ServicioRepository servicioRepository) {
        this.solicitudRepository = solicitudRepository;
        this.clienteRepository = clienteRepository;
        this.servicioRepository = servicioRepository;
    }

    @Override
    @Transactional
    public SolicitudServicio crearSolicitud(SolicitudServicioForm formulario,
            String correoUsuario) {
        Cliente cliente = clienteRepository.findByUsuarioEmailUsuario(correoUsuario)
                .orElseThrow(() -> new IllegalArgumentException(
                        "business.customer.profileNotFound"));
        Servicio servicio = servicioRepository.findById(formulario.getIdServicio())
                .filter(Servicio::isActivo)
                .orElseThrow(() -> new IllegalArgumentException(
                        "business.request.serviceUnavailable"));

        SolicitudServicio solicitud = new SolicitudServicio();
        solicitud.setCliente(cliente);
        solicitud.setServicio(servicio);
        solicitud.setDireccionServicio(formulario.getDireccionServicio().trim());
        solicitud.setObservaciones(formulario.getObservaciones() == null
                ? null : formulario.getObservaciones().trim());
        solicitud.setEstado(EstadoSolicitudServicio.PENDIENTE);
        return solicitudRepository.save(solicitud);
    }

    @Override
    public List<SolicitudServicio> listarSolicitudesDelCliente(String correoUsuario) {
        return solicitudRepository
                .findByClienteUsuarioEmailUsuarioOrderByFechaSolicitudDesc(correoUsuario);
    }

    @Override
    public List<SolicitudServicio> listarSolicitudes(EstadoSolicitudServicio estado) {
        return estado == null
                ? solicitudRepository.findAllByOrderByFechaSolicitudDesc()
                : solicitudRepository.findByEstadoOrderByFechaSolicitudDesc(estado);
    }

    @Override
    @Transactional
    public SolicitudServicio actualizarEstado(Long idSolicitud,
            EstadoSolicitudServicio estado) {
        if (estado == null) {
            throw new IllegalArgumentException("business.status.required");
        }
        SolicitudServicio solicitud = solicitudRepository.findById(idSolicitud)
                .orElseThrow(() -> new IllegalArgumentException("business.request.notFound"));
        solicitud.setEstado(estado);
        return solicitudRepository.save(solicitud);
    }
}
