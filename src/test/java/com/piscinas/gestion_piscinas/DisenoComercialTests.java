package com.piscinas.gestion_piscinas;

import com.piscinas.gestion_piscinas.domain.Categoria;
import com.piscinas.gestion_piscinas.domain.Producto;
import com.piscinas.gestion_piscinas.domain.Servicio;
import com.piscinas.gestion_piscinas.repository.CategoriaRepository;
import com.piscinas.gestion_piscinas.repository.ProductoRepository;
import com.piscinas.gestion_piscinas.repository.ServicioRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class DisenoComercialTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Test
    void portadaMuestraDatosRealesDelCatalogoYAccionPorRol() throws Exception {
        Categoria categoria = categoriaRepository.save(
                new Categoria("Iluminación comercial", "Accesorios de iluminación"));
        productoRepository.save(new Producto("Lámpara sumergible comercial",
                "Iluminación demostrativa para piscina", new BigDecimal("18900.00"),
                4, categoria));

        Servicio servicio = new Servicio();
        servicio.setNombre("Revisión técnica comercial");
        servicio.setDescripcion("Revisión demostrativa de equipos");
        servicio.setPrecioBase(new BigDecimal("25000.00"));
        servicio.setActivo(true);
        servicioRepository.save(servicio);

        mockMvc.perform(get("/")
                .with(user("cliente@tiendapiscinas.test").roles("CLIENTE")))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString(
                        "Lámpara sumergible comercial")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString(
                        "Revisión técnica comercial")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString(
                        "Agregar al carrito")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString(
                        "/css/estilos.css")));
    }

    @Test
    void hojaDeEstilosComercialEsAccesiblePublicamente() throws Exception {
        mockMvc.perform(get("/css/estilos.css"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString(".hero-piscina")));
    }
}
