package com.piscinas.gestion_piscinas.domain;

import java.math.BigDecimal;

public class CotizacionPiscinaResultado {

    private final BigDecimal volumenMetrosCubicos;
    private final BigDecimal volumenLitros;
    private final BigDecimal factorVolumen;
    private final BigDecimal factorEstado;
    private final BigDecimal precioEstimado;
    private final EstadoAgua estadoAgua;
    private final Servicio servicioRecomendado;

    public CotizacionPiscinaResultado(BigDecimal volumenMetrosCubicos,
            BigDecimal volumenLitros, BigDecimal factorVolumen,
            BigDecimal factorEstado, BigDecimal precioEstimado,
            EstadoAgua estadoAgua, Servicio servicioRecomendado) {
        this.volumenMetrosCubicos = volumenMetrosCubicos;
        this.volumenLitros = volumenLitros;
        this.factorVolumen = factorVolumen;
        this.factorEstado = factorEstado;
        this.precioEstimado = precioEstimado;
        this.estadoAgua = estadoAgua;
        this.servicioRecomendado = servicioRecomendado;
    }

    public BigDecimal getVolumenMetrosCubicos() {
        return volumenMetrosCubicos;
    }

    public BigDecimal getVolumenLitros() {
        return volumenLitros;
    }

    public BigDecimal getFactorVolumen() {
        return factorVolumen;
    }

    public BigDecimal getFactorEstado() {
        return factorEstado;
    }

    public BigDecimal getPrecioEstimado() {
        return precioEstimado;
    }

    public EstadoAgua getEstadoAgua() {
        return estadoAgua;
    }

    public Servicio getServicioRecomendado() {
        return servicioRecomendado;
    }
}
