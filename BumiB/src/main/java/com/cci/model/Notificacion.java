/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cci.model;

import java.io.Serializable;

/**
 *
 * @author jared
 */
public class Notificacion implements Serializable{
    private int id;
    private String tipo;
    private String donde;
    private String de;

    public Notificacion(int id, String tipo, String donde, String de) {
        this.id = id;
        this.tipo = tipo;
        this.donde = donde;
        this.de = de;
    }

    public Notificacion() {
    }

    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDonde() {
        return donde;
    }

    public void setDonde(String donde) {
        this.donde = donde;
    }

    public String getDe() {
        return de;
    }

    public void setDe(String de) {
        this.de = de;
    }
    
    
}
