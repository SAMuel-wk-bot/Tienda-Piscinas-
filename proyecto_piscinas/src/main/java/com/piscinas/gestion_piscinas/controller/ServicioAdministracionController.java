package com.piscinas.gestion_piscinas.controller;

import com.piscinas.gestion_piscinas.domain.Servicio;
import com.piscinas.gestion_piscinas.service.ServicioService;
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
@RequestMapping("/administracion/servicios")
public class ServicioAdministracionController {

    private final ServicioService servicioService;

    public ServicioAdministracionController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("servicios", servicioService.listarServicios());
        return "servicios/administracion";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("servicio", new Servicio());
        return "servicios/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model,
            RedirectAttributes mensajes) {
        Servicio servicio = servicioService.obtenerServicioPorId(id);
        if (servicio == null) {
            mensajes.addFlashAttribute("error", "El servicio solicitado no existe.");
            return "redirect:/administracion/servicios";
        }
        model.addAttribute("servicio", servicio);
        return "servicios/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Servicio servicio, BindingResult resultado,
            RedirectAttributes mensajes) {
        if (resultado.hasErrors()) {
            return "servicios/formulario";
        }
        try {
            servicioService.guardarServicio(servicio);
            mensajes.addFlashAttribute("exito", "message.service.saved");
            return "redirect:/administracion/servicios";
        } catch (IllegalArgumentException ex) {
            resultado.reject("servicio.invalido", ex.getMessage());
            return "servicios/formulario";
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes mensajes) {
        try {
            servicioService.eliminarServicio(id);
            mensajes.addFlashAttribute("exito", "message.service.deleted");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            mensajes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/administracion/servicios";
    }
}
