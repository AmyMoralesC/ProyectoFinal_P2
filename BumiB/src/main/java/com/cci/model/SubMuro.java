package com.cci.model;

import java.io.Serializable;

public class SubMuro implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nombre;
    private String carrera;
    private String descripcionCorta;

    // Constructor
    public SubMuro(Long id, String nombre, String carrera, String descripcionCorta) {
        this.id = id;
        this.nombre = nombre;
        this.carrera = carrera;
        this.descripcionCorta = descripcionCorta;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getDescripcionCorta() {
        return descripcionCorta;
    }

    public void setDescripcionCorta(String descripcionCorta) {
        this.descripcionCorta = descripcionCorta;
    }
}