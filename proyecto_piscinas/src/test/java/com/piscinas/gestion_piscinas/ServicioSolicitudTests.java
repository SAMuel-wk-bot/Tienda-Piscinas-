package com.piscinas.gestion_piscinas;

import com.piscinas.gestion_piscinas.domain.EstadoSolicitudServicio;
import com.piscinas.gestion_piscinas.domain.Servicio;
import com.piscinas.gestion_piscinas.domain.SolicitudServicio;
import com.piscinas.gestion_piscinas.domain.SolicitudServicioForm;
import com.piscinas.gestion_piscinas.service.ServicioService;
import com.piscinas.gestion_piscinas.service.SolicitudServicioService;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class ServicioSolicitudTests {

    private static final String CORREO_CLIENTE = "cliente@tiendapiscinas.test";

    @Autowired
    private ServicioService servicioService;

    @Autowired
    private SolicitudServicioService solicitudService;

    @Test
    void clienteCreaSolicitudPendienteConIdentidadDelServidor() {
        Servicio servicio = guardarServicio("Mantenimiento de prueba", true);
        SolicitudServicioForm formulario = crearFormulario(servicio.getIdServicio());

        SolicitudServicio solicitud = solicitudService.crearSolicitud(formulario, CORREO_CLIENTE);

        assertThat(solicitud.getIdSolicitud()).isNotNull();
        assertThat(solicitud.getEstado()).isEqualTo(EstadoSolicitudServicio.PENDIENTE);
        assertThat(solicitud.getCliente().getUsuario().getEmailUsuario()).isEqualTo(CORREO_CLIENTE);
        assertThat(solicitud.getServicio().getIdServicio()).isEqualTo(servicio.getIdServicio());
    }

    @Test
    void rechazaSolicitudParaServicioInactivo() {
        Servicio servicio = guardarServicio("Servicio inactivo de prueba", false);
        SolicitudServicioForm formulario = crearFormulario(servicio.getIdServicio());

        assertThatThrownBy(() -> solicitudService.crearSolicitud(formulario, CORREO_CLIENTE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("business.request.serviceUnavailable");
    }

    @Test
    void administradorActualizaEstadoYPuedeFiltrarlo() {
        Servicio servicio = guardarServicio("Limpieza de prueba", true);
        SolicitudServicio solicitud = solicitudService.crearSolicitud(
                crearFormulario(servicio.getIdServicio()), CORREO_CLIENTE);

        SolicitudServicio actualizada = solicitudService.actualizarEstado(
                solicitud.getIdSolicitud(), EstadoSolicitudServicio.EN_PROCESO);

        assertThat(actualizada.getEstado()).isEqualTo(EstadoSolicitudServicio.EN_PROCESO);
        assertThat(solicitudService.listarSolicitudes(EstadoSolicitudServicio.EN_PROCESO))
                .extracting(SolicitudServicio::getIdSolicitud)
                .contains(solicitud.getIdSolicitud());
    }

    @Test
    void impideEliminarServicioConSolicitudes() {
        Servicio servicio = guardarServicio("Instalación de prueba", true);
        solicitudService.crearSolicitud(crearFormulario(servicio.getIdServicio()), CORREO_CLIENTE);

        assertThatThrownBy(() -> servicioService.eliminarServicio(servicio.getIdServicio()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("business.service.associated");
    }

    private Servicio guardarServicio(String nombre, boolean activo) {
        Servicio servicio = new Servicio();
        servicio.setNombre(nombre);
        servicio.setDescripcion("Descripción académica de prueba");
        servicio.setPrecioBase(new BigDecimal("15000.00"));
        servicio.setActivo(activo);
        return servicioService.guardarServicio(servicio);
    }

    private SolicitudServicioForm crearFormulario(Long idServicio) {
        SolicitudServicioForm formulario = new SolicitudServicioForm();
        formulario.setIdServicio(idServicio);
        formulario.setDireccionServicio("Dirección académica de prueba");
        formulario.setObservaciones("Observación de prueba");
        return formulario;
    }
}
