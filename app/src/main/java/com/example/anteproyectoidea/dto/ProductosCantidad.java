package com.example.anteproyectoidea.dto;

public class ProductosCantidad {

    private ProductoDTO producto;
    private int cantidad;


    public ProductosCantidad(ProductoDTO productoDTO, int cantidad) {
        this.producto = productoDTO;
        this.cantidad = cantidad;
    }

    public ProductoDTO getProductoDTO() {
        return producto;
    }

    public void setProductoDTO(ProductoDTO productoDTO) {
        this.producto = productoDTO;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
