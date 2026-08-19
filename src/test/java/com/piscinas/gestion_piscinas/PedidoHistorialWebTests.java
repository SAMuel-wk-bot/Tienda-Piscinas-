package com.piscinas.gestion_piscinas;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PedidoHistorialWebTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void clienteRenderizaSolamenteSuHistorial() throws Exception {
        mockMvc.perform(get("/cliente/pedidos")
                .with(user("cliente@tiendapiscinas.test").roles("CLIENTE")))
                .andExpect(status().isOk())
                .andExpect(view().name("pedidos/cliente-listado"));
    }

    @Test
    void administradorRenderizaGestionDePedidos() throws Exception {
        mockMvc.perform(get("/administracion/pedidos")
                .with(user("admin@tiendapiscinas.test").roles("ADMINISTRADOR")))
                .andExpect(status().isOk())
                .andExpect(view().name("pedidos/administracion"));
    }
}
