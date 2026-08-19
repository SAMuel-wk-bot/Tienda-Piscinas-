package com.piscinas.gestion_piscinas.service;

import com.piscinas.gestion_piscinas.domain.CotizacionPiscinaForm;
import com.piscinas.gestion_piscinas.domain.CotizacionPiscinaResultado;
import com.piscinas.gestion_piscinas.domain.EstadoAgua;
import com.piscinas.gestion_piscinas.domain.Servicio;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.Normalizer;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CotizacionPiscinaServiceImpl implements CotizacionPiscinaService {

    private static final BigDecimal MIL = new BigDecimal("1000");

    private final ServicioService servicioService;

    public CotizacionPiscinaServiceImpl(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    @Override
    public CotizacionPiscinaResultado calcular(CotizacionPiscinaForm formulario) {
        validarFormulario(formulario);

        BigDecimal volumenMetrosCubicos = formulario.getLargo()
                .multiply(formulario.getAncho())
                .multiply(formulario.getProfundidadPromedio())
                .setScale(2, RoundingMode.HALF_UP);
        BigDecimal volumenLitros = volumenMetrosCubicos.multiply(MIL)
                .setScale(0, RoundingMode.HALF_UP);
        BigDecimal factorVolumen = calcularFactorVolumen(volumenMetrosCubicos);
        BigDecimal factorEstado = calcularFactorEstado(formulario.getEstadoAgua());
        Servicio servicio = buscarServicio(formulario.getEstadoAgua());
        BigDecimal precioEstimado = servicio == null ? null
                : servicio.getPrecioBase()
                        .multiply(factorVolumen)
                        .multiply(factorEstado)
                        .setScale(2, RoundingMode.HALF_UP);

        return new CotizacionPiscinaResultado(volumenMetrosCubicos, volumenLitros,
                factorVolumen, factorEstado, precioEstimado,
                formulario.getEstadoAgua(), servicio);
    }

    private void validarFormulario(CotizacionPiscinaForm formulario) {
        if (formulario == null || formulario.getLargo() == null
                || formulario.getAncho() == null
                || formulario.getProfundidadPromedio() == null
                || formulario.getEstadoAgua() == null) {
            throw new IllegalArgumentException("Los datos de la piscina están incompletos.");
        }
        if (formulario.getLargo().signum() <= 0 || formulario.getAncho().signum() <= 0
                || formulario.getProfundidadPromedio().signum() <= 0) {
            throw new IllegalArgumentException("Las dimensiones deben ser mayores a cero.");
        }
    }

    private BigDecimal calcularFactorVolumen(BigDecimal volumen) {
        if (volumen.compareTo(new BigDecimal("30")) <= 0) {
            return new BigDecimal("1.00");
        }
        if (volumen.compareTo(new BigDecimal("60")) <= 0) {
            return new BigDecimal("1.25");
        }
        return new BigDecimal("1.50");
    }

    private BigDecimal calcularFactorEstado(EstadoAgua estadoAgua) {
        return switch (estadoAgua) {
            case CLARA, PROBLEMA_EQUIPO -> new BigDecimal("1.00");
            case TURBIA -> new BigDecimal("1.15");
            case VERDE -> new BigDecimal("1.30");
        };
    }

    private Servicio buscarServicio(EstadoAgua estadoAgua) {
        List<String> palabras = switch (estadoAgua) {
            case CLARA -> List.of("mantenimiento", "limpieza");
            case TURBIA -> List.of("limpieza", "tratamiento", "agua");
            case VERDE -> List.of("tratamiento", "agua", "limpieza");
            case PROBLEMA_EQUIPO -> List.of("bomba", "equipo", "revision");
        };

        return servicioService.listarServiciosActivos().stream()
                .filter(servicio -> contienePalabra(servicio, palabras))
                .findFirst()
                .orElse(null);
    }

    private boolean contienePalabra(Servicio servicio, List<String> palabras) {
        String texto = normalizar(servicio.getNombre() + " " + servicio.getDescripcion());
        return palabras.stream().anyMatch(texto::contains);
    }

    private String normalizar(String texto) {
        return Normalizer.normalize(texto.toLowerCase(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
    }
}
