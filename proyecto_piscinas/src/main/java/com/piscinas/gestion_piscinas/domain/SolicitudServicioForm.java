package com.piscinas.gestion_piscinas.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SolicitudServicioForm {

    @NotNull(message = "Debe seleccionar un servicio.")
    private Long idServicio;

    @NotBlank(message = "La dirección del servicio es obligatoria.")
    @Size(max = 250, message = "La dirección no puede superar 250 caracteres.")
    private String direccionServicio;

    @Size(max = 1000, message = "Las observaciones no pueden superar 1000 caracteres.")
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
