package com.piscinas.gestion_piscinas.controller;

import com.piscinas.gestion_piscinas.domain.EstadoSolicitudServicio;
import com.piscinas.gestion_piscinas.service.SolicitudServicioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/administracion/solicitudes")
public class SolicitudAdministracionController {

    private final SolicitudServicioService solicitudService;

    public SolicitudAdministracionController(SolicitudServicioService solicitudService) {
        this.solicitudService = solicitudService;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) EstadoSolicitudServicio estado,
            Model model) {
        model.addAttribute("solicitudes", solicitudService.listarSolicitudes(estado));
        model.addAttribute("estados", EstadoSolicitudServicio.values());
        model.addAttribute("estadoSeleccionado", estado);
        return "solicitudes/administracion";
    }

    @PostMapping("/{id}/estado")
    public String actualizarEstado(@PathVariable Long id,
            @RequestParam EstadoSolicitudServicio estado,
            RedirectAttributes mensajes) {
        try {
            solicitudService.actualizarEstado(id, estado);
            mensajes.addFlashAttribute("exito", "message.request.status");
        } catch (IllegalArgumentException ex) {
            mensajes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/administracion/solicitudes";
    }
}
