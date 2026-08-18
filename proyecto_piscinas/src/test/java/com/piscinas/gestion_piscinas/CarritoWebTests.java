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
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CarritoWebTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CarritoService carritoService;

    @Test
    void clienteAgregaYVisualizaCarritoSinPoderCambiarElPrecio() throws Exception {
        Categoria categoria = categoriaService.guardarCategoria(
                new Categoria("Carrito web", "Categoría de prueba"));
        Producto producto = productoService.guardarProducto(new Producto(
                "Producto web", "Prueba", new BigDecimal("7250.00"), 2, categoria));
        MockHttpSession session = new MockHttpSession();

        mockMvc.perform(post("/carrito/agregar/{id}", producto.getIdProducto())
                .with(user("cliente@tiendapiscinas.test").roles("CLIENTE"))
                .with(csrf())
                .session(session)
                .param("cantidad", "1")
                .param("precio", "0.01"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/carrito"));

        assertThat(carritoService.obtenerResumen(session).getSubtotal())
                .isEqualByComparingTo("7250.00");

        mockMvc.perform(get("/carrito")
                .with(user("cliente@tiendapiscinas.test").roles("CLIENTE"))
                .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("carrito/ver"));
    }
}
