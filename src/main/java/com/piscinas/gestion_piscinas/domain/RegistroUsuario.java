package com.piscinas.gestion_piscinas.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegistroUsuario {

    @NotBlank(message = "{validation.required.name}")
    @Size(max = 50, message = "{validation.size.name}")
    private String nombre;

    @NotBlank(message = "{validation.required.lastName}")
    @Size(max = 50, message = "{validation.size.lastName}")
    private String apellido;

    @NotBlank(message = "{validation.required.email}")
    @Email(message = "{validation.email}")
    @Size(max = 100, message = "{validation.size.email}")
    private String correo;

    @NotBlank(message = "{validation.required.phone}")
    @Size(max = 20, message = "{validation.size.phone}")
    private String telefono;

    @NotBlank(message = "{validation.required.address}")
    @Size(max = 250, message = "{validation.size.address}")
    private String direccion;

    @NotBlank(message = "{validation.required.password}")
    @Size(min = 8, max = 72, message = "{validation.size.password}")
    private String contrasena;

    @NotBlank(message = "{validation.required.passwordConfirmation}")
    private String confirmarContrasena;

    public RegistroUsuario() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getConfirmarContrasena() {
        return confirmarContrasena;
    }

    public void setConfirmarContrasena(String confirmarContrasena) {
        this.confirmarContrasena = confirmarContrasena;
    }
}
