package com.piscinas.gestion_piscinas.config;

import com.piscinas.gestion_piscinas.domain.Cliente;
import com.piscinas.gestion_piscinas.domain.Rol;
import com.piscinas.gestion_piscinas.domain.Usuario;
import com.piscinas.gestion_piscinas.repository.ClienteRepository;
import com.piscinas.gestion_piscinas.repository.RolRepository;
import com.piscinas.gestion_piscinas.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class DatosInicialesConfig {

    @Bean
    public CommandLineRunner inicializarSeguridad(RolRepository rolRepository,
            UsuarioRepository usuarioRepository,
            ClienteRepository clienteRepository,
            PasswordEncoder passwordEncoder) {
        return argumentos -> crearDatosDemostracion(rolRepository, usuarioRepository,
                clienteRepository, passwordEncoder);
    }

    @Transactional
    public void crearDatosDemostracion(RolRepository rolRepository,
            UsuarioRepository usuarioRepository,
            ClienteRepository clienteRepository,
            PasswordEncoder passwordEncoder) {
        Rol administrador = rolRepository.findByNombre("ADMINISTRADOR")
                .orElseGet(() -> rolRepository.save(new Rol("ADMINISTRADOR")));
        Rol cliente = rolRepository.findByNombre("CLIENTE")
                .orElseGet(() -> rolRepository.save(new Rol("CLIENTE")));

        if (!usuarioRepository.existsByEmailUsuarioIgnoreCase("admin@tiendapiscinas.test")) {
            Usuario usuarioAdmin = crearUsuario("Administrador", "Demostración",
                    "admin@tiendapiscinas.test", "8888-0001",
                    "AdminPiscinas2026!", administrador, passwordEncoder);
            usuarioRepository.save(usuarioAdmin);
        }

        if (!usuarioRepository.existsByEmailUsuarioIgnoreCase("cliente@tiendapiscinas.test")) {
            Usuario usuarioCliente = crearUsuario("Cliente", "Demostración",
                    "cliente@tiendapiscinas.test", "8888-0002",
                    "ClientePiscinas2026!", cliente, passwordEncoder);
            usuarioRepository.save(usuarioCliente);

            Cliente perfilCliente = new Cliente();
            perfilCliente.setUsuario(usuarioCliente);
            perfilCliente.setDireccion("Dirección ficticia para demostración académica");
            clienteRepository.save(perfilCliente);
        }
    }

    private Usuario crearUsuario(String nombre, String apellido, String correo,
            String telefono, String contrasena, Rol rol, PasswordEncoder passwordEncoder) {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(nombre);
        usuario.setApellidoUsuario(apellido);
        usuario.setEmailUsuario(correo);
        usuario.setTelefonoUsuario(telefono);
        usuario.setContrasena(passwordEncoder.encode(contrasena));
        usuario.setActivo(true);
        usuario.getRoles().add(rol);
        return usuario;
    }
}
