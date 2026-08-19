package com.piscinas.gestion_piscinas;

import com.piscinas.gestion_piscinas.domain.CotizacionPiscinaForm;
import com.piscinas.gestion_piscinas.domain.CotizacionPiscinaResultado;
import com.piscinas.gestion_piscinas.domain.EstadoAgua;
import com.piscinas.gestion_piscinas.domain.Servicio;
import com.piscinas.gestion_piscinas.service.CotizacionPiscinaServiceImpl;
import com.piscinas.gestion_piscinas.service.ServicioService;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CotizacionPiscinaTests {

    @Test
    void calculaVolumenLitrosYPrecioParaAguaVerde() {
        Servicio servicio = servicio("Tratamiento de agua", "Evaluación y tratamiento", "20000.00");
        CotizacionPiscinaServiceImpl cotizador = cotizadorCon(servicio);

        CotizacionPiscinaResultado resultado = cotizador.calcular(
                formulario("10", "4", "1.25", EstadoAgua.VERDE));

        assertThat(resultado.getVolumenMetrosCubicos()).isEqualByComparingTo("50.00");
        assertThat(resultado.getVolumenLitros()).isEqualByComparingTo("50000");
        assertThat(resultado.getFactorVolumen()).isEqualByComparingTo("1.25");
        assertThat(resultado.getFactorEstado()).isEqualByComparingTo("1.30");
        assertThat(resultado.getPrecioEstimado()).isEqualByComparingTo("32500.00");
        assertThat(resultado.getServicioRecomendado()).isSameAs(servicio);
    }

    @Test
    void aplicaFactorBaseHastaTreintaMetrosCubicos() {
        Servicio servicio = servicio("Mantenimiento preventivo", "Limpieza periódica", "18000.00");
        CotizacionPiscinaServiceImpl cotizador = cotizadorCon(servicio);

        CotizacionPiscinaResultado resultado = cotizador.calcular(
                formulario("5", "4", "1.5", EstadoAgua.CLARA));

        assertThat(resultado.getVolumenMetrosCubicos()).isEqualByComparingTo("30.00");
        assertThat(resultado.getFactorVolumen()).isEqualByComparingTo("1.00");
        assertThat(resultado.getPrecioEstimado()).isEqualByComparingTo("18000.00");
    }

    @Test
    void conservaCalculoAunqueNoExistaServicioRelacionado() {
        CotizacionPiscinaServiceImpl cotizador = cotizadorCon();

        CotizacionPiscinaResultado resultado = cotizador.calcular(
                formulario("8", "4", "1.5", EstadoAgua.PROBLEMA_EQUIPO));

        assertThat(resultado.getVolumenMetrosCubicos()).isEqualByComparingTo("48.00");
        assertThat(resultado.getServicioRecomendado()).isNull();
        assertThat(resultado.getPrecioEstimado()).isNull();
    }

    private CotizacionPiscinaServiceImpl cotizadorCon(Servicio... servicios) {
        ServicioService servicioService = mock(ServicioService.class);
        when(servicioService.listarServiciosActivos()).thenReturn(List.of(servicios));
        return new CotizacionPiscinaServiceImpl(servicioService);
    }

    private CotizacionPiscinaForm formulario(String largo, String ancho,
            String profundidad, EstadoAgua estado) {
        CotizacionPiscinaForm formulario = new CotizacionPiscinaForm();
        formulario.setLargo(new BigDecimal(largo));
        formulario.setAncho(new BigDecimal(ancho));
        formulario.setProfundidadPromedio(new BigDecimal(profundidad));
        formulario.setEstadoAgua(estado);
        return formulario;
    }

    private Servicio servicio(String nombre, String descripcion, String precio) {
        Servicio servicio = new Servicio();
        servicio.setNombre(nombre);
        servicio.setDescripcion(descripcion);
        servicio.setPrecioBase(new BigDecimal(precio));
        servicio.setActivo(true);
        return servicio;
    }
}
