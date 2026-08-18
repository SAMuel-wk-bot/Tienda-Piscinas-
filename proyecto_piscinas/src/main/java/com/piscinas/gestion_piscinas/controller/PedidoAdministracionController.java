package com.piscinas.gestion_piscinas.controller;

import com.piscinas.gestion_piscinas.domain.EstadoPedido;
import com.piscinas.gestion_piscinas.domain.Pedido;
import com.piscinas.gestion_piscinas.service.PedidoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/administracion/pedidos")
public class PedidoAdministracionController {

    private final PedidoService pedidoService;

    public PedidoAdministracionController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) EstadoPedido estado,
            Model model) {
        model.addAttribute("pedidos", pedidoService.listarPedidos(estado));
        model.addAttribute("estados", EstadoPedido.values());
        model.addAttribute("estadoSeleccionado", estado);
        return "pedidos/administracion";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model,
            RedirectAttributes mensajes) {
        try {
            Pedido pedido = pedidoService.obtenerPedidoPorId(id);
            model.addAttribute("pedido", pedido);
            model.addAttribute("detalles", pedidoService.obtenerDetalles(id));
            model.addAttribute("vistaAdministrativa", true);
            return "pedidos/detalle";
        } catch (IllegalArgumentException ex) {
            mensajes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/administracion/pedidos";
        }
    }

    @PostMapping("/{id}/estado")
    public String actualizarEstado(@PathVariable Long id,
            @RequestParam EstadoPedido estado, RedirectAttributes mensajes) {
        try {
            pedidoService.actualizarEstado(id, estado);
            mensajes.addFlashAttribute("exito", "El estado del pedido se actualizó correctamente.");
        } catch (IllegalArgumentException ex) {
            mensajes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/administracion/pedidos";
    }
}
