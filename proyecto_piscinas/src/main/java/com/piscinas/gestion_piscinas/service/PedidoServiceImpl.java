package com.piscinas.gestion_piscinas.service;

import com.piscinas.gestion_piscinas.domain.CarritoSesion;
import com.piscinas.gestion_piscinas.domain.Cliente;
import com.piscinas.gestion_piscinas.domain.DetallePedido;
import com.piscinas.gestion_piscinas.domain.EstadoPedido;
import com.piscinas.gestion_piscinas.domain.ItemCarrito;
import com.piscinas.gestion_piscinas.domain.Pedido;
import com.piscinas.gestion_piscinas.domain.Producto;
import com.piscinas.gestion_piscinas.repository.ClienteRepository;
import com.piscinas.gestion_piscinas.repository.DetallePedidoRepository;
import com.piscinas.gestion_piscinas.repository.PedidoRepository;
import com.piscinas.gestion_piscinas.repository.ProductoRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final DetallePedidoRepository detalleRepository;
    private final ProductoRepository productoRepository;
    private final ClienteRepository clienteRepository;
    private final BigDecimal tasaImpuesto;

    public PedidoServiceImpl(PedidoRepository pedidoRepository,
            DetallePedidoRepository detalleRepository,
            ProductoRepository productoRepository,
            ClienteRepository clienteRepository,
            @Value("${tienda.impuesto.tasa:0.13}") BigDecimal tasaImpuesto) {
        this.pedidoRepository = pedidoRepository;
        this.detalleRepository = detalleRepository;
        this.productoRepository = productoRepository;
        this.clienteRepository = clienteRepository;
        this.tasaImpuesto = tasaImpuesto;
    }

    @Override
    @Transactional
    public Pedido finalizarCompra(CarritoSesion carrito, String correoUsuario) {
        if (carrito == null || carrito.estaVacio()) {
            throw new IllegalStateException("El carrito está vacío.");
        }
        Cliente cliente = clienteRepository.findByUsuarioEmailUsuario(correoUsuario)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe un perfil de cliente para el usuario autenticado."));

        List<ItemCarrito> lineas = validarYPrepararLineas(carrito);
        BigDecimal subtotal = lineas.stream().map(ItemCarrito::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
        BigDecimal impuesto = subtotal.multiply(tasaImpuesto)
                .setScale(2, RoundingMode.HALF_UP);

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setSubtotal(subtotal);
        pedido.setImpuesto(impuesto);
        pedido.setTotal(subtotal.add(impuesto));
        pedido.setEstado(EstadoPedido.PENDIENTE);
        pedidoRepository.save(pedido);

        List<DetallePedido> detalles = new ArrayList<>();
        for (ItemCarrito linea : lineas) {
            Producto producto = linea.getProducto();
            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setProducto(producto);
            detalle.setCantidad(linea.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setSubtotalLinea(linea.getSubtotal());
            detalles.add(detalle);

            producto.setStock(producto.getStock() - linea.getCantidad());
            productoRepository.save(producto);
        }
        detalleRepository.saveAll(detalles);
        return pedido;
    }

    @Override
    public Pedido obtenerPedidoDelCliente(Long idPedido, String correoUsuario) {
        return pedidoRepository
                .findByIdPedidoAndClienteUsuarioEmailUsuario(idPedido, correoUsuario)
                .orElseThrow(() -> new IllegalArgumentException(
                        "El pedido no existe o no pertenece al cliente autenticado."));
    }

    @Override
    public List<DetallePedido> obtenerDetalles(Long idPedido) {
        return detalleRepository.findByPedidoIdPedido(idPedido);
    }

    private List<ItemCarrito> validarYPrepararLineas(CarritoSesion carrito) {
        List<ItemCarrito> lineas = new ArrayList<>();
        for (Map.Entry<Long, Integer> entrada : carrito.getCantidades().entrySet()) {
            Integer cantidad = entrada.getValue();
            if (cantidad == null || cantidad <= 0) {
                throw new IllegalArgumentException("El carrito contiene una cantidad inválida.");
            }
            Producto producto = productoRepository
                    .buscarPorIdParaActualizar(entrada.getKey())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Uno de los productos del carrito ya no existe."));
            if (producto.getStock() < cantidad) {
                throw new IllegalStateException(
                        "Inventario insuficiente para " + producto.getNombreProducto() + ".");
            }
            lineas.add(new ItemCarrito(producto, cantidad));
        }
        return lineas;
    }
}
