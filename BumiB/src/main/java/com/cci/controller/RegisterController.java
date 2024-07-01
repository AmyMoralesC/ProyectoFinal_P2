/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cci.controller;

import com.cci.data.ServicioUsuario;
import com.cci.model.Usuario;
import java.io.Serializable;
import java.util.Random;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.jboss.weld.context.RequestContext;

/**
 *
 * @author maule
 */
@ManagedBean(name = "registerController")
@SessionScoped
public class RegisterController implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Usuario usuario = new Usuario();
    private ServicioUsuario servicioUsuario = new ServicioUsuario();

    private String codigoVerificacion;
    private boolean camposValidados;
    private String codigoIngresado;
    private boolean codigoVerificado;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void verificarUsuario() {
        
        generarCodigo();
        enviarCorreoConCodigo();
        
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Verificación requerida", "Se ha enviado un código de verificación a su correo."));
    }

    public void generarCodigo() {
        Random random = new Random();
        int codigo = 100000 + random.nextInt(900000);
        codigoVerificacion = String.valueOf(codigo);
    }

    private void enviarCorreoConCodigo() {
        String correoUsuario = usuario.getCorreo(); // Obtén el correo del usuario
        String asunto = "Código de Verificación";

        // Contenido del correo
        String contenido = codigoVerificacion;

        // Envía el correo usando tu clase EmailSender
        EmailSender emailSender = new EmailSender();
        emailSender.enviarCorreo(correoUsuario, contenido);
    }

    public void registrarUsuario() {
        if (codigoIngresado != null && codigoIngresado.equals(codigoVerificacion)) {
            codigoVerificado = true;
            // Si el código es correcto, registrar el usuario
            boolean isInserted = servicioUsuario.insertar(this.getUsuario());

            if (isInserted) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Registro exitoso", "El usuario ha sido registrado correctamente."));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Error en el registro", "No se pudo registrar el usuario. Inténtalo de nuevo."));
            }

        } else {
            codigoVerificado = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Código incorrecto", "El código de verificación ingresado no es válido."));
        }
    }

    public String getCodigoVerificacion() {
        return codigoVerificacion;
    }

    public void setCodigoVerificacion(String codigoVerificacion) {
        this.codigoVerificacion = codigoVerificacion;
    }

    public boolean isCamposValidados() {
        return camposValidados;
    }

    public void setCamposValidados(boolean camposValidados) {
        this.camposValidados = camposValidados;
    }

    public String getCodigoIngresado() {
        return codigoIngresado;
    }

    public void setCodigoIngresado(String codigoIngresado) {
        this.codigoIngresado = codigoIngresado;
    }
}