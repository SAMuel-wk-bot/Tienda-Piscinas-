package com.piscinas.gestion_piscinas.controller;

import com.piscinas.gestion_piscinas.domain.SolicitudServicioForm;
import com.piscinas.gestion_piscinas.service.ServicioService;
import com.piscinas.gestion_piscinas.service.SolicitudServicioService;
import jakarta.validation.Valid;
import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cliente/solicitudes")
public class SolicitudServicioController {

    private final SolicitudServicioService solicitudService;
    private final ServicioService servicioService;

    public SolicitudServicioController(SolicitudServicioService solicitudService,
            ServicioService servicioService) {
        this.solicitudService = solicitudService;
        this.servicioService = servicioService;
    }

    @GetMapping
    public String listar(Principal principal, Model model) {
        model.addAttribute("solicitudes",
                solicitudService.listarSolicitudesDelCliente(principal.getName()));
        return "solicitudes/cliente-listado";
    }

    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("solicitudForm", new SolicitudServicioForm());
        cargarServicios(model);
        return "solicitudes/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid SolicitudServicioForm solicitudForm,
            BindingResult resultado, Principal principal, Model model,
            RedirectAttributes mensajes) {
        if (resultado.hasErrors()) {
            cargarServicios(model);
            return "solicitudes/formulario";
        }
        try {
            solicitudService.crearSolicitud(solicitudForm, principal.getName());
            mensajes.addFlashAttribute("exito", "message.request.created");
            return "redirect:/cliente/solicitudes";
        } catch (IllegalArgumentException ex) {
            resultado.reject("solicitud.invalida", ex.getMessage());
            cargarServicios(model);
            return "solicitudes/formulario";
        }
    }

    private void cargarServicios(Model model) {
        model.addAttribute("servicios", servicioService.listarServiciosActivos());
    }
}
