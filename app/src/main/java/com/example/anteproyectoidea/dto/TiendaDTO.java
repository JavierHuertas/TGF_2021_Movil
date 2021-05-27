package com.example.anteproyectoidea.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;

public class TiendaDTO  implements Serializable {

    private String key;
    private String tipo;
    private String nombreDueño;
    private String email;
    private String contrasenia;
    private String logoTienda;
    private String nombreComercio;
    private String localizacion;
    private double longitud;
    private double latitud;
    private String cadenaConexion;




    public TiendaDTO(String key, String tipo,String nombreDueño, String email, String logoTienda, String nombreComercio, String localizacion,double longitud, double latitud) {
        this.key = key;
        this.tipo = tipo;
        this.nombreDueño = nombreDueño;
        this.email = email;
        this.logoTienda = logoTienda;
        this.nombreComercio = nombreComercio;
        this.localizacion = localizacion;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public TiendaDTO(String nombreDueño, String logoTienda, String nombreComercio, String localizacion, double longitud, double latitud, String cadenaConexion) {
        this.nombreDueño = nombreDueño;
        this.logoTienda = logoTienda;
        this.nombreComercio = nombreComercio;
        this.localizacion = localizacion;
        this.longitud = longitud;
        this.latitud = latitud;
        this.cadenaConexion = cadenaConexion;
    }

    public TiendaDTO() {
    }

    public String getContrasenia() {
        return  contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNombreDueño() {
        return nombreDueño;
    }

    public void setNombreDueño(String nombreDueño) {
        this.nombreDueño = nombreDueño;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogoTienda() {
        return logoTienda;
    }

    public void setLogoTienda(String logoTienda) {
        this.logoTienda = logoTienda;
    }

    public String getNombreComercio() {
        return nombreComercio;
    }

    public void setNombreComercio(String nombreComercio) {
        this.nombreComercio = nombreComercio;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public String getCadenaConexion() {
        return cadenaConexion;
    }

    public void setCadenaConexion(String cadenaConexion) {
        this.cadenaConexion = cadenaConexion;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }



}
