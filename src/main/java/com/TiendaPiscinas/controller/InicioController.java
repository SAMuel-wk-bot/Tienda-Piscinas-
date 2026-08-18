package com.TiendaPiscinas.controller;

import com.TiendaPiscinas.model.Producto;
import com.TiendaPiscinas.repository.ProductoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {
    private final ProductoRepository productoRepository;

    public InicioController(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("productos", productoRepository.findTop8ByActivoTrueOrderByDestacadoDescNombreAsc());
        return "index";
    }
}
