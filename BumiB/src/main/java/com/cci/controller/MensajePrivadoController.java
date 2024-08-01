package com.cci.controller;

import com.cci.data.ServicioMensajePrivado;
import com.cci.data.ServicioUsuario;
import com.cci.model.MensajePrivado;
import com.cci.model.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.primefaces.PrimeFaces;

@ManagedBean(name = "mensajePrivadoController")
@SessionScoped
public class MensajePrivadoController implements Serializable {

    @ManagedProperty(value = "#{loginController}")
    private LoginController loginController;

    private ServicioMensajePrivado servicioMensajePrivado;
    private ServicioUsuario servicioUsuario;

    private List<Usuario> usuariosSeguidos;
    private List<MensajePrivado> historialMensajes;
    private Usuario usuarioSeleccionado;
    private MensajePrivado nuevoMensaje;

    private boolean contenidoMensajeVacio;

    public MensajePrivadoController() {
        servicioMensajePrivado = new ServicioMensajePrivado();
        servicioUsuario = new ServicioUsuario();
        nuevoMensaje = new MensajePrivado();
    }

    public void cargarUsuariosSeguidos() {
        try {
            usuariosSeguidos = servicioUsuario.obtenerUsuariosSeguidos(loginController.getUsuario().getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void seleccionarUsuario(Usuario usuario) {
        usuarioSeleccionado = usuario;
        contenidoMensajeVacio = true;
        cargarHistorialMensajes();
    }

    public void cargarHistorialMensajes() {
        try {
            historialMensajes = servicioMensajePrivado.obtenerHistorialMensajes(loginController.getUsuario().getId(), usuarioSeleccionado.getId());
            PrimeFaces.current().executeScript("scrollToBottom('chatContainer');");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enviarMensaje() {
        nuevoMensaje.setRemitenteId(loginController.getUsuario().getId());
        nuevoMensaje.setDestinatarioId(usuarioSeleccionado.getId());
        try {
            servicioMensajePrivado.enviarMensaje(nuevoMensaje);
            cargarHistorialMensajes();
            nuevoMensaje = new MensajePrivado(); // Limpiar el mensaje después de enviar
            contenidoMensajeVacio = true;
            PrimeFaces.current().executeScript("scrollToBottom('chatContainer');");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para obtener el nombre del remitente
    public String getNombreRemitente(int remitenteId) {
        Usuario remitente = servicioUsuario.buscarPorId(remitenteId);
        return remitente != null ? remitente.getNombre() : "Desconocido";
    }

    public void verificarContenidoMensaje() {
        contenidoMensajeVacio = nuevoMensaje.getContenido() == null || nuevoMensaje.getContenido().trim().isEmpty();
    }

    public int getIdUsuario() {
        return loginController.getUsuario() != null ? loginController.getUsuario().getId() : null;
    }

    public List<Usuario> getUsuariosSeguidos() {
        return usuariosSeguidos;
    }

    public void setUsuariosSeguidos(List<Usuario> usuariosSeguidos) {
        this.usuariosSeguidos = usuariosSeguidos;
    }

    public List<MensajePrivado> getHistorialMensajes() {
        return historialMensajes;
    }

    public void setHistorialMensajes(List<MensajePrivado> historialMensajes) {
        this.historialMensajes = historialMensajes;
    }

    public Usuario getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(Usuario usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }

    public MensajePrivado getNuevoMensaje() {
        return nuevoMensaje;
    }

    public void setNuevoMensaje(MensajePrivado nuevoMensaje) {
        this.nuevoMensaje = nuevoMensaje;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public boolean isContenidoMensajeVacio() {
        return contenidoMensajeVacio;
    }

    public void setContenidoMensajeVacio(boolean contenidoMensajeVacio) {
        this.contenidoMensajeVacio = contenidoMensajeVacio;
    }

}
