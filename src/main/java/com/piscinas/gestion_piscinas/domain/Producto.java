package com.piscinas.gestion_piscinas.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "productos")
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProducto;

    @Column(name = "nombre_producto", nullable = false, length = 100)
    @NotBlank(message = "{validation.required.productName}")
    @Size(max = 100, message = "{validation.size.productName}")
    private String nombreProducto;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    @Size(max = 1000, message = "{validation.size.description}")
    private String descripcion;

    @Column(name = "precio", nullable = false, precision = 12, scale = 2)
    @NotNull(message = "{validation.required.price}")
    @DecimalMin(value = "0.01", message = "{validation.min.price}")
    private BigDecimal precio;

    @Column(name = "stock", nullable = false)
    @NotNull(message = "{validation.required.stock}")
    @Min(value = 0, message = "{validation.min.stock}")
    private Integer stock = 0;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDateTime fechaIngreso;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_categoria", nullable = false)
    @NotNull(message = "{validation.required.category}")
    private Categoria categoria;

    public Producto() {
    }

    public Producto(String nombreProducto, String descripcion, BigDecimal precio,
            Integer stock, Categoria categoria) {
        this.nombreProducto = nombreProducto;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
    }

    @PrePersist
    public void asignarFechaIngreso() {
        if (fechaIngreso == null) {
            fechaIngreso = LocalDateTime.now();
        }
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public LocalDateTime getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDateTime fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
