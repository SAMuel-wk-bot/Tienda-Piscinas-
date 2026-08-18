package com.piscinas.gestion_piscinas.service;

import com.piscinas.gestion_piscinas.domain.Servicio;
import com.piscinas.gestion_piscinas.repository.ServicioRepository;
import com.piscinas.gestion_piscinas.repository.SolicitudServicioRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioServiceImpl implements ServicioService {

    private final ServicioRepository servicioRepository;
    private final SolicitudServicioRepository solicitudRepository;

    public ServicioServiceImpl(ServicioRepository servicioRepository,
            SolicitudServicioRepository solicitudRepository) {
        this.servicioRepository = servicioRepository;
        this.solicitudRepository = solicitudRepository;
    }

    @Override
    public List<Servicio> listarServicios() {
        return servicioRepository.findAllByOrderByNombreAsc();
    }

    @Override
    public List<Servicio> listarServiciosActivos() {
        return servicioRepository.findByActivoTrueOrderByNombreAsc();
    }

    @Override
    public Servicio obtenerServicioPorId(Long id) {
        return servicioRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Servicio guardarServicio(Servicio servicio) {
        if (servicio.getIdServicio() == null) {
            servicio.setNombre(servicio.getNombre().trim());
            servicio.setDescripcion(servicio.getDescripcion().trim());
            return servicioRepository.save(servicio);
        }

        Servicio existente = obtenerObligatorio(servicio.getIdServicio());
        existente.setNombre(servicio.getNombre().trim());
        existente.setDescripcion(servicio.getDescripcion().trim());
        existente.setPrecioBase(servicio.getPrecioBase());
        existente.setActivo(servicio.isActivo());
        return servicioRepository.save(existente);
    }

    @Override
    @Transactional
    public void eliminarServicio(Long id) {
        Servicio servicio = obtenerObligatorio(id);
        if (solicitudRepository.existsByServicioIdServicio(id)) {
            throw new IllegalStateException(
                    "No se puede eliminar el servicio porque tiene solicitudes asociadas; puede desactivarlo.");
        }
        servicioRepository.delete(servicio);
    }

    private Servicio obtenerObligatorio(Long id) {
        return servicioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El servicio no existe."));
    }
}
