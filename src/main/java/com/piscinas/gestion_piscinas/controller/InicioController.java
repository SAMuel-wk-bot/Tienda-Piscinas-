package com.piscinas.gestion_piscinas.controller;

import com.piscinas.gestion_piscinas.service.CategoriaService;
import com.piscinas.gestion_piscinas.service.ProductoService;
import com.piscinas.gestion_piscinas.service.ServicioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;
    private final ServicioService servicioService;

    public InicioController(ProductoService productoService,
            CategoriaService categoriaService, ServicioService servicioService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
        this.servicioService = servicioService;
    }

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("productosDestacados", productoService.listarProductos().stream()
                .filter(producto -> producto.getStock() != null && producto.getStock() > 0)
                .limit(3)
                .toList());
        model.addAttribute("categoriasDestacadas", categoriaService.listarCategorias().stream()
                .limit(4)
                .toList());
        model.addAttribute("serviciosDestacados", servicioService.listarServiciosActivos().stream()
                .limit(3)
                .toList());
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "seguridad/login";
    }

    @GetMapping("/error/403")
    public String accesoDenegado() {
        return "error/403";
    }

    @GetMapping("/error/404")
    public String recursoNoEncontrado() {
        return "error/404";
    }

    @GetMapping("/error/500")
    public String errorInterno() {
        return "error/500";
    }
}
