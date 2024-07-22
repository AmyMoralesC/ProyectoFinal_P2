package com.cci.controller;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import com.cci.model.Usuario;
import com.cci.data.ServicioUsuario;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@ManagedBean(name = "perfilController")
@SessionScoped
public class PerfilController implements Serializable {
    
    @ManagedProperty(value = "#{loginController}")
    private LoginController loginController;
    
    private Usuario usuario;
    private ServicioUsuario servicioUsuario;

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
        return usuario != null ? usuario.getNombre() : "Usuario no conectado";
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
}
