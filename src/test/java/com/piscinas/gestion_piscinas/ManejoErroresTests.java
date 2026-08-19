package com.piscinas.gestion_piscinas;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class ManejoErroresTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void clienteRecibe403ConPaginaPersonalizadaAlAbrirAdministracion() throws Exception {
        mockMvc.perform(get("/administracion")
                .with(user("cliente@tiendapiscinas.test").roles("CLIENTE")))
                .andExpect(status().isForbidden())
                .andExpect(forwardedUrl("/error/403"));
    }

    @Test
    void rutaInexistenteDevuelve404() throws Exception {
        mockMvc.perform(get("/recurso-que-no-existe")
                .with(user("cliente@tiendapiscinas.test").roles("CLIENTE")))
                .andExpect(status().isNotFound());
    }

    @Test
    void paginasDeErrorSeRenderizanConMensajeComprensible() throws Exception {
        mockMvc.perform(get("/error/403"))
                .andExpect(status().isOk())
                .andExpect(view().name("error/403"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Acceso denegado")));

        mockMvc.perform(get("/error/404"))
                .andExpect(status().isOk())
                .andExpect(view().name("error/404"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("No encontramos")));

        mockMvc.perform(get("/error/500"))
                .andExpect(status().isOk())
                .andExpect(view().name("error/500"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("error inesperado")));
    }
}
