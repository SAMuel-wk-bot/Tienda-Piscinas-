package com.piscinas.gestion_piscinas.controller;

import com.piscinas.gestion_piscinas.domain.Producto;
import com.piscinas.gestion_piscinas.service.CategoriaService;
import com.piscinas.gestion_piscinas.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;
    
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public String listarProductos(Model model) {
        model.addAttribute("titulo", "Listado de Productos - Inventario");
        model.addAttribute("listaProductos", productoService.listarProductos());
        return "productos/listado"; 
    }
}