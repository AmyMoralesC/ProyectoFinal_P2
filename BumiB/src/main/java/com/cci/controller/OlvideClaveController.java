/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cci.controller;

import com.cci.data.ServicioUsuario;
import com.cci.model.Usuario;
import java.util.UUID;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author maule
 */
@ManagedBean(name = "olvideContraController")
@SessionScoped
public class OlvideClaveController {

    private String correo;
    private String nuevaContraseña;
    private String token;
    private ServicioUsuario servicioUsuario = new ServicioUsuario();

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNuevaContraseña() {
        return nuevaContraseña;
    }

    public void setNuevaContraseña(String nuevaContrasena) {
        this.nuevaContraseña = nuevaContrasena;
    }

    public void enviarCorreoRestablecimiento() {
        Usuario usuario = servicioUsuario.buscarPorCorreo(correo);
        if (usuario != null) {
            token = UUID.randomUUID().toString(); // Generar un token único
            servicioUsuario.guardarToken(correo, token); // Guardar token en la base de datos
            String enlace = generarEnlaceRestablecimiento(token);
            enviarCorreoConEnlace(correo, enlace);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Correo enviado", "Se ha enviado un enlace para restablecer tu contraseña."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Correo no encontrado", "El correo ingresado no está registrado."));
        }
    }

    private String generarEnlaceRestablecimiento(String token) {
        return "http://localhost:8080/BumiB/recuperarCuenta.xhtml?token=" + token;
    }

    private void enviarCorreoConEnlace(String correo, String enlace) {
        EmailSender emailSender = new EmailSender();
        emailSender.enviarCorreoConEnlace(correo, enlace);
    }

    public void restablecerContraseña() {
        if (servicioUsuario.validarToken(correo, token)) {
            Usuario usuario = servicioUsuario.buscarPorCorreo(correo);
            if (usuario != null) {
                usuario.setClave(nuevaContraseña);
                servicioUsuario.actualizar(usuario);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Contraseña restablecida", "Tu contraseña ha sido restablecida exitosamente."));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Error", "No se pudo actualizar la contraseña."));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Token inválido", "El enlace de restablecimiento de contraseña no es válido."));
        }
    }

}
