package com.piscinas.gestion_piscinas.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitudes_servicio")
public class SolicitudServicio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solicitud")
    private Long idSolicitud;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cliente", nullable = false)
    @NotNull(message = "El cliente es obligatorio.")
    private Cliente cliente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_servicio", nullable = false)
    @NotNull(message = "El servicio es obligatorio.")
    private Servicio servicio;

    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDateTime fechaSolicitud;

    @Column(name = "direccion_servicio", nullable = false, length = 250)
    @NotBlank(message = "La dirección del servicio es obligatoria.")
    @Size(max = 250, message = "La dirección no puede superar 250 caracteres.")
    private String direccionServicio;

    @Column(name = "observaciones", length = 1000)
    @Size(max = 1000, message = "Las observaciones no pueden superar 1000 caracteres.")
    private String observaciones;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoSolicitudServicio estado = EstadoSolicitudServicio.PENDIENTE;

    public SolicitudServicio() {
    }

    @PrePersist
    public void asignarFechaSolicitud() {
        if (fechaSolicitud == null) {
            fechaSolicitud = LocalDateTime.now();
        }
    }

    public Long getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
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

    public EstadoSolicitudServicio getEstado() {
        return estado;
    }

    public void setEstado(EstadoSolicitudServicio estado) {
        this.estado = estado;
    }
}
