package com.example.anteproyectoidea.dto;

import java.io.Serializable;

public class UserDTOAPI implements Serializable {

    private String id;
    private String nombre;
    private String apellidos;
    private String email;


    public UserDTOAPI(String id, String nombre, String apellido, String email) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellido;
        this.email = email;
    }

    public UserDTOAPI() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
}
