package com.example.anteproyectoidea.dto;

import java.io.Serializable;

public class ProductoDTO implements Serializable {

    private int id;
    private String nombre;
    private double precio;
    private int cantidad;
    private Boolean mostrarApp;
    private String descripcion;
    private String urlImg;



    public ProductoDTO() {
    }

    public ProductoDTO(int id, String nombre, double precio, String urlImg, int cantidad, String descripcion,Boolean mostrarApp) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.urlImg = urlImg;
        this.cantidad = cantidad;
        this.mostrarApp = mostrarApp;
        this.descripcion=descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getUrlImagen() {
        return urlImg;
    }

    public void setUrlImagen(String urlImg) {
        this.urlImg = urlImg;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Boolean getMostrarApp() {
        return mostrarApp;
    }

    public void setMostrarApp(Boolean mostrarApp) {
        this.mostrarApp = mostrarApp;
    }
}
