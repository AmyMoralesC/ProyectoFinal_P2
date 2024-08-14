/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cci.controller;

import com.cci.data.ServicioUsuario;
import com.cci.model.Usuario;
import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author jared
 */
@ManagedBean(name = "configuracionController")
@SessionScoped
public class ConfiguracionController {

    @ManagedProperty(value = "#{loginController}")
    private LoginController loginController;

    @ManagedProperty(value = "#{servicioUsuario}")
    private ServicioUsuario servicioUsuario;

    private Usuario usuario;

    public void eliminarCuenta() throws IOException {
        Usuario usuarioActual = loginController.getUsuario();
        if (usuarioActual != null) {
            boolean resultado = servicioUsuario.eliminar(usuarioActual.getId());
            if (resultado) {
                // Si la eliminación es exitosa, agregar un mensaje y redirigir al index
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cuenta eliminada correctamente", ""));
                FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            } else {
                // Si la eliminación falla, mostrar un mensaje de error
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo eliminar la cuenta", ""));
            }
        }
    }

    public void cambiarContraseña() {
        Usuario usuarioActual = loginController.getUsuario();
        if (usuarioActual != null) {
            servicioUsuario.actualizar(usuarioActual);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Perfil actualizado correctamente", ""));
            loginController.setUsuario(usuarioActual);
        }
    }

    public void init() {
        if (loginController != null && loginController.getUsuario() != null) {
            this.usuario = loginController.getUsuario();
        } else {
            this.usuario = new Usuario();
        }
    }

    public void UsuarioConfiguracion() {
        System.out.println("Usuario en configuracionController: " + loginController.getUsuario().getNombre());
    }
    // Getters y Setters

    public LoginController getLoginController() {
        return loginController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
        init();
    }

    public ServicioUsuario getServicioUsuario() {
        return servicioUsuario;
    }

    public void setServicioUsuario(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }
}
