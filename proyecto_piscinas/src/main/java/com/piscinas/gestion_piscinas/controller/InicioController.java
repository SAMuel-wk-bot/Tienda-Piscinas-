package com.piscinas.gestion_piscinas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {

    @GetMapping("/")
    public String inicio() {
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
