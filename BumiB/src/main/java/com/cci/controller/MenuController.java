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
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Amy
 */
@ManagedBean(name = "menuController")
@SessionScoped
public class MenuController implements Serializable {

    private Usuario usuarioTO = new Usuario();
    private List<Usuario> listaUsuarioTO = new ArrayList<>();
    private Usuario selectedUsuario;

    public MenuController() {
    }

    @PostConstruct
    public void init() {
        ServicioUsuario servicioUsuario = new ServicioUsuario();
        this.listaUsuarioTO = servicioUsuario.buscarTodos();

    }

    public Usuario getUsuarioTO() {
        return usuarioTO;
    }

    public void setUsuarioTO(Usuario usuarioTO) {
        this.usuarioTO = usuarioTO;
    }

    public List<Usuario> getListaUsuarioTO() {
        return listaUsuarioTO;
    }

    public void setListaUsuarioTO(List<Usuario> listaUsuarioTO) {
        this.listaUsuarioTO = listaUsuarioTO;
    }

    public Usuario getSelectedUsuario() {
        return selectedUsuario;
    }

    public void setSelectedUsuario(Usuario selectedUsuario) {
        this.selectedUsuario = selectedUsuario;
    }
    
    
}
