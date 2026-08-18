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
class ServicioSolicitudWebTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void catalogoPublicoDeServiciosRenderiza() throws Exception {
        mockMvc.perform(get("/servicios"))
                .andExpect(status().isOk())
                .andExpect(view().name("servicios/listado"));
    }

    @Test
    void clientePuedeVerYCrearSusSolicitudes() throws Exception {
        mockMvc.perform(get("/cliente/solicitudes")
                .with(user("cliente@tiendapiscinas.test").roles("CLIENTE")))
                .andExpect(status().isOk())
                .andExpect(view().name("solicitudes/cliente-listado"));

        mockMvc.perform(get("/cliente/solicitudes/nueva")
                .with(user("cliente@tiendapiscinas.test").roles("CLIENTE")))
                .andExpect(status().isOk())
                .andExpect(view().name("solicitudes/formulario"));
    }

    @Test
    void administradorRenderizaServiciosYSolicitudes() throws Exception {
        mockMvc.perform(get("/administracion/servicios")
                .with(user("admin@tiendapiscinas.test").roles("ADMINISTRADOR")))
                .andExpect(status().isOk())
                .andExpect(view().name("servicios/administracion"));

        mockMvc.perform(get("/administracion/solicitudes")
                .with(user("admin@tiendapiscinas.test").roles("ADMINISTRADOR")))
                .andExpect(status().isOk())
                .andExpect(view().name("solicitudes/administracion"));
    }
}
