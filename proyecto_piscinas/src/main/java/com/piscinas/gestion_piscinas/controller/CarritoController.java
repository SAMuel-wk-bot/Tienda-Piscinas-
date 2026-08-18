package com.piscinas.gestion_piscinas.controller;

import com.piscinas.gestion_piscinas.service.CarritoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @GetMapping
    public String verCarrito(HttpSession session, Model model) {
        model.addAttribute("resumen", carritoService.obtenerResumen(session));
        return "carrito/ver";
    }

    @PostMapping("/agregar/{id}")
    public String agregar(@PathVariable Long id,
            @RequestParam(defaultValue = "1") int cantidad,
            HttpSession session, RedirectAttributes mensajes) {
        ejecutarOperacion(() -> carritoService.agregarProducto(id, cantidad, session),
                "El producto se agregó al carrito.", mensajes);
        return "redirect:/carrito";
    }

    @PostMapping("/aumentar/{id}")
    public String aumentar(@PathVariable Long id, HttpSession session,
            RedirectAttributes mensajes) {
        ejecutarOperacion(() -> carritoService.aumentarProducto(id, session),
                "La cantidad se actualizó.", mensajes);
        return "redirect:/carrito";
    }

    @PostMapping("/reducir/{id}")
    public String reducir(@PathVariable Long id, HttpSession session,
            RedirectAttributes mensajes) {
        ejecutarOperacion(() -> carritoService.reducirProducto(id, session),
                "La cantidad se actualizó.", mensajes);
        return "redirect:/carrito";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, HttpSession session,
            RedirectAttributes mensajes) {
        carritoService.eliminarProducto(id, session);
        mensajes.addFlashAttribute("exito", "El producto se eliminó del carrito.");
        return "redirect:/carrito";
    }

    @PostMapping("/vaciar")
    public String vaciar(HttpSession session, RedirectAttributes mensajes) {
        carritoService.vaciar(session);
        mensajes.addFlashAttribute("exito", "El carrito se vació correctamente.");
        return "redirect:/carrito";
    }

    private void ejecutarOperacion(Runnable operacion, String mensajeExito,
            RedirectAttributes mensajes) {
        try {
            operacion.run();
            mensajes.addFlashAttribute("exito", mensajeExito);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            mensajes.addFlashAttribute("error", ex.getMessage());
        }
    }
}
