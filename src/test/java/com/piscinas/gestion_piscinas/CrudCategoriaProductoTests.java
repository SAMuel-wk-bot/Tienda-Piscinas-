package com.piscinas.gestion_piscinas;

import com.piscinas.gestion_piscinas.domain.Categoria;
import com.piscinas.gestion_piscinas.domain.Producto;
import com.piscinas.gestion_piscinas.repository.ProductoRepository;
import com.piscinas.gestion_piscinas.service.CategoriaService;
import com.piscinas.gestion_piscinas.service.ProductoService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class CrudCategoriaProductoTests {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoRepository productoRepository;

    @Test
    void rechazaNombreDeCategoriaDuplicadoSinImportarMayusculas() {
        categoriaService.guardarCategoria(new Categoria("Limpieza", "Productos de limpieza"));

        assertThatThrownBy(() -> categoriaService.guardarCategoria(
                new Categoria("limpieza", "Nombre repetido")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("business.category.duplicate");
    }

    @Test
    void impideEliminarCategoriaConProductosAsociados() {
        Categoria categoria = categoriaService.guardarCategoria(
                new Categoria("Accesorios prueba", "Categoría utilizada por producto"));
        productoService.guardarProducto(new Producto("Cepillo de prueba", "Cepillo",
                new BigDecimal("2500.00"), 4, categoria));

        assertThatThrownBy(() -> categoriaService.eliminarCategoria(categoria.getIdCategoria()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("business.category.associated");
    }

    @Test
    void creaYActualizaProductoConCategoriaPersistida() {
        Categoria categoria = categoriaService.guardarCategoria(
                new Categoria("Químicos prueba", "Tratamiento del agua"));
        Producto guardado = productoService.guardarProducto(new Producto(
                "Cloro prueba", "Producto inicial", new BigDecimal("4500.00"), 8, categoria));
        productoRepository.flush();
        LocalDateTime fechaOriginal = guardado.getFechaIngreso();

        Producto cambios = new Producto("Cloro actualizado", "Descripción actualizada",
                new BigDecimal("4750.00"), 10, categoria);
        cambios.setIdProducto(guardado.getIdProducto());
        Producto actualizado = productoService.guardarProducto(cambios);

        assertThat(actualizado.getNombreProducto()).isEqualTo("Cloro actualizado");
        assertThat(actualizado.getStock()).isEqualTo(10);
        assertThat(actualizado.getFechaIngreso()).isEqualTo(fechaOriginal);
    }

    @Test
    void rechazaProductoConCategoriaInexistente() {
        Categoria categoriaInexistente = new Categoria("No persistida", "Prueba");
        categoriaInexistente.setIdCategoria(999999L);
        Producto producto = new Producto("Producto inválido", "Prueba",
                BigDecimal.ONE, 1, categoriaInexistente);

        assertThatThrownBy(() -> productoService.guardarProducto(producto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("business.product.categoryNotFound");
    }
}
