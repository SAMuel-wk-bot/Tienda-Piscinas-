package com.piscinas.gestion_piscinas.controller;

import com.piscinas.gestion_piscinas.domain.Pedido;
import com.piscinas.gestion_piscinas.service.CarritoService;
import com.piscinas.gestion_piscinas.service.PedidoService;
import jakarta.servlet.http.HttpSession;
import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cliente/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final CarritoService carritoService;

    public PedidoController(PedidoService pedidoService, CarritoService carritoService) {
        this.pedidoService = pedidoService;
        this.carritoService = carritoService;
    }

    @GetMapping
    public String historial(Principal principal, Model model) {
        model.addAttribute("pedidos",
                pedidoService.listarPedidosDelCliente(principal.getName()));
        return "pedidos/cliente-listado";
    }

    @PostMapping("/confirmar")
    public String confirmar(HttpSession session, Principal principal,
            RedirectAttributes mensajes) {
        try {
            Pedido pedido = pedidoService.finalizarCompra(
                    carritoService.obtenerCarrito(session), principal.getName());
            carritoService.vaciar(session);
            mensajes.addFlashAttribute("exito", "message.order.created");
            return "redirect:/cliente/pedidos/" + pedido.getIdPedido();
        } catch (IllegalArgumentException | IllegalStateException ex) {
            mensajes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/carrito";
        }
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Principal principal, Model model,
            RedirectAttributes mensajes) {
        try {
            Pedido pedido = pedidoService.obtenerPedidoDelCliente(id, principal.getName());
            model.addAttribute("pedido", pedido);
            model.addAttribute("detalles", pedidoService.obtenerDetalles(id));
            return "pedidos/detalle";
        } catch (IllegalArgumentException ex) {
            mensajes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/cliente/pedidos";
        }
    }
}
