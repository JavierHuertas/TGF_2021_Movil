package com.example.anteproyectoidea.dto;

import java.io.Serializable;

public class UserDTO implements Serializable {

    private String key;

    private String nombre;

    private String email;

    private String direccion;

    private String imagenUri;

    public UserDTO(String key, String nombre, String email, String direccion, String imagenUri) {
        this.key = key;
        this.nombre = nombre;
        this.email = email;
        this.direccion = direccion;
        this.imagenUri = imagenUri;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getImagenUri() {
        return imagenUri;
    }

    public void setImagenUri(String imagenUri) {
        this.imagenUri = imagenUri;
    }
}
