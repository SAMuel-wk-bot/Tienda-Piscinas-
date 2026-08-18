package com.piscinas.gestion_piscinas;

import com.piscinas.gestion_piscinas.domain.CarritoSesion;
import com.piscinas.gestion_piscinas.domain.Categoria;
import com.piscinas.gestion_piscinas.domain.DetallePedido;
import com.piscinas.gestion_piscinas.domain.EstadoPedido;
import com.piscinas.gestion_piscinas.domain.Pedido;
import com.piscinas.gestion_piscinas.domain.Producto;
import com.piscinas.gestion_piscinas.repository.DetallePedidoRepository;
import com.piscinas.gestion_piscinas.repository.PedidoRepository;
import com.piscinas.gestion_piscinas.service.CategoriaService;
import com.piscinas.gestion_piscinas.service.PedidoService;
import com.piscinas.gestion_piscinas.service.ProductoService;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class PedidoTransaccionTests {

    private static final String CORREO_CLIENTE = "cliente@tiendapiscinas.test";

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoRepository detalleRepository;

    @Test
    void compraExitosaCreaPedidoDetallesTotalesYDescuentaStock() {
        Categoria categoria = categoriaService.guardarCategoria(
                new Categoria("Pedido exitoso", "Prueba transaccional"));
        Producto productoA = guardarProducto("Producto pedido A", "10000.00", 5, categoria);
        Producto productoB = guardarProducto("Producto pedido B", "5000.00", 4, categoria);
        CarritoSesion carrito = new CarritoSesion();
        carrito.getCantidades().put(productoA.getIdProducto(), 2);
        carrito.getCantidades().put(productoB.getIdProducto(), 1);

        Pedido pedido = pedidoService.finalizarCompra(carrito, CORREO_CLIENTE);
        List<DetallePedido> detalles = pedidoService.obtenerDetalles(pedido.getIdPedido());

        assertThat(pedido.getEstado()).isEqualTo(EstadoPedido.PENDIENTE);
        assertThat(pedido.getSubtotal()).isEqualByComparingTo("25000.00");
        assertThat(pedido.getImpuesto()).isEqualByComparingTo("3250.00");
        assertThat(pedido.getTotal()).isEqualByComparingTo("28250.00");
        assertThat(detalles).hasSize(2);
        assertThat(detalles).extracting(DetallePedido::getPrecioUnitario)
                .containsExactlyInAnyOrder(new BigDecimal("10000.00"), new BigDecimal("5000.00"));
        assertThat(productoService.obtenerProductoPorId(productoA.getIdProducto()).getStock())
                .isEqualTo(3);
        assertThat(productoService.obtenerProductoPorId(productoB.getIdProducto()).getStock())
                .isEqualTo(3);
    }

    @Test
    void inventarioInsuficienteNoDejaRegistrosParcialesNiAlteraStock() {
        Categoria categoria = categoriaService.guardarCategoria(
                new Categoria("Pedido rollback", "Prueba sin parciales"));
        Producto producto = guardarProducto("Producto sin stock", "8000.00", 1, categoria);
        CarritoSesion carrito = new CarritoSesion();
        carrito.getCantidades().put(producto.getIdProducto(), 2);
        long pedidosAntes = pedidoRepository.count();
        long detallesAntes = detalleRepository.count();

        assertThatThrownBy(() -> pedidoService.finalizarCompra(carrito, CORREO_CLIENTE))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Inventario insuficiente");

        assertThat(pedidoRepository.count()).isEqualTo(pedidosAntes);
        assertThat(detalleRepository.count()).isEqualTo(detallesAntes);
        assertThat(productoService.obtenerProductoPorId(producto.getIdProducto()).getStock())
                .isEqualTo(1);
    }

    @Test
    void clienteNoPuedeConsultarPedidoAjeno() {
        Categoria categoria = categoriaService.guardarCategoria(
                new Categoria("Pedido propiedad", "Prueba de pertenencia"));
        Producto producto = guardarProducto("Producto propiedad", "2000.00", 2, categoria);
        CarritoSesion carrito = new CarritoSesion();
        carrito.getCantidades().put(producto.getIdProducto(), 1);
        Pedido pedido = pedidoService.finalizarCompra(carrito, CORREO_CLIENTE);

        assertThatThrownBy(() -> pedidoService.obtenerPedidoDelCliente(
                pedido.getIdPedido(), "otro@tiendapiscinas.test"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("no pertenece");
    }

    private Producto guardarProducto(String nombre, String precio, int stock,
            Categoria categoria) {
        return productoService.guardarProducto(new Producto(nombre, "Prueba",
                new BigDecimal(precio), stock, categoria));
    }
}
