package com.piscinas.gestion_piscinas.domain;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CotizacionPiscinaForm {

    @NotNull(message = "{validation.required.length}")
    @DecimalMin(value = "0.50", message = "{validation.range.length}")
    @DecimalMax(value = "50.00", message = "{validation.range.length}")
    private BigDecimal largo;

    @NotNull(message = "{validation.required.width}")
    @DecimalMin(value = "0.50", message = "{validation.range.width}")
    @DecimalMax(value = "30.00", message = "{validation.range.width}")
    private BigDecimal ancho;

    @NotNull(message = "{validation.required.depth}")
    @DecimalMin(value = "0.30", message = "{validation.range.depth}")
    @DecimalMax(value = "5.00", message = "{validation.range.depth}")
    private BigDecimal profundidadPromedio;

    @NotNull(message = "{validation.required.waterState}")
    private EstadoAgua estadoAgua;

    public BigDecimal getLargo() {
        return largo;
    }

    public void setLargo(BigDecimal largo) {
        this.largo = largo;
    }

    public BigDecimal getAncho() {
        return ancho;
    }

    public void setAncho(BigDecimal ancho) {
        this.ancho = ancho;
    }

    public BigDecimal getProfundidadPromedio() {
        return profundidadPromedio;
    }

    public void setProfundidadPromedio(BigDecimal profundidadPromedio) {
        this.profundidadPromedio = profundidadPromedio;
    }

    public EstadoAgua getEstadoAgua() {
        return estadoAgua;
    }

    public void setEstadoAgua(EstadoAgua estadoAgua) {
        this.estadoAgua = estadoAgua;
    }
}
