package com.example.anteproyectoidea.dto;

import java.io.Serializable;

public class UserDTO implements Serializable {

    private String key;

    private String nombre;

    private String email;

   private  Double latitud;

   private Double longitud;

    private String imagenUri;

    public UserDTO(String key, String nombre, String email,  String imagenUri,double latitud,double longitud) {
        this.key = key;
        this.nombre = nombre;
        this.email = email;
        this.latitud = latitud;
        this.longitud = longitud;
        this.imagenUri = imagenUri;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
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


    public String getImagenUri() {
        return imagenUri;
    }

    public void setImagenUri(String imagenUri) {
        this.imagenUri = imagenUri;
    }
}
