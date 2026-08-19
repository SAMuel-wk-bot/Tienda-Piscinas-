package com.piscinas.gestion_piscinas.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "piscinas")
public class Piscina implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_piscina")
    private Long idPiscina;

    @Column(name = "nombre_piscina", nullable = false, length = 50)
    @NotBlank(message = "El nombre de la piscina es obligatorio.")
    private String nombrePiscina;

    @Column(name = "tipo", nullable = false, length = 30)
    @NotBlank(message = "El tipo de piscina es obligatorio.")
    private String tipo;

    @Column(name = "capacidad_max", nullable = false)
    @NotNull(message = "La capacidad es obligatoria.")
    @Min(value = 1, message = "La capacidad debe ser mayor a cero.")
    private Integer capacidadMax;

    @Column(name = "precio_hora", nullable = false, precision=10, scale=2)
    @NotNull(message = "El precio por hora es obligatorio.")
    @DecimalMin(value="0.00", inclusive=true)
    private BigDecimal precio_hora;

    @Column(name = "estado", nullable = false, length = 20)
    @NotBlank(message = "El estado es obligatorio.")
    private String estado;

    public Piscina() {
    }

    public Long getIdPiscina() {
        return idPiscina;
    }

    public void setIdPiscina(Long idPiscina) {
        this.idPiscina = idPiscina;
    }

    public String getNombrePiscina() {
        return nombrePiscina;
    }

    public void setNombrePiscina(String nombrePiscina) {
        this.nombrePiscina = nombrePiscina;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getCapacidadMax() {
        return capacidadMax;
    }

    public void setCapacidadMax(Integer capacidadMax) {
        this.capacidadMax = capacidadMax;
    }

    public BigDecimal getPrecio_hora() {
        return precio_hora;
    }

    public void setPrecio_hora(BigDecimal precio_hora) {
        this.precio_hora = precio_hora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
    
}
