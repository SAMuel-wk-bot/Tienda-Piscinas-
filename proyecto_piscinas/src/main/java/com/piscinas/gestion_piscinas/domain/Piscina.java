package com.piscinas.gestion_piscinas.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "piscinas")
public class Piscina implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_piscina")
    private Integer idPiscina;

    @Column(name = "nombre_piscina", nullable = false, length = 50)
    private String nombrePiscina;

    @Column(name = "tipo", nullable = false, length = 30)
    private String tipo;

    @Column(name = "capacidad_max", nullable = false)
    private Integer capacidadMax;

    @Column(name = "precio_hora", precision=10, scale=2)
    @DecimalMin(value="0.00", inclusive=true)
    private BigDecimal precio_hora;

    @Column(name = "estado", length = 20)
    private String estado;

    public Piscina() {
    }

    public Integer getIdPiscina() {
        return idPiscina;
    }

    public void setIdPiscina(Integer idPiscina) {
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
