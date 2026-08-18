package com.piscinas.gestion_piscinas.controller;

import com.piscinas.gestion_piscinas.domain.CotizacionPiscinaForm;
import com.piscinas.gestion_piscinas.domain.EstadoAgua;
import com.piscinas.gestion_piscinas.service.CotizacionPiscinaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cotizador")
public class CotizacionPiscinaController {

    private final CotizacionPiscinaService cotizacionService;

    public CotizacionPiscinaController(CotizacionPiscinaService cotizacionService) {
        this.cotizacionService = cotizacionService;
    }

    @GetMapping
    public String mostrar(Model model) {
        model.addAttribute("cotizacionForm", new CotizacionPiscinaForm());
        cargarEstados(model);
        return "cotizacion/formulario";
    }

    @PostMapping
    public String calcular(@Valid @ModelAttribute("cotizacionForm") CotizacionPiscinaForm formulario,
            BindingResult resultado, Model model) {
        cargarEstados(model);
        if (!resultado.hasErrors()) {
            model.addAttribute("cotizacionResultado", cotizacionService.calcular(formulario));
        }
        return "cotizacion/formulario";
    }

    private void cargarEstados(Model model) {
        model.addAttribute("estadosAgua", EstadoAgua.values());
    }
}
