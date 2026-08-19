package com.piscinas.gestion_piscinas.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "servicios")
public class Servicio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio")
    private Long idServicio;

    @Column(name = "nombre", nullable = false, length = 100)
    @NotBlank(message = "{validation.required.serviceName}")
    @Size(max = 100, message = "{validation.size.serviceName}")
    private String nombre;

    @Column(name = "descripcion", nullable = false, length = 1000)
    @NotBlank(message = "{validation.required.serviceDescription}")
    @Size(max = 1000, message = "{validation.size.description}")
    private String descripcion;

    @Column(name = "precio_base", nullable = false, precision = 12, scale = 2)
    @NotNull(message = "{validation.required.basePrice}")
    @DecimalMin(value = "0.00", message = "{validation.min.basePrice}")
    private BigDecimal precioBase;

    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    public Servicio() {
    }

    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(BigDecimal precioBase) {
        this.precioBase = precioBase;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
