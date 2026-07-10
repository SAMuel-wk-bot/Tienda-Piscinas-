package com.piscinas.gestion_piscinas.service;

import com.piscinas.gestion_piscinas.domain.Categoria;
import java.util.List;

public interface CategoriaService {
    List<Categoria> listarCategorias();
    Categoria guardarCategoria(Categoria categoria);
    Categoria obtenerCategoriaPorId(Long id);
    void eliminarCategoria(Long id);
}