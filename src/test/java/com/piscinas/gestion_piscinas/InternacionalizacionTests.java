package com.piscinas.gestion_piscinas;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class InternacionalizacionTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void espanolEsElIdiomaPredeterminado() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Iniciar sesión")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Correo electrónico")));
    }

    @Test
    void cambioAInglesPermaneceEnLaSesion() throws Exception {
        MvcResult resultado = mockMvc.perform(get("/?lang=en"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString(
                        "Everything you need to care for your pool")))
                .andReturn();

        MockHttpSession sesion = (MockHttpSession) resultado.getRequest().getSession(false);

        mockMvc.perform(get("/login").session(sesion))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Sign in")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Email address")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Language")));
    }

    @Test
    void validacionesVisiblesSeTraducenAlIngles() throws Exception {
        MvcResult resultado = mockMvc.perform(get("/registro?lang=en"))
                .andExpect(status().isOk())
                .andReturn();
        MockHttpSession sesion = (MockHttpSession) resultado.getRequest().getSession(false);

        mockMvc.perform(post("/registro")
                .session(sesion)
                .with(csrf())
                .param("nombre", "")
                .param("apellido", "")
                .param("correo", "")
                .param("telefono", "")
                .param("direccion", "")
                .param("contrasena", "")
                .param("confirmarContrasena", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("seguridad/registro"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("First name is required.")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Password is required.")));
    }

    @Test
    void confirmacionDelCarritoSeTraduceAlIngles() throws Exception {
        mockMvc.perform(get("/carrito?lang=en")
                .with(user("cliente@tiendapiscinas.test").roles("CLIENTE"))
                .flashAttr("exito", "message.cart.emptied"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString(
                        "Cart emptied successfully.")));
    }

    @Test
    void errorDeNegocioDelCarritoSeTraduceAlIngles() throws Exception {
        mockMvc.perform(get("/carrito?lang=en")
                .with(user("cliente@tiendapiscinas.test").roles("CLIENTE"))
                .flashAttr("error", "business.cart.stockExceeded"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString(
                        "The requested quantity exceeds the available stock.")));
    }
}
