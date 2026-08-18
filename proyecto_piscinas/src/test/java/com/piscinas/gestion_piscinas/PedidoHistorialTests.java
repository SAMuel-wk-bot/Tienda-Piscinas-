package com.piscinas.gestion_piscinas;

import com.piscinas.gestion_piscinas.domain.CarritoSesion;
import com.piscinas.gestion_piscinas.domain.Categoria;
import com.piscinas.gestion_piscinas.domain.EstadoPedido;
import com.piscinas.gestion_piscinas.domain.Pedido;
import com.piscinas.gestion_piscinas.domain.Producto;
import com.piscinas.gestion_piscinas.service.CategoriaService;
import com.piscinas.gestion_piscinas.service.PedidoService;
import com.piscinas.gestion_piscinas.service.ProductoService;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PedidoHistorialTests {

    private static final String CORREO_CLIENTE = "cliente@tiendapiscinas.test";

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ProductoService productoService;

    @Test
    void historialDelClienteIncluyeSuPedido() {
        Pedido pedido = crearPedido("Historial cliente");

        assertThat(pedidoService.listarPedidosDelCliente(CORREO_CLIENTE))
                .extracting(Pedido::getIdPedido)
                .contains(pedido.getIdPedido());
    }

    @Test
    void administradorActualizaYFiltraEstado() {
        Pedido pedido = crearPedido("Historial administrador");

        Pedido actualizado = pedidoService.actualizarEstado(
                pedido.getIdPedido(), EstadoPedido.PREPARANDO);

        assertThat(actualizado.getEstado()).isEqualTo(EstadoPedido.PREPARANDO);
        assertThat(pedidoService.listarPedidos(EstadoPedido.PREPARANDO))
                .extracting(Pedido::getIdPedido)
                .contains(pedido.getIdPedido());
    }

    private Pedido crearPedido(String nombreCategoria) {
        Categoria categoria = categoriaService.guardarCategoria(
                new Categoria(nombreCategoria, "Prueba de historial"));
        Producto producto = productoService.guardarProducto(new Producto(
                "Producto " + nombreCategoria, "Prueba", new BigDecimal("3500.00"), 2, categoria));
        CarritoSesion carrito = new CarritoSesion();
        carrito.getCantidades().put(producto.getIdProducto(), 1);
        return pedidoService.finalizarCompra(carrito, CORREO_CLIENTE);
    }
}
