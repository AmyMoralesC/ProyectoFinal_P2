/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cci.model;

import java.io.Serializable;
import java.util.Date;

public class Post implements Serializable {
    private int id;
    private String titulo;
    private String creador;
    private String texto;
    private Date fecha;
    private int notifi;
    

    public Post(int id, String titulo, String creador, String texto, Date fecha, int notifi) {
        this.id = id;
        this.titulo = titulo;
        this.creador = creador;
        this.texto = texto;
        this.fecha = fecha;
        this.notifi = notifi;
    }

   
    public Post() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCreador() {
        return creador;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getNotifi() {
        return notifi;
    }

    public void setNotifi(int notifi) {
        this.notifi = notifi;
    }
    
   
    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", texto='" + texto + '\'' +
                ", fecha=" + fecha +
                '}';
    }
}
