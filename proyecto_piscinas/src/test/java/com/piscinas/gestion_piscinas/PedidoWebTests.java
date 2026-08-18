package com.piscinas.gestion_piscinas;

import com.piscinas.gestion_piscinas.domain.Categoria;
import com.piscinas.gestion_piscinas.domain.Producto;
import com.piscinas.gestion_piscinas.service.CarritoService;
import com.piscinas.gestion_piscinas.service.CategoriaService;
import com.piscinas.gestion_piscinas.service.ProductoService;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PedidoWebTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ProductoService productoService;

    @Test
    void confirmarPedidoVaciaCarritoYRenderizaDetallePropio() throws Exception {
        Categoria categoria = categoriaService.guardarCategoria(
                new Categoria("Pedido web", "Prueba web"));
        Producto producto = productoService.guardarProducto(new Producto(
                "Producto pedido web", "Prueba", new BigDecimal("4000.00"), 2, categoria));
        MockHttpSession session = new MockHttpSession();
        carritoService.agregarProducto(producto.getIdProducto(), 1, session);

        MvcResult resultado = mockMvc.perform(post("/cliente/pedidos/confirmar")
                .with(user("cliente@tiendapiscinas.test").roles("CLIENTE"))
                .with(csrf())
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        String destino = resultado.getResponse().getRedirectedUrl();
        assertThat(destino).startsWith("/cliente/pedidos/");
        assertThat(carritoService.obtenerResumen(session).isVacio()).isTrue();

        mockMvc.perform(get(destino)
                .with(user("cliente@tiendapiscinas.test").roles("CLIENTE"))
                .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("pedidos/detalle"));
    }
}
