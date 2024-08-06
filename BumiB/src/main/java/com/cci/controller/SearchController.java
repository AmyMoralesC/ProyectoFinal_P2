package com.cci.controller;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import com.cci.model.Usuario;
import com.cci.data.ServicioUsuario;
import java.util.List;

@ManagedBean(name = "searchController")
@ViewScoped
public class SearchController implements Serializable {

    @ManagedProperty(value = "#{perfilController}")
    private PerfilController perfilController;

    private List<Usuario> searchResults;
    private ServicioUsuario servicioUsuario;
    private Usuario selectedUser;

    public SearchController() {
        servicioUsuario = new ServicioUsuario();
    }

    public void verPerfilUsuario(Usuario usuario) {
        if (usuario != null) {
            perfilController.cargarPerfil(usuario.getId());
        }
    }

    // Getters y Setters
    public PerfilController getPerfilController() {
        return perfilController;
    }

    public void setPerfilController(PerfilController perfilController) {
        this.perfilController = perfilController;
    }

    public List<Usuario> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<Usuario> searchResults) {
        this.searchResults = searchResults;
    }

    public ServicioUsuario getServicioUsuario() {
        return servicioUsuario;
    }

    public void setServicioUsuario(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }

    public Usuario getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(Usuario selectedUser) {
        this.selectedUser = selectedUser;
    }
}
