package com.piscinas.gestion_piscinas.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SolicitudServicioForm {

    @NotNull(message = "{validation.required.service}")
    private Long idServicio;

    @NotBlank(message = "{validation.required.serviceAddress}")
    @Size(max = 250, message = "{validation.size.address}")
    private String direccionServicio;

    @Size(max = 1000, message = "{validation.size.notes}")
    private String observaciones;

    public SolicitudServicioForm() {
    }

    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public String getDireccionServicio() {
        return direccionServicio;
    }

    public void setDireccionServicio(String direccionServicio) {
        this.direccionServicio = direccionServicio;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
