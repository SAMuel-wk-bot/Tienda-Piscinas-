package com.piscinas.gestion_piscinas.service;

import com.piscinas.gestion_piscinas.domain.Usuario;
import com.piscinas.gestion_piscinas.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetallesService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDetallesService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmailUsuarioIgnoreCase(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado."));

        String[] autoridades = usuario.getRoles().stream()
                .map(rol -> "ROLE_" + rol.getNombre())
                .toArray(String[]::new);

        return User.withUsername(usuario.getEmailUsuario())
                .password(usuario.getContrasena())
                .authorities(autoridades)
                .disabled(!usuario.isActivo())
                .build();
    }
}
