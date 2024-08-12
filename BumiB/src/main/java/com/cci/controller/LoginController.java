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
import org.primefaces.PrimeFaces;

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

    private int seguidorId;

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
            System.out.println("Usuario en loginController: " + usuario.getNombre());
        } else {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campos inválidos", "La clave o correo no son correctos"));
        }
    }

    public void followUser() {

        ServicioUsuario servicioUsuario = new ServicioUsuario();
        int seguidoId = this.selectedUsuario.getId(); // ID del usuario que está siendo seguido
        // `seguidorId` debe ser el ID del usuario actual logueado
        int seguidorId = this.usuario.getId();
        
        if (!servicioUsuario.seguirUsuario(seguidorId, seguidoId)) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se logró seguir al usuario", "No se logró seguir al usuario"));
        } else {
            // Actualiza la lista y mensajes
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario seguido"));
        }

        PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-usuarios");

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

    public void logout() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            facesContext.getExternalContext().invalidateSession();
            facesContext.getExternalContext().redirect(request.getContextPath() + "/index.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
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

    public int getSeguidorId() {
        return seguidorId;
    }

    public void setSeguidorId(int seguidorId) {
        this.seguidorId = seguidorId;
    }

}
