package com.cci.controller;

import com.cci.data.ServicioPost;
import com.cci.model.Post;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "postController")
@ViewScoped
public class PostController implements Serializable {
   
    private static final long serialVersionUID = 1L;

    private ServicioPost servicioPost;
    private Post post;
    private List<Post> posts;
    private String nuevoTexto;

    @PostConstruct
    public void init() {
        servicioPost = new ServicioPost();
        post = new Post();
        posts = servicioPost.buscarTodosLosPosts();
    }

    public void crearPost() {
        post.setFecha(new Timestamp(System.currentTimeMillis()));
        post.setTexto(nuevoTexto);
        
        boolean exito = servicioPost.crearPost(post);
        if (exito) {
            posts.add(post);
            post = new Post();
            nuevoTexto = "";
            post.setNotifi(1);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Publicación creada exitosamente"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo crear la publicación"));
        }
    }

    // Método para formatear la fecha en español
    public String formatDate(Timestamp timestamp) {
        Date date = new Date(timestamp.getTime());
        DateFormat df = new SimpleDateFormat("dd MMMM yyyy", new Locale("es", "ES"));
        return df.format(date);
    }

    // Getters y Setters
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
}