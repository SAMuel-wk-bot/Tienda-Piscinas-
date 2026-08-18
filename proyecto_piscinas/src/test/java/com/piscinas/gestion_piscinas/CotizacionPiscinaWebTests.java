package com.piscinas.gestion_piscinas;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class CotizacionPiscinaWebTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void cotizadorEsPublicoYRenderizaFormulario() throws Exception {
        mockMvc.perform(get("/cotizador"))
                .andExpect(status().isOk())
                .andExpect(view().name("cotizacion/formulario"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString(
                        "Cotización estimada para su piscina")));
    }

    @Test
    void calculoValidoMuestraVolumenYAdvertencia() throws Exception {
        mockMvc.perform(post("/cotizador")
                .with(csrf())
                .param("largo", "10")
                .param("ancho", "4")
                .param("profundidadPromedio", "1.25")
                .param("estadoAgua", "VERDE"))
                .andExpect(status().isOk())
                .andExpect(view().name("cotizacion/formulario"))
                .andExpect(model().attributeExists("cotizacionResultado"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("50,000 L")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString(
                        "no un diagnóstico ni una dosificación química")));
    }

    @Test
    void dimensionesInvalidasRegresanAlFormularioConMensajes() throws Exception {
        mockMvc.perform(post("/cotizador")
                .with(csrf())
                .param("largo", "")
                .param("ancho", "0.10")
                .param("profundidadPromedio", "")
                .param("estadoAgua", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("cotizacion/formulario"))
                .andExpect(model().hasErrors())
                .andExpect(content().string(org.hamcrest.Matchers.containsString(
                        "El largo es obligatorio.")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString(
                        "El ancho debe estar entre 0.50 y 30 metros.")));
    }

    @Test
    void cotizadorTambienSePresentaEnIngles() throws Exception {
        mockMvc.perform(get("/cotizador?lang=en"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString(
                        "Estimated quote for your pool")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString(
                        "Main water condition or need")));
    }
}
