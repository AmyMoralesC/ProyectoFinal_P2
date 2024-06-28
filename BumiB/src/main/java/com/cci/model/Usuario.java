/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cci.model;

import java.io.Serializable;

/**
 *
 * @author maule
 */
public class Usuario implements Serializable{
    
    private int id;
    private String carnet;
    private String nombre;
    private String correo;
    private String clave;
    private String facultad;
    private String carrera;
    private String sede;
    private String biografia;
    private String telefono;

    public Usuario() {
    }

    public Usuario(int id, String carnet, String nombre, String correo, String clave, String facultad, String carrera, String sede, String biografia, String telefono) {
        this.id = id;
        this.carnet = carnet;
        this.nombre = nombre;
        this.correo = correo;
        this.clave = clave;
        this.facultad = facultad;
        this.carrera = carrera;
        this.sede = sede;
        this.biografia = biografia;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    
    
}
