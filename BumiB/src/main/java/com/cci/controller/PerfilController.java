package com.cci.controller;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import com.cci.model.Usuario;
import com.cci.data.ServicioUsuario;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

@ManagedBean(name = "perfilController")
@SessionScoped
public class PerfilController implements Serializable {

    @ManagedProperty(value = "#{loginController}")
    private LoginController loginController;

    private Usuario usuario;
    private ServicioUsuario servicioUsuario;
    private Usuario selectedUsuario;
    private int cantidadSeguidores;
    private Usuario perfilVisualizado;
    private UploadedFile file;

    private String nombreUsuarioBuscado;

    public PerfilController() {
        servicioUsuario = new ServicioUsuario();
    }

    public UploadedFile getFile() {
        return file;
    }

    public void init() {
        if (loginController != null && loginController.getUsuario() != null) {
            this.usuario = loginController.getUsuario();
            this.cantidadSeguidores = servicioUsuario.contarSeguidores(usuario.getId());
        } else {
            this.usuario = new Usuario();
            this.cantidadSeguidores = 0;
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

    public String getFacultadUsuario() {
        return loginController.getUsuario() != null ? loginController.getUsuario().getFacultad() : "Sede no encontrada";
    }

    public String getCarreraUsuario() {
        return loginController.getUsuario() != null ? loginController.getUsuario().getCarrera() : "Sede no encontrada";
    }

    public String getBiografiaUsuario() {
        return loginController.getUsuario() != null ? loginController.getUsuario().getBiografia() : "Sede no encontrada";
    }

    public void guardarPerfil() {
        if (usuario != null) {
            servicioUsuario.actualizar(usuario);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Perfil actualizado correctamente", ""));
            loginController.setUsuario(usuario);
        }
    }

    public List<Usuario> getSeguidores() {
        if (usuario != null) {
            return servicioUsuario.obtenerSeguidores(usuario.getId());
        }
        return new ArrayList<>();
    }

    public void eliminarUsuario() {
        if (selectedUsuario != null) {
            boolean resultado = servicioUsuario.eliminarSeguidor(usuario.getId(), selectedUsuario.getId());
            if (resultado) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Seguidor eliminado correctamente", ""));
                FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo eliminar el seguidor", ""));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se seleccionó ningún seguidor", ""));
        }
    }

    public void upload() {
        if (file != null) {
            try (InputStream input = file.getInputStream()) {
                String fileName = usuario.getId() + "_" + file.getFileName();
                String uploadsDir = getUploadsDirectory();

                // Verificar y crear directorio si no existe
                Files.createDirectories(Paths.get(uploadsDir));
                String destination = Paths.get(uploadsDir, fileName).toString();
                Files.copy(input, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
                usuario.setFotoPerfil(fileName);
                guardarPerfil();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Foto subida correctamente"));
            } catch (IOException e) {
                e.printStackTrace();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al subir la foto", e.getMessage()));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Seleccione un archivo para subir", ""));
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        this.file = event.getFile();
        if (file != null) {
            try (InputStream input = file.getInputStream()) {
                String fileName = usuario.getId() + "_" + file.getFileName();
                String uploadsDir = getUploadsDirectory();

                // Verificar y crear directorio si no existe
                Files.createDirectories(Paths.get(uploadsDir));
                String destination = Paths.get(uploadsDir, fileName).toString();
                Files.copy(input, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
                usuario.setFotoPerfil(fileName);
                guardarPerfil();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Foto subida correctamente"));
            } catch (IOException e) {
                e.printStackTrace();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al subir la foto", e.getMessage()));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Seleccione un archivo para subir", ""));
        }
    }

    private String getUploadsDirectory() {
        String projectPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        // Navegar desde el directorio de destino hasta el directorio de cargas en src/main/webapp
        String uploadsPath = Paths.get(projectPath, "..", "..", "src", "main", "webapp", "resources", "uploads").normalize().toAbsolutePath().toString();
        return uploadsPath;
    }

    public String getRutaFotoPerfil() {
        if (usuario != null && usuario.getFotoPerfil() != null) {
            return "/resources/uploads/" + usuario.getFotoPerfil();
        } else {
            return "/resources/images/profile.jpg";
        }
    }

    public String getFotoPerfil() {
        if (perfilVisualizado != null && perfilVisualizado.getFotoPerfil() != null) {
            return "/resources/uploads/" + perfilVisualizado.getFotoPerfil();
        } else {
            return "/resources/images/profile.jpg";
        }
    }

    public String getFotoPerfilSeguidor(Usuario seguidor) {
        if (seguidor != null && seguidor.getFotoPerfil() != null) {
            return "/resources/uploads/" + seguidor.getFotoPerfil();
        } else {
            return "/resources/images/profile.jpg";
        }
    }

    public void cargarPerfil(int usuarioId) {
        perfilVisualizado = servicioUsuario.buscarPorId(usuarioId);
    }

    public String verPerfil() {
        perfilVisualizado = servicioUsuario.buscarPorNombre(nombreUsuarioBuscado);
        if (perfilVisualizado != null) {
            nombreUsuarioBuscado = null;
            System.out.println("Ruta de la imagen de perfil: " + this.perfilVisualizado.getFotoPerfil());
            return "verPerfilUsuario.xhtml?faces-redirect=true";

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario no encontrado", ""));
            return null;
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

    public int getCantidadSeguidores() {
        return cantidadSeguidores;
    }

    public void setCantidadSeguidores(int cantidadSeguidores) {
        this.cantidadSeguidores = cantidadSeguidores;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public Usuario getPerfilVisualizado() {
        return perfilVisualizado;
    }

    public void setPerfilVisualizado(Usuario perfilVisualizado) {
        this.perfilVisualizado = perfilVisualizado;
    }

    public String getNombreUsuarioBuscado() {
        return nombreUsuarioBuscado;
    }

    public void setNombreUsuarioBuscado(String nombreUsuarioBuscado) {
        this.nombreUsuarioBuscado = nombreUsuarioBuscado;
    }

}
