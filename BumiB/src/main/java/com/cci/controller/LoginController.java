/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cci.controller;

import com.cci.data.ServicioUsuario;
import com.cci.model.Usuario;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author jared
 */
@ManagedBean(name = "loginController")
@SessionScoped
public class LoginController implements Serializable {

    List<Usuario> listaUsuarioTO = new ArrayList<>();
    private Usuario usuario = new Usuario();
    private Usuario selectedUsuario;

    private boolean permisoActivo;
    private boolean permisoSemiActivo;

    public LoginController() {
    }

    public Boolean daPermiso() {

        return true;

    }

    public void ingresar() {
        ServicioUsuario servicioUsuario = new ServicioUsuario();
        Usuario usuarioRetorno = servicioUsuario.Validar(this.getUsuario().getCorreo(), this.getUsuario().getClave());
        if (usuarioRetorno != null) {
            this.usuario = usuarioRetorno;
            this.listaUsuarioTO = servicioUsuario.buscarTodos();
            String estado = this.usuario.getEstado();
        switch (estado) {
            case "Activo":
                this.permisoActivo = true;
                this.permisoSemiActivo = true;
                break;
            case "SemiActivo":
                this.permisoActivo = false;
                this.permisoSemiActivo = true;
                break;
            default:
                this.permisoActivo = false;
                this.permisoSemiActivo = false;
                break;
        }
            this.redireccionar("/paginaPrincipal.xhtml");
        } else {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campos inválidos", "La clave o correo no son correctos"));
        }
    }

    public void ingresarRegistro() {
        this.redireccionar("/register.xhtml");
    }

    public void ingresarLogin() {
        this.redireccionar("/index.xhtml");
    }

    public void redireccionar(String ruta) {
        HttpServletRequest request;
        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath() + ruta);
        } catch (IOException e) {
        }
    }

    public void openNew() {

    }

    public void saveUser() {

    }

    public void deleteUser() {

    }

    public void editUser() {

    }

    public List<Usuario> getListaUsuarioTO() {
        return listaUsuarioTO;
    }

    public void setListaUsuarioTO(List<Usuario> listaUsuarioTO) {
        this.listaUsuarioTO = listaUsuarioTO;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getSelectedUsuario() {
        return selectedUsuario;
    }

    public void setSelectedUsuario(Usuario selectedUsuario) {
        this.selectedUsuario = selectedUsuario;
    }

    public boolean isPermisoActivo() {
        return permisoActivo;
    }

    public void setPermisoActivo(boolean permisoActivo) {
        this.permisoActivo = permisoActivo;
    }

    public boolean isPermisoSemiActivo() {
        return permisoSemiActivo;
    }

    public void setPermisoSemiActivo(boolean permisoSemiActivo) {
        this.permisoSemiActivo = permisoSemiActivo;
    }

}
