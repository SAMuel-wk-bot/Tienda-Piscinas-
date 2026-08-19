package com.piscinas.gestion_piscinas.domain;

import java.math.BigDecimal;
import java.util.List;

public class ResumenCarrito {

    private final List<ItemCarrito> items;
    private final int totalUnidades;
    private final BigDecimal subtotal;

    public ResumenCarrito(List<ItemCarrito> items) {
        this.items = items;
        this.totalUnidades = items.stream().mapToInt(ItemCarrito::getCantidad).sum();
        this.subtotal = items.stream().map(ItemCarrito::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<ItemCarrito> getItems() {
        return items;
    }

    public int getTotalUnidades() {
        return totalUnidades;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public boolean isVacio() {
        return items.isEmpty();
    }

    public boolean isStockValido() {
        return items.stream().allMatch(ItemCarrito::isStockSuficiente);
    }
}
