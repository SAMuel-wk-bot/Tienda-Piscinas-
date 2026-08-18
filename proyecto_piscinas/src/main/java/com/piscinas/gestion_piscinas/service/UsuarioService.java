package com.piscinas.gestion_piscinas.service;

import com.piscinas.gestion_piscinas.domain.RegistroUsuario;
import com.piscinas.gestion_piscinas.domain.Usuario;
import java.util.List;

public interface UsuarioService {

    List<Usuario> listarUsuarios();

    Usuario registrarCliente(RegistroUsuario registro);

    boolean existeCorreo(String correo);
}
