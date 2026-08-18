package com.piscinas.gestion_piscinas.controller;

import com.piscinas.gestion_piscinas.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administracion")
public class AdministracionController {

    private final UsuarioService usuarioService;

    public AdministracionController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String inicio() {
        return "administracion/inicio";
    }

    @GetMapping("/usuarios")
    public String usuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarUsuarios());
        return "administracion/usuarios";
    }
}
