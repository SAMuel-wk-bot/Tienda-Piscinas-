package com.piscinas.gestion_piscinas.service;

import com.piscinas.gestion_piscinas.domain.Categoria;
import com.piscinas.gestion_piscinas.repository.CategoriaRepository;
import com.piscinas.gestion_piscinas.repository.ProductoRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository,
            ProductoRepository productoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    @Override
    @Transactional
    public Categoria guardarCategoria(Categoria categoria) {
        String nombre = categoria.getNombreCategoria().trim();
        boolean nombreDuplicado = categoria.getIdCategoria() == null
                ? categoriaRepository.existsByNombreCategoriaIgnoreCase(nombre)
                : categoriaRepository.existsByNombreCategoriaIgnoreCaseAndIdCategoriaNot(
                        nombre, categoria.getIdCategoria());
        if (nombreDuplicado) {
            throw new IllegalArgumentException("Ya existe una categoría con ese nombre.");
        }

        if (categoria.getIdCategoria() == null) {
            categoria.setNombreCategoria(nombre);
            return categoriaRepository.save(categoria);
        }

        Categoria existente = obtenerCategoriaObligatoria(categoria.getIdCategoria());
        existente.setNombreCategoria(nombre);
        existente.setDescripcion(categoria.getDescripcion());
        return categoriaRepository.save(existente);
    }

    @Override
    public Categoria obtenerCategoriaPorId(Long id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void eliminarCategoria(Long id) {
        Categoria categoria = obtenerCategoriaObligatoria(id);
        if (productoRepository.existsByCategoriaIdCategoria(id)) {
            throw new IllegalStateException(
                    "No se puede eliminar la categoría porque tiene productos asociados.");
        }
        categoriaRepository.delete(categoria);
    }

    private Categoria obtenerCategoriaObligatoria(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La categoría no existe."));
    }
}
