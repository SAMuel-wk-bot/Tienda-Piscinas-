package com.piscinas.gestion_piscinas.service;

import com.piscinas.gestion_piscinas.domain.Cliente;
import com.piscinas.gestion_piscinas.domain.RegistroUsuario;
import com.piscinas.gestion_piscinas.domain.Rol;
import com.piscinas.gestion_piscinas.domain.Usuario;
import com.piscinas.gestion_piscinas.repository.ClienteRepository;
import com.piscinas.gestion_piscinas.repository.RolRepository;
import com.piscinas.gestion_piscinas.repository.UsuarioRepository;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,
            RolRepository rolRepository,
            ClienteRepository clienteRepository,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    @Transactional
    public Usuario registrarCliente(RegistroUsuario registro) {
        String correoNormalizado = registro.getCorreo().trim().toLowerCase();
        if (usuarioRepository.existsByEmailUsuarioIgnoreCase(correoNormalizado)) {
            throw new IllegalArgumentException("Ya existe un usuario con ese correo.");
        }

        Rol rolCliente = rolRepository.findByNombre("CLIENTE")
                .orElseGet(() -> rolRepository.save(new Rol("CLIENTE")));

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(registro.getNombre().trim());
        usuario.setApellidoUsuario(registro.getApellido().trim());
        usuario.setEmailUsuario(correoNormalizado);
        usuario.setTelefonoUsuario(registro.getTelefono().trim());
        usuario.setContrasena(passwordEncoder.encode(registro.getContrasena()));
        usuario.setActivo(true);
        usuario.getRoles().add(rolCliente);
        usuarioRepository.save(usuario);

        Cliente cliente = new Cliente();
        cliente.setUsuario(usuario);
        cliente.setDireccion(registro.getDireccion().trim());
        clienteRepository.save(cliente);

        return usuario;
    }

    @Override
    public boolean existeCorreo(String correo) {
        return correo != null && usuarioRepository.existsByEmailUsuarioIgnoreCase(correo.trim());
    }
}
