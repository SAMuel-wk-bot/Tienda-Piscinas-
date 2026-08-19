package com.piscinas.gestion_piscinas.domain;

import java.math.BigDecimal;

public class ItemCarrito {

    private final Producto producto;
    private final int cantidad;
    private final BigDecimal subtotal;
    private final boolean stockSuficiente;

    public ItemCarrito(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.subtotal = producto.getPrecio().multiply(BigDecimal.valueOf(cantidad));
        this.stockSuficiente = producto.getStock() >= cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public boolean isStockSuficiente() {
        return stockSuficiente;
    }
}
