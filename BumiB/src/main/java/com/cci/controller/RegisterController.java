/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cci.controller;

import com.cci.data.ServicioUsuario;
import com.cci.model.Usuario;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author maule
 */
@ManagedBean(name = "registerController")
@SessionScoped
public class RegisterController {

    private Usuario usuario = new Usuario();
    private ServicioUsuario servicioUsuario = new ServicioUsuario();

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void registrarUsuario() {
        boolean isInserted = servicioUsuario.insertar(this.getUsuario());
        if (isInserted) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro exitoso", "El usuario ha sido registrado correctamente."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en el registro", "No se pudo registrar el usuario. Inténtalo de nuevo."));
        }
    }

}
