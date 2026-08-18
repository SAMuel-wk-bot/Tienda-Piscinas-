package com.piscinas.gestion_piscinas;

import com.piscinas.gestion_piscinas.domain.RegistroUsuario;
import com.piscinas.gestion_piscinas.domain.Usuario;
import com.piscinas.gestion_piscinas.repository.ClienteRepository;
import com.piscinas.gestion_piscinas.repository.UsuarioRepository;
import com.piscinas.gestion_piscinas.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class RegistroUsuarioTests {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void registraClienteConRolFijoYContrasenaBCrypt() {
        RegistroUsuario registro = crearRegistro("nuevo@tiendapiscinas.test");

        Usuario guardado = usuarioService.registrarCliente(registro);

        assertThat(guardado.getIdUsuario()).isNotNull();
        assertThat(guardado.getContrasena()).isNotEqualTo(registro.getContrasena());
        assertThat(passwordEncoder.matches(registro.getContrasena(), guardado.getContrasena())).isTrue();
        assertThat(guardado.getRoles()).extracting("nombre").containsExactly("CLIENTE");
        assertThat(clienteRepository.findByUsuarioEmailUsuario(registro.getCorreo())).isPresent();
    }

    @Test
    void rechazaCorreoDuplicado() {
        RegistroUsuario registro = crearRegistro("duplicado@tiendapiscinas.test");
        usuarioService.registrarCliente(registro);

        assertThatThrownBy(() -> usuarioService.registrarCliente(registro))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("correo");
        assertThat(usuarioRepository.findAll()).filteredOn(usuario ->
                usuario.getEmailUsuario().equals(registro.getCorreo())).hasSize(1);
    }

    private RegistroUsuario crearRegistro(String correo) {
        RegistroUsuario registro = new RegistroUsuario();
        registro.setNombre("Prueba");
        registro.setApellido("Registro");
        registro.setCorreo(correo);
        registro.setTelefono("8888-9999");
        registro.setDireccion("Dirección académica de prueba");
        registro.setContrasena("ClaveSegura2026!");
        registro.setConfirmarContrasena("ClaveSegura2026!");
        return registro;
    }
}
