/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cci.controller;

import com.cci.data.ServicioUsuario;
import com.cci.model.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Amy
 */
@ManagedBean(name = "menuController")
@SessionScoped
public class MenuController implements Serializable {

    private Usuario usuario = new Usuario();
    private List<Usuario> listaUsuario = new ArrayList<>();
    private Usuario selectedUsuario;
    @ManagedProperty(value = "#{loginController}")
    private LoginController loginController;

    public MenuController() {
    }

    @PostConstruct
    public void init() {
        ServicioUsuario servicioUsuario = new ServicioUsuario();
        this.listaUsuario = servicioUsuario.buscarTodos();

    }

    public void UsuarioMenu() {
        System.out.println("Inicio de UsuarioMenu()");
        Usuario usuarioDesdeLogin = loginController.getUsuario();
        this.usuario = usuarioDesdeLogin;
        if (usuarioDesdeLogin != null) {
            System.out.println("Usuario en loginController: " + usuarioDesdeLogin.getNombre());
            System.out.println("Usuario en menuController: " + this.usuario.getNombre());
        } else {
            System.out.println("usuarioDesdeLogin es nulo.");
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuarioTO(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getListaUsuarioTO() {
        return listaUsuario;
    }

    public void setListaUsuario(List<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }

    public Usuario getSelectedUsuario() {
        return selectedUsuario;
    }

    public void setSelectedUsuario(Usuario selectedUsuario) {
        this.selectedUsuario = selectedUsuario;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

}
