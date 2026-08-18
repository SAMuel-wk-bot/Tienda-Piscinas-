package com.piscinas.gestion_piscinas.service;

import com.piscinas.gestion_piscinas.domain.Producto;
import java.util.List;

public interface ProductoService {
    List<Producto> listarProductos();
    List<Producto> buscarProductos(String nombre, Long idCategoria, boolean soloDisponibles);
    Producto guardarProducto(Producto producto);
    Producto obtenerProductoPorId(Long id);
    void eliminarProducto(Long id);
}
