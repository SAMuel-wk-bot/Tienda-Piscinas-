package com.piscinas.gestion_piscinas.controller;

import com.piscinas.gestion_piscinas.domain.Categoria;
import com.piscinas.gestion_piscinas.service.CategoriaService;
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
@RequestMapping("/administracion/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("categorias", categoriaService.listarCategorias());
        return "categorias/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categorias/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model,
            RedirectAttributes mensajes) {
        Categoria categoria = categoriaService.obtenerCategoriaPorId(id);
        if (categoria == null) {
            mensajes.addFlashAttribute("error", "La categoría solicitada no existe.");
            return "redirect:/administracion/categorias";
        }
        model.addAttribute("categoria", categoria);
        return "categorias/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Categoria categoria, BindingResult resultado,
            RedirectAttributes mensajes) {
        if (resultado.hasErrors()) {
            return "categorias/formulario";
        }
        try {
            categoriaService.guardarCategoria(categoria);
            mensajes.addFlashAttribute("exito", "message.category.saved");
            return "redirect:/administracion/categorias";
        } catch (IllegalArgumentException ex) {
            resultado.rejectValue("nombreCategoria", "categoria.invalida", ex.getMessage());
            return "categorias/formulario";
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes mensajes) {
        try {
            categoriaService.eliminarCategoria(id);
            mensajes.addFlashAttribute("exito", "message.category.deleted");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            mensajes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/administracion/categorias";
    }
}
