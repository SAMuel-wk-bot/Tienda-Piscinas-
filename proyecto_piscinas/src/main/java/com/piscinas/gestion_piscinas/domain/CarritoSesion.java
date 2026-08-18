package com.piscinas.gestion_piscinas.domain;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class CarritoSesion implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Map<Long, Integer> cantidades = new LinkedHashMap<>();

    public Map<Long, Integer> getCantidades() {
        return cantidades;
    }

    public int getTotalUnidades() {
        return cantidades.values().stream().mapToInt(Integer::intValue).sum();
    }

    public boolean estaVacio() {
        return cantidades.isEmpty();
    }
}
