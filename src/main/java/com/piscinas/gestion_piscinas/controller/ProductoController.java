package com.piscinas.gestion_piscinas.controller;

import com.piscinas.gestion_piscinas.domain.Producto;
import com.piscinas.gestion_piscinas.service.CategoriaService;
import com.piscinas.gestion_piscinas.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Map;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private static final Map<String, String> IMAGENES_PRODUCTOS = Map.ofEntries(
            Map.entry("Cloro granulado 5 kg", "/img/productos/quimicos.jpg"),
            Map.entry("Tabletas de cloro 3 pulgadas", "/img/productos/quimicos.jpg"),
            Map.entry("Regulador de pH Plus 2 kg", "/img/productos/quimicos.jpg"),
            Map.entry("Alguicida concentrado 1 L", "/img/productos/alguicida.webp"),
            Map.entry("Clarificador líquido 1 L", "/img/productos/quimicos.jpg"),
            Map.entry("Kit de análisis de agua", "/img/productos/limpieza.jpg"),
            Map.entry("Red recogehojas reforzada", "/img/productos/limpieza.jpg"),
            Map.entry("Cepillo curvo 45 cm", "/img/productos/limpieza.jpg"),
            Map.entry("Bomba centrífuga 1 HP", "/img/productos/equipos.jpg"),
            Map.entry("Filtro de arena 20 pulgadas", "/img/productos/equipos.jpg"),
            Map.entry("Luz LED sumergible RGB", "/img/productos/equipos.jpg"),
            Map.entry("Cobertor térmico 4 x 8 m", "/img/productos/cobertor.jpg")
    );

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
        model.addAttribute("imagenesProductos", IMAGENES_PRODUCTOS);
        return "productos/listado";
    }
}
