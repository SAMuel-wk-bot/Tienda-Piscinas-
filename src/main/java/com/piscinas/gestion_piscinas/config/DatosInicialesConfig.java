package com.piscinas.gestion_piscinas.config;

import com.piscinas.gestion_piscinas.domain.Cliente;
import com.piscinas.gestion_piscinas.domain.Categoria;
import com.piscinas.gestion_piscinas.domain.Producto;
import com.piscinas.gestion_piscinas.domain.Rol;
import com.piscinas.gestion_piscinas.domain.Usuario;
import com.piscinas.gestion_piscinas.repository.ClienteRepository;
import com.piscinas.gestion_piscinas.repository.CategoriaRepository;
import com.piscinas.gestion_piscinas.repository.ProductoRepository;
import com.piscinas.gestion_piscinas.repository.RolRepository;
import com.piscinas.gestion_piscinas.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Configuration
public class DatosInicialesConfig {

    @Bean
    public CommandLineRunner inicializarSeguridad(RolRepository rolRepository,
            UsuarioRepository usuarioRepository,
            ClienteRepository clienteRepository,
            CategoriaRepository categoriaRepository,
            ProductoRepository productoRepository,
            Environment environment,
            PasswordEncoder passwordEncoder) {
        return argumentos -> crearDatosDemostracion(rolRepository, usuarioRepository,
                clienteRepository, categoriaRepository, productoRepository,
                environment.acceptsProfiles(Profiles.of("local")), passwordEncoder);
    }

    @Transactional
    public void crearDatosDemostracion(RolRepository rolRepository,
            UsuarioRepository usuarioRepository,
            ClienteRepository clienteRepository,
            CategoriaRepository categoriaRepository,
            ProductoRepository productoRepository,
            boolean cargarCatalogo,
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

        if (cargarCatalogo && productoRepository.count() == 0) {
            Categoria quimicos = obtenerCategoria(categoriaRepository, "Químicos",
                    "Productos para tratamiento y balance del agua");
            Categoria limpieza = obtenerCategoria(categoriaRepository, "Limpieza",
                    "Herramientas para el mantenimiento de la piscina");
            Categoria equipos = obtenerCategoria(categoriaRepository, "Equipos",
                    "Bombas, filtros y sistemas para circulación del agua");
            Categoria accesorios = obtenerCategoria(categoriaRepository, "Accesorios",
                    "Complementos de seguridad, iluminación y recreación");

            productoRepository.save(new Producto("Cloro granulado 5 kg", "Desinfectante de disolución rápida para piscinas.", new BigDecimal("18900.00"), 25, quimicos));
            productoRepository.save(new Producto("Tabletas de cloro 3 pulgadas", "Presentación de 5 kg para dosificación prolongada.", new BigDecimal("23500.00"), 18, quimicos));
            productoRepository.save(new Producto("Regulador de pH Plus 2 kg", "Incrementa y estabiliza el nivel de pH del agua.", new BigDecimal("8900.00"), 30, quimicos));
            productoRepository.save(new Producto("Alguicida concentrado 1 L", "Previene y elimina la formación de algas.", new BigDecimal("7250.00"), 22, quimicos));
            productoRepository.save(new Producto("Clarificador líquido 1 L", "Ayuda a recuperar la transparencia del agua.", new BigDecimal("6500.00"), 20, quimicos));
            productoRepository.save(new Producto("Kit de análisis de agua", "Mide cloro y pH mediante reactivos de fácil lectura.", new BigDecimal("12900.00"), 15, limpieza));
            productoRepository.save(new Producto("Red recogehojas reforzada", "Malla profunda con mango compatible universal.", new BigDecimal("9800.00"), 12, limpieza));
            productoRepository.save(new Producto("Cepillo curvo 45 cm", "Cepillo de alta resistencia para paredes y fondo.", new BigDecimal("11200.00"), 14, limpieza));
            productoRepository.save(new Producto("Bomba centrífuga 1 HP", "Bomba silenciosa para circulación y filtrado residencial.", new BigDecimal("185000.00"), 6, equipos));
            productoRepository.save(new Producto("Filtro de arena 20 pulgadas", "Filtro de alto rendimiento con válvula multipuerto.", new BigDecimal("245000.00"), 4, equipos));
            productoRepository.save(new Producto("Luz LED sumergible RGB", "Iluminación multicolor de bajo consumo con control remoto.", new BigDecimal("49900.00"), 10, accesorios));
            productoRepository.save(new Producto("Cobertor térmico 4 x 8 m", "Reduce la evaporación y conserva la temperatura del agua.", new BigDecimal("89900.00"), 7, accesorios));
        }
    }

    private Categoria obtenerCategoria(CategoriaRepository categoriaRepository,
            String nombre, String descripcion) {
        return categoriaRepository.findByNombreCategoriaIgnoreCase(nombre)
                .orElseGet(() -> categoriaRepository.save(new Categoria(nombre, descripcion)));
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
