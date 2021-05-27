package com.example.anteproyectoidea.dto;

import java.math.BigInteger;
import java.security.MessageDigest;

public class TiendaDTOAPI {

    private String id;
    private String nombre;
    private String localizacion;
    private String contrasenia;
    private String email;

    public TiendaDTOAPI(String id, String nombre, String localizacion, String contrasenia, String email) {
        this.id = id;
        this.nombre = nombre;
        this.localizacion = localizacion;
        this.contrasenia = contrasenia;
        this.email = email;
    }

    public TiendaDTOAPI() {
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

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
