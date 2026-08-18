package com.piscinas.gestion_piscinas;

import com.piscinas.gestion_piscinas.domain.Categoria;
import com.piscinas.gestion_piscinas.domain.Producto;
import com.piscinas.gestion_piscinas.service.CategoriaService;
import com.piscinas.gestion_piscinas.service.ProductoService;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class BusquedaProductoTests {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ProductoService productoService;

    private Categoria quimicos;
    private Categoria accesorios;

    @BeforeEach
    void prepararProductos() {
        quimicos = categoriaService.guardarCategoria(
                new Categoria("Químicos filtro", "Prueba de filtros"));
        accesorios = categoriaService.guardarCategoria(
                new Categoria("Accesorios filtro", "Prueba de filtros"));

        productoService.guardarProducto(new Producto("Cloro granulado filtro", "Prueba",
                new BigDecimal("5000.00"), 5, quimicos));
        productoService.guardarProducto(new Producto("Cloro agotado filtro", "Prueba",
                new BigDecimal("4500.00"), 0, quimicos));
        productoService.guardarProducto(new Producto("Cepillo filtro", "Prueba",
                new BigDecimal("2500.00"), 3, accesorios));
    }

    @Test
    void buscaPorNombreConConsultaDerivada() {
        List<Producto> resultado = productoService.buscarProductos("cloro", null, false);

        assertThat(resultado).extracting(Producto::getNombreProducto)
                .containsExactly("Cloro agotado filtro", "Cloro granulado filtro");
    }

    @Test
    void filtraPorCategoriaConConsultaDerivada() {
        List<Producto> resultado = productoService.buscarProductos(
                null, accesorios.getIdCategoria(), false);

        assertThat(resultado).extracting(Producto::getNombreProducto)
                .containsExactly("Cepillo filtro");
    }

    @Test
    void combinaNombreCategoriaYDisponibilidadConJpql() {
        List<Producto> resultado = productoService.buscarProductos(
                "cloro", quimicos.getIdCategoria(), true);

        assertThat(resultado).extracting(Producto::getNombreProducto)
                .containsExactly("Cloro granulado filtro");
        assertThat(resultado).allMatch(producto -> producto.getStock() > 0);
    }
}
