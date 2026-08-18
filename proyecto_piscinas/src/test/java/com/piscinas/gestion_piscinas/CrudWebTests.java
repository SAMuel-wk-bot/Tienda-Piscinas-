package com.piscinas.gestion_piscinas;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CrudWebTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void catalogoPublicoRenderizaConThymeleaf() throws Exception {
        mockMvc.perform(get("/productos"))
                .andExpect(status().isOk())
                .andExpect(view().name("productos/listado"));
    }

    @Test
    void administradorPuedeRenderizarListadosCrud() throws Exception {
        mockMvc.perform(get("/administracion/categorias")
                .with(user("admin@tiendapiscinas.test").roles("ADMINISTRADOR")))
                .andExpect(status().isOk())
                .andExpect(view().name("categorias/listado"));

        mockMvc.perform(get("/administracion/productos")
                .with(user("admin@tiendapiscinas.test").roles("ADMINISTRADOR")))
                .andExpect(status().isOk())
                .andExpect(view().name("productos/administracion"));
    }

    @Test
    void formulariosRechazanDatosInvalidos() throws Exception {
        mockMvc.perform(post("/administracion/categorias/guardar")
                .with(user("admin@tiendapiscinas.test").roles("ADMINISTRADOR"))
                .with(csrf())
                .param("nombreCategoria", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("categorias/formulario"))
                .andExpect(model().attributeHasFieldErrors("categoria", "nombreCategoria"));

        mockMvc.perform(post("/administracion/productos/guardar")
                .with(user("admin@tiendapiscinas.test").roles("ADMINISTRADOR"))
                .with(csrf())
                .param("nombreProducto", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("productos/formulario"))
                .andExpect(model().attributeHasFieldErrors("producto",
                        "nombreProducto", "precio", "categoria"));
    }
}
