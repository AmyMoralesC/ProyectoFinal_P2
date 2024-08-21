package com.cci.controller;

import com.cci.data.ServicioPost;
import com.cci.model.Post;
import com.cci.model.Usuario;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@ManagedBean(name = "postController")
@SessionScoped
public class PostController implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{loginController}")
    private LoginController logingController;

    private ServicioPost servicioPost;
    private Post post;
    private List<Post> posts;
    private String nuevoTexto;
    private Usuario usuario;

    @PostConstruct
    public void init() {
        servicioPost = new ServicioPost();
        post = new Post();
        posts = servicioPost.buscarTodosLosPosts();
    }

    public void crearPost() {
        if (nuevoTexto == null || nuevoTexto.trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El campo de publicación no puede estar vacío"));
            return;
        }

        post.setFecha(new Timestamp(System.currentTimeMillis()));
        post.setTexto(nuevoTexto);
        post.setCreador(logingController.getUsuario().getNombre());
        post.setCreadorId(logingController.getUsuario().getId());
        post.setNotifi(1);

        boolean exito = servicioPost.crearPost(post);
        if (exito) {
            posts = servicioPost.buscarTodosLosPosts();
            post = new Post();
            nuevoTexto = "";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Publicación creada exitosamente"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo crear la publicación"));
        }
    }

    public void darLike(int postId) {
        // * Registra un "like" para una publicación específica si el usuario actual aún no ha dado "like" a la publicación.
        Usuario usuarioActual = logingController.getUsuario();
        if (usuarioActual == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Debes estar logueado para dar like."));
            return;
        }

        if (!servicioPost.usuarioYaDioLike(postId, usuarioActual.getId())) {
            boolean exito = servicioPost.agregarLike(postId, usuarioActual.getId());
            if (exito) {
                Post postActualizado = posts.stream().filter(p -> p.getId() == postId).findFirst().orElse(null);
                if (postActualizado != null) {
                    postActualizado.setLikesCount(postActualizado.getLikesCount() + 1);
                    servicioPost.actualizarLikes(postId, postActualizado.getLikesCount());
                }
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Like registrado exitosamente"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo registrar el like"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Ya has dado like a esta publicación."));
        }
    }

    public String formatDate(Timestamp timestamp) {
        Date date = new Date(timestamp.getTime());
        DateFormat df = new SimpleDateFormat("dd MMMM yyyy", new Locale("es", "ES"));
        return df.format(date);
    }

    // Getters y Setters
    public LoginController getLogingController() {
        return logingController;
    }

    public void setLogingController(LoginController logingController) {
        this.logingController = logingController;
    }

    public ServicioPost getServicioPost() {
        return servicioPost;
    }

    public void setServicioPost(ServicioPost servicioPost) {
        this.servicioPost = servicioPost;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public String getNuevoTexto() {
        return nuevoTexto;
    }

    public void setNuevoTexto(String nuevoTexto) {
        this.nuevoTexto = nuevoTexto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}