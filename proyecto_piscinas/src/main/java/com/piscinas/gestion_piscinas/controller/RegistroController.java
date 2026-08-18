package com.piscinas.gestion_piscinas.controller;

import com.piscinas.gestion_piscinas.domain.RegistroUsuario;
import com.piscinas.gestion_piscinas.service.UsuarioService;
import jakarta.validation.Valid;
import java.util.Objects;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistroController {

    private final UsuarioService usuarioService;

    public RegistroController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("registro", new RegistroUsuario());
        return "seguridad/registro";
    }

    @PostMapping("/registro")
    public String registrar(@Valid @ModelAttribute("registro") RegistroUsuario registro,
            BindingResult resultado) {
        if (!Objects.equals(registro.getContrasena(), registro.getConfirmarContrasena())) {
            resultado.rejectValue("confirmarContrasena", "contrasena.diferente",
                    "Las contraseñas no coinciden.");
        }
        if (usuarioService.existeCorreo(registro.getCorreo())) {
            resultado.rejectValue("correo", "correo.duplicado",
                    "Ya existe una cuenta con ese correo.");
        }
        if (resultado.hasErrors()) {
            return "seguridad/registro";
        }

        usuarioService.registrarCliente(registro);
        return "redirect:/login?registro";
    }
}
