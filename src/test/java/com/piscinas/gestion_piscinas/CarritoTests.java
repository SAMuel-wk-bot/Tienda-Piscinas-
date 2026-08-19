package com.piscinas.gestion_piscinas;

import com.piscinas.gestion_piscinas.domain.Categoria;
import com.piscinas.gestion_piscinas.domain.Producto;
import com.piscinas.gestion_piscinas.domain.ResumenCarrito;
import com.piscinas.gestion_piscinas.service.CarritoService;
import com.piscinas.gestion_piscinas.service.CategoriaService;
import com.piscinas.gestion_piscinas.service.ProductoService;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class CarritoTests {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ProductoService productoService;

    private Producto producto;
    private MockHttpSession session;

    @BeforeEach
    void prepararProducto() {
        Categoria categoria = categoriaService.guardarCategoria(
                new Categoria("Carrito prueba", "Categoría de prueba"));
        producto = productoService.guardarProducto(new Producto(
                "Producto carrito", "Producto de prueba",
                new BigDecimal("3000.00"), 3, categoria));
        session = new MockHttpSession();
    }

    @Test
    void agregaProductoYCalculaSubtotalConPrecioDelServidor() {
        carritoService.agregarProducto(producto.getIdProducto(), 2, session);

        ResumenCarrito resumen = carritoService.obtenerResumen(session);
        assertThat(resumen.getTotalUnidades()).isEqualTo(2);
        assertThat(resumen.getSubtotal()).isEqualByComparingTo("6000.00");
        assertThat(resumen.getItems()).singleElement()
                .satisfies(item -> assertThat(item.getProducto().getPrecio())
                .isEqualByComparingTo("3000.00"));
    }

    @Test
    void rechazaCantidadesInvalidasYSuperioresAlStock() {
        assertThatThrownBy(() -> carritoService.agregarProducto(
                producto.getIdProducto(), 0, session))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> carritoService.agregarProducto(
                producto.getIdProducto(), 4, session))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("business.cart.stockExceeded");
        assertThat(carritoService.obtenerResumen(session).isVacio()).isTrue();
    }

    @Test
    void aumentaReduceEliminaYVaciaCarrito() {
        carritoService.agregarProducto(producto.getIdProducto(), 1, session);
        carritoService.aumentarProducto(producto.getIdProducto(), session);
        assertThat(carritoService.obtenerResumen(session).getTotalUnidades()).isEqualTo(2);

        carritoService.reducirProducto(producto.getIdProducto(), session);
        assertThat(carritoService.obtenerResumen(session).getTotalUnidades()).isEqualTo(1);

        carritoService.eliminarProducto(producto.getIdProducto(), session);
        assertThat(carritoService.obtenerResumen(session).isVacio()).isTrue();

        carritoService.agregarProducto(producto.getIdProducto(), 1, session);
        carritoService.vaciar(session);
        assertThat(carritoService.obtenerResumen(session).isVacio()).isTrue();
    }

    @Test
    void detectaCambioDeStockPosterior() {
        carritoService.agregarProducto(producto.getIdProducto(), 3, session);
        producto.setStock(1);
        productoService.guardarProducto(producto);

        assertThat(carritoService.obtenerResumen(session).isStockValido()).isFalse();
    }
}
