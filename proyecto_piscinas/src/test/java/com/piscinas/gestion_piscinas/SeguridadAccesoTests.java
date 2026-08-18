package com.piscinas.gestion_piscinas;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class SeguridadAccesoTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void rutaPublicaEsAccesibleSinLogin() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void rutaAdministrativaSinLoginSolicitaAutenticacion() throws Exception {
        mockMvc.perform(get("/administracion"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void clienteNoPuedeAbrirAdministracion() throws Exception {
        mockMvc.perform(get("/administracion")
                .with(user("cliente@tiendapiscinas.test").roles("CLIENTE")))
                .andExpect(status().isForbidden());
    }

    @Test
    void administradorPuedeAbrirAdministracion() throws Exception {
        mockMvc.perform(get("/administracion")
                .with(user("admin@tiendapiscinas.test").roles("ADMINISTRADOR")))
                .andExpect(status().isOk())
                .andExpect(view().name("administracion/inicio"));
    }

    @Test
    void clientePuedeAbrirSuZona() throws Exception {
        mockMvc.perform(get("/cliente")
                .with(user("cliente@tiendapiscinas.test").roles("CLIENTE")))
                .andExpect(status().isOk())
                .andExpect(view().name("cliente/inicio"));
    }

    @Test
    void zonaClienteSinLoginSolicitaAutenticacion() throws Exception {
        mockMvc.perform(get("/cliente"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void loginValidoAutenticaContraLaBaseDeDatos() throws Exception {
        mockMvc.perform(post("/login")
                .with(csrf())
                .param("correo", "cliente@tiendapiscinas.test")
                .param("contrasena", "ClientePiscinas2026!"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(authenticated().withUsername("cliente@tiendapiscinas.test"));
    }
}
