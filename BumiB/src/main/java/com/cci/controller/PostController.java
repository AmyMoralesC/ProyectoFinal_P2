package com.cci.controller;

import com.cci.data.ServicioPost;
import com.cci.model.Post;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
        post.setFecha(new Date());
        post.setTexto(nuevoTexto);
        boolean exito = servicioPost.crearPost(post);
        if (exito) {
            posts.add(post);
            post = new Post();
            nuevoTexto = "";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Publicación creada exitosamente"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo crear la publicación"));
        }
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
