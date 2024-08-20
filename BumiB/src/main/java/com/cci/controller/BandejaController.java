/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cci.controller;

import com.cci.data.ServicioNotificacion;
import com.cci.model.Notificacion;
import com.cci.model.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author jared
 */
@ManagedBean(name = "bandejaController")
@SessionScoped
public class BandejaController implements Serializable {

    @ManagedProperty(value = "#{postController}")
    private PostController postController;
    @ManagedProperty(value = "#{servicioNotificacion}")
    private ServicioNotificacion servicioNot;
    @ManagedProperty(value = "#{loginController}")
    private LoginController loginController;
    private Notificacion notificacion;
    private Notificacion selectedNotificacion;
    private List<Notificacion> notificaciones;

    public void avisoPost() {

        if (postController.getPost().getNotifi() == 1) {
            System.out.println("Se notifico post");

        } else {
            System.out.println("No se notifico post");
        }

    }

    // Método para crear una notificación de mensaje
    public void crearNotificacionMensaje() {
        if (postController.getPost().getNotifi() == 1) {
            notificacion = new Notificacion();
            String creador = loginController.getUsuario().getNombre();
            System.out.println("Valor de creador en Notificación: " + creador);
            notificacion.setTipo("Mensaje");
            notificacion.setDonde("Muro general");
            notificacion.setDe(creador);
            servicioNot.crearNotif(notificacion);
        }
    }

    // Método para cargar las notificaciones
    public void cargarNotificaciones() {
        if (servicioNot != null) {
            notificaciones = servicioNot.buscarTodasLasNotificaciones();
        }
    }

    // Getter para notificaciones, cargando si es necesario
    public List<Notificacion> getNotificaciones() {
        if (notificaciones == null) {
            cargarNotificaciones();
        }
        return notificaciones;
    }

    public void deleteNotif() {
       if (selectedNotificacion != null) {
            boolean exito = servicioNot.eliminarNot(selectedNotificacion.getId());
            
            if (exito) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Notificación eliminada exitosamente"));
                cargarNotificaciones(); // Recargar la lista después de eliminar
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar la notificación"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "No hay notificación seleccionada para eliminar"));
        }
    }

    public Notificacion getNotificacion() {
        return notificacion;
    }

    public void setNotificacion(Notificacion notificacion) {
        this.notificacion = notificacion;
    }

    public Notificacion getSelectedNotificacion() {
        return selectedNotificacion;
    }

    public void setSelectedNotificacion(Notificacion selectedNotificacion) {
        this.selectedNotificacion = selectedNotificacion;
    }

    public ServicioNotificacion getServicioNot() {
        return servicioNot;
    }

    public void setServicioNot(ServicioNotificacion servicioNot) {
        this.servicioNot = servicioNot;
    }

    public PostController getPostController() {
        return postController;
    }

    public void setPostController(PostController postController) {
        this.postController = postController;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

}
