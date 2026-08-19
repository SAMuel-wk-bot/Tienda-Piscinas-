package com.piscinas.gestion_piscinas.service;

import com.piscinas.gestion_piscinas.domain.CarritoSesion;
import com.piscinas.gestion_piscinas.domain.ResumenCarrito;
import jakarta.servlet.http.HttpSession;

public interface CarritoService {

    CarritoSesion obtenerCarrito(HttpSession session);

    ResumenCarrito obtenerResumen(HttpSession session);

    void agregarProducto(Long idProducto, int cantidad, HttpSession session);

    void aumentarProducto(Long idProducto, HttpSession session);

    void reducirProducto(Long idProducto, HttpSession session);

    void eliminarProducto(Long idProducto, HttpSession session);

    void vaciar(HttpSession session);
}
