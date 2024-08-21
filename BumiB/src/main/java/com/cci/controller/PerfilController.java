package com.cci.controller;

import com.cci.data.ServicioPost;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import com.cci.model.Usuario;
import com.cci.model.Post;
import com.cci.data.ServicioUsuario;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
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
    private int cantidadSeguidoresPerfil;

    private String nombreUsuarioBuscado;
    private boolean usuarioSiguiendo;

    private ServicioPost servicioPost;
    private List<Post> postsDelUsuario;

    public PerfilController() {
        servicioUsuario = new ServicioUsuario();
        servicioPost = new ServicioPost();
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

    public List<Usuario> getSeguidoresPerfilVisualizado() {
        if (perfilVisualizado != null) {
            return servicioUsuario.obtenerSeguidores(perfilVisualizado.getId());
        }
        return new ArrayList<>();
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
        if (perfilVisualizado != null) {
            cantidadSeguidoresPerfil = servicioUsuario.contarSeguidores(perfilVisualizado.getId());
        }
    }

    public String verPerfil() {
        perfilVisualizado = servicioUsuario.buscarPorNombre(nombreUsuarioBuscado);
        if (perfilVisualizado != null) {
            cantidadSeguidoresPerfil = servicioUsuario.contarSeguidores(perfilVisualizado.getId());
            nombreUsuarioBuscado = null;
            System.out.println("Ruta de la imagen de perfil: " + this.perfilVisualizado.getFotoPerfil());
            return "verPerfilUsuario.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario no encontrado", ""));
            return null;
        }
    }

    public void followUser() {
        ServicioUsuario servicioUsuario = new ServicioUsuario();
        int seguidoId = this.perfilVisualizado.getId(); // ID del usuario que está siendo seguido
        int seguidorId = this.usuario.getId(); // ID del usuario actual logueado

        if (!servicioUsuario.seguirUsuario(seguidorId, seguidoId)) {
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se logró seguir al usuario", "No se logró seguir al usuario"));
        } else {
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario seguido", "Ahora sigues a este usuario"));
        }
    }

    public void unfollowUser() {
        ServicioUsuario servicioUsuario = new ServicioUsuario();
        int seguidoId = this.perfilVisualizado.getId(); // ID del usuario que está siendo dejado de seguir
        int seguidorId = this.usuario.getId(); // ID del usuario actual logueado

        if (!servicioUsuario.dejarDeSeguirUsuario(seguidorId, seguidoId)) {
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se logró dejar de seguir al usuario", "No se logró dejar de seguir al usuario"));
        } else {
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Dejado de seguir", "Ya no sigues a este usuario"));
            // Actualizar el estado del botón
            this.usuarioSiguiendo = false;
        }
    }

    public boolean isUsuarioSiguiendo() {
        if (perfilVisualizado != null && usuario != null) {
            int seguidorId = usuario.getId(); // ID del usuario actual
            int seguidoId = perfilVisualizado.getId(); // ID del usuario visualizado
            this.usuarioSiguiendo = servicioUsuario.yaSigueUsuario(seguidorId, seguidoId);
        } else {
            this.usuarioSiguiendo = false;
        }
        return usuarioSiguiendo;
    }

    public void toggleFollow(ActionEvent event) {
        if (isUsuarioSiguiendo()) {
            unfollowUser();
        } else {
            followUser();
        }
    }

    public List<Post> getPostsDelUsuario() {
        if (usuario != null) {
            return servicioPost.buscarPostsPorUsuario(usuario.getId());
        }
        return new ArrayList<>();
    }
    
    public void crearPost(String titulo, String texto) {
        if (usuario != null && titulo != null && !titulo.isEmpty() && texto != null && !texto.isEmpty()) {
            Post newPost = new Post();
            newPost.setTitulo(titulo);
            newPost.setTexto(texto);
            newPost.setCreador(usuario.getNombre());
            newPost.setFecha(new Date());
            newPost.setCreadorId(usuario.getId());
            newPost.setNotifi(0);  // Asume 0 como valor predeterminado para notificación

            if (servicioPost.crearPost(newPost)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Publicación creada correctamente"));
                // Actualiza la lista de publicaciones del usuario
                postsDelUsuario = servicioPost.buscarPostsPorUsuario(usuario.getId());
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al crear la publicación", ""));
            }
        }
    }

    public void eliminarPost(int postId) {
        if (servicioPost.eliminarPost(postId)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Publicación eliminada correctamente"));
            // Actualiza la lista de publicaciones del usuario
            postsDelUsuario = servicioPost.buscarPostsPorUsuario(usuario.getId());
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al eliminar la publicación", ""));
        }
    }

    public List<Post> getPostsDelPerfilVisualizado() {
        if (perfilVisualizado != null) {
            return servicioPost.buscarPostsPorUsuario(perfilVisualizado.getId());
        }
        return new ArrayList<>();
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

    public int getCantidadSeguidoresPerfil() {
        return cantidadSeguidoresPerfil;
    }

    public void setCantidadSeguidoresPerfil(int cantidadSeguidoresPerfil) {
        this.cantidadSeguidoresPerfil = cantidadSeguidoresPerfil;
    }

    public void setUsuarioSiguiendo(boolean usuarioSiguiendo) {
        this.usuarioSiguiendo = usuarioSiguiendo;
    }

}
