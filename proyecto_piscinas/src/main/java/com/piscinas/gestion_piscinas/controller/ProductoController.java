package com.piscinas.gestion_piscinas.controller;

import com.piscinas.gestion_piscinas.domain.Producto;
import com.piscinas.gestion_piscinas.service.CategoriaService;
import com.piscinas.gestion_piscinas.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    public ProductoController(ProductoService productoService,
            CategoriaService categoriaService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String listarProductos(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Long categoria,
            @RequestParam(defaultValue = "false") boolean disponibles,
            Model model) {
        model.addAttribute("titulo", "Catálogo de productos");
        model.addAttribute("listaProductos",
                productoService.buscarProductos(nombre, categoria, disponibles));
        model.addAttribute("listaCategorias", categoriaService.listarCategorias());
        model.addAttribute("nombreBuscado", nombre);
        model.addAttribute("categoriaSeleccionada", categoria);
        model.addAttribute("soloDisponibles", disponibles);
        return "productos/listado";
    }
}
