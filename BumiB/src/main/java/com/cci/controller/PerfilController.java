package com.cci.controller;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import com.cci.model.Usuario;
import com.cci.data.ServicioUsuario;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@ManagedBean(name = "perfilController")
@SessionScoped
public class PerfilController implements Serializable {

    @ManagedProperty(value = "#{loginController}")
    private LoginController loginController;

    private Usuario usuario;
    private ServicioUsuario servicioUsuario;

    private Usuario selectedUsuario;

    public PerfilController() {
        servicioUsuario = new ServicioUsuario();
    }

    public void init() {
        if (loginController != null && loginController.getUsuario() != null) {
            this.usuario = loginController.getUsuario();
        } else {
            this.usuario = new Usuario();
        }
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
        init();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNombreUsuario() {
        return loginController.getUsuario() != null ? loginController.getUsuario().getNombre() : "Usuario no conectado";
    }
    
    public String getSedeUsuario() {
        return loginController.getUsuario() != null ? loginController.getUsuario().getSede() : "Sede no encontrada";
    }
    
    public String getCarreraUsuario() {
        return loginController.getUsuario() != null ? loginController.getUsuario().getCarrera(): "Sede no encontrada";
    }
    
    public String getBiografiaUsuario() {
        return loginController.getUsuario() != null ? loginController.getUsuario().getBiografia(): "Sede no encontrada";
    }

    public void guardarPerfil() {
        if (usuario != null) {
            servicioUsuario.actualizar(usuario);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Perfil actualizado correctamente", ""));
            // Actualiza la información del usuario en la sesión
            loginController.setUsuario(usuario);
        }
    }

    // Método para obtener los seguidores del usuario
    public List<Usuario> getSeguidores() {
        if (usuario != null) {
            return servicioUsuario.obtenerSeguidores(usuario.getId()); // Asegúrate de tener este método en tu ServicioUsuario
        }
        return new ArrayList<>();
    }

    public void eliminarUsuario() throws IOException {
        if (selectedUsuario != null) {
            boolean resultado = servicioUsuario.eliminarSeguidor(usuario.getId(), selectedUsuario.getId());
            if (resultado) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Seguidor eliminado correctamente", ""));
                // Actualiza la lista de seguidores después de la eliminación
                FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
                FacesContext.getCurrentInstance().getExternalContext().redirect("perfilUsuario.xhtml");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo eliminar el seguidor", ""));
            }
        }
    }

    public ServicioUsuario getServicioUsuario() {
        return servicioUsuario;
    }

    public void setServicioUsuario(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }

    public Usuario getSelectedUsuario() {
        return selectedUsuario;
    }

    public void setSelectedUsuario(Usuario selectedUsuario) {
        this.selectedUsuario = selectedUsuario;
    }

}
