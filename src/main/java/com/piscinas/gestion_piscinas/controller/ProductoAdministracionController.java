package com.piscinas.gestion_piscinas.controller;

import com.piscinas.gestion_piscinas.domain.Producto;
import com.piscinas.gestion_piscinas.service.CategoriaService;
import com.piscinas.gestion_piscinas.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/administracion/productos")
public class ProductoAdministracionController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    public ProductoAdministracionController(ProductoService productoService,
            CategoriaService categoriaService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("productos", productoService.listarProductos());
        return "productos/administracion";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("producto", new Producto());
        cargarCategorias(model);
        return "productos/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model,
            RedirectAttributes mensajes) {
        Producto producto = productoService.obtenerProductoPorId(id);
        if (producto == null) {
            mensajes.addFlashAttribute("error", "business.product.notFound");
            return "redirect:/administracion/productos";
        }
        model.addAttribute("producto", producto);
        cargarCategorias(model);
        return "productos/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Producto producto, BindingResult resultado,
            Model model, RedirectAttributes mensajes) {
        if (resultado.hasErrors()) {
            cargarCategorias(model);
            return "productos/formulario";
        }
        try {
            productoService.guardarProducto(producto);
            mensajes.addFlashAttribute("exito", "message.product.saved");
            return "redirect:/administracion/productos";
        } catch (IllegalArgumentException ex) {
            resultado.rejectValue("categoria", ex.getMessage());
            cargarCategorias(model);
            return "productos/formulario";
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes mensajes) {
        try {
            productoService.eliminarProducto(id);
            mensajes.addFlashAttribute("exito", "message.product.deleted");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            mensajes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/administracion/productos";
    }

    private void cargarCategorias(Model model) {
        model.addAttribute("categorias", categoriaService.listarCategorias());
    }
}
