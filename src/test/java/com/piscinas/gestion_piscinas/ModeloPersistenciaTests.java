package com.piscinas.gestion_piscinas;

import com.piscinas.gestion_piscinas.domain.Categoria;
import com.piscinas.gestion_piscinas.domain.Cliente;
import com.piscinas.gestion_piscinas.domain.DetallePedido;
import com.piscinas.gestion_piscinas.domain.EstadoPedido;
import com.piscinas.gestion_piscinas.domain.EstadoSolicitudServicio;
import com.piscinas.gestion_piscinas.domain.Pedido;
import com.piscinas.gestion_piscinas.domain.Producto;
import com.piscinas.gestion_piscinas.domain.Rol;
import com.piscinas.gestion_piscinas.domain.Servicio;
import com.piscinas.gestion_piscinas.domain.SolicitudServicio;
import com.piscinas.gestion_piscinas.domain.Usuario;
import com.piscinas.gestion_piscinas.repository.CategoriaRepository;
import com.piscinas.gestion_piscinas.repository.ClienteRepository;
import com.piscinas.gestion_piscinas.repository.DetallePedidoRepository;
import com.piscinas.gestion_piscinas.repository.PedidoRepository;
import com.piscinas.gestion_piscinas.repository.ProductoRepository;
import com.piscinas.gestion_piscinas.repository.RolRepository;
import com.piscinas.gestion_piscinas.repository.ServicioRepository;
import com.piscinas.gestion_piscinas.repository.SolicitudServicioRepository;
import com.piscinas.gestion_piscinas.repository.UsuarioRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class ModeloPersistenciaTests {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private SolicitudServicioRepository solicitudServicioRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Test
    void persisteRelacionesDelSegundoCincuentaPorCiento() {
        Rol rolCliente = rolRepository.save(new Rol("CLIENTE"));

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario("Cliente");
        usuario.setApellidoUsuario("Demostración");
        usuario.setEmailUsuario("cliente.modelo@demo.local");
        usuario.setTelefonoUsuario("0000-0000");
        usuario.setContrasena("hash-demostrativo-no-real");
        usuario.getRoles().add(rolCliente);
        usuario = usuarioRepository.save(usuario);

        Cliente cliente = new Cliente();
        cliente.setUsuario(usuario);
        cliente.setDireccion("Dirección académica de demostración");
        cliente = clienteRepository.save(cliente);

        Categoria categoria = new Categoria("Químicos", "Tratamiento del agua");
        categoria = categoriaRepository.save(categoria);

        Producto producto = new Producto("Cloro de prueba", "Producto académico",
                new BigDecimal("10.00"), 5, categoria);
        producto = productoRepository.save(producto);

        Servicio servicio = new Servicio();
        servicio.setNombre("Limpieza");
        servicio.setDescripcion("Limpieza general de piscina");
        servicio.setPrecioBase(new BigDecimal("25.00"));
        servicio = servicioRepository.save(servicio);

        SolicitudServicio solicitud = new SolicitudServicio();
        solicitud.setCliente(cliente);
        solicitud.setServicio(servicio);
        solicitud.setDireccionServicio(cliente.getDireccion());
        solicitud.setObservaciones("Prueba de relación JPA");
        solicitud = solicitudServicioRepository.save(solicitud);

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setSubtotal(new BigDecimal("20.00"));
        pedido.setImpuesto(new BigDecimal("2.60"));
        pedido.setTotal(new BigDecimal("22.60"));
        pedido = pedidoRepository.save(pedido);

        DetallePedido detalle = new DetallePedido();
        detalle.setPedido(pedido);
        detalle.setProducto(producto);
        detalle.setCantidad(2);
        detalle.setPrecioUnitario(new BigDecimal("10.00"));
        detalle.setSubtotalLinea(new BigDecimal("20.00"));
        detalle = detallePedidoRepository.save(detalle);

        assertNotNull(usuario.getIdUsuario());
        assertTrue(usuarioRepository.existsByEmailUsuarioIgnoreCase("CLIENTE.MODELO@DEMO.LOCAL"));
        assertEquals("CLIENTE", cliente.getUsuario().getRoles().iterator().next().getNombre());
        assertEquals(EstadoSolicitudServicio.PENDIENTE, solicitud.getEstado());
        assertEquals(EstadoPedido.PENDIENTE, pedido.getEstado());
        assertEquals(new BigDecimal("20.00"), detalle.getSubtotalLinea());
        assertEquals(1, detallePedidoRepository.findByPedidoIdPedido(pedido.getIdPedido()).size());
    }
}
