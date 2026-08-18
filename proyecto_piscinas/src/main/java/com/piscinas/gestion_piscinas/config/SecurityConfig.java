package com.piscinas.gestion_piscinas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(autorizar -> autorizar
                .requestMatchers("/", "/login", "/registro", "/webjars/**",
                        "/css/**", "/images/**", "/error/**").permitAll()
                .requestMatchers("/administracion/**", "/admin/**")
                        .hasRole("ADMINISTRADOR")
                .requestMatchers("/cliente/**", "/carrito/**")
                        .hasRole("CLIENTE")
                .requestMatchers("/productos", "/productos/detalle/**", "/servicios")
                        .permitAll()
                .anyRequest().authenticated()
                )
                .formLogin(formulario -> formulario
                .loginPage("/login")
                .usernameParameter("correo")
                .passwordParameter("contrasena")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error")
                .permitAll()
                )
                .logout(salida -> salida
                .logoutUrl("/logout")
                .logoutSuccessUrl("/?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
                )
                .exceptionHandling(errores -> errores
                .accessDeniedPage("/error/403")
                );

        return http.build();
    }
}
