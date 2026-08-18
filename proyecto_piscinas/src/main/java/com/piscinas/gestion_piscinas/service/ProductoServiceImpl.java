package com.piscinas.gestion_piscinas.service;

import com.piscinas.gestion_piscinas.domain.Producto;
import com.piscinas.gestion_piscinas.domain.Categoria;
import com.piscinas.gestion_piscinas.repository.CategoriaRepository;
import com.piscinas.gestion_piscinas.repository.DetallePedidoRepository;
import com.piscinas.gestion_piscinas.repository.ProductoRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final DetallePedidoRepository detallePedidoRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository,
            CategoriaRepository categoriaRepository,
            DetallePedidoRepository detallePedidoRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.detallePedidoRepository = detallePedidoRepository;
    }

    @Override
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    @Override
    public List<Producto> buscarProductos(String nombre, Long idCategoria,
            boolean soloDisponibles) {
        String nombreNormalizado = nombre == null || nombre.isBlank()
                ? null : nombre.trim();

        if (nombreNormalizado != null && idCategoria == null && !soloDisponibles) {
            return productoRepository
                    .findByNombreProductoContainingIgnoreCaseOrderByNombreProductoAsc(
                            nombreNormalizado);
        }
        if (nombreNormalizado == null && idCategoria != null && !soloDisponibles) {
            return productoRepository
                    .findByCategoriaIdCategoriaOrderByNombreProductoAsc(idCategoria);
        }
        return productoRepository.buscarConFiltros(
                nombreNormalizado, idCategoria, soloDisponibles);
    }

    @Override
    @Transactional
    public Producto guardarProducto(Producto producto) {
        Long idCategoria = producto.getCategoria() == null
                ? null : producto.getCategoria().getIdCategoria();
        if (idCategoria == null) {
            throw new IllegalArgumentException("business.product.categoryNotFound");
        }
        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new IllegalArgumentException("business.product.categoryNotFound"));

        if (producto.getIdProducto() == null) {
            producto.setNombreProducto(producto.getNombreProducto().trim());
            producto.setCategoria(categoria);
            return productoRepository.save(producto);
        }

        Producto existente = obtenerProductoObligatorio(producto.getIdProducto());
        existente.setNombreProducto(producto.getNombreProducto().trim());
        existente.setDescripcion(producto.getDescripcion());
        existente.setPrecio(producto.getPrecio());
        existente.setStock(producto.getStock());
        existente.setCategoria(categoria);
        return productoRepository.save(existente);
    }

    @Override
    public Producto obtenerProductoPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void eliminarProducto(Long id) {
        Producto producto = obtenerProductoObligatorio(id);
        if (detallePedidoRepository.existsByProductoIdProducto(id)) {
            throw new IllegalStateException("business.product.associated");
        }
        productoRepository.delete(producto);
    }

    private Producto obtenerProductoObligatorio(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("business.product.notFound"));
    }
}
