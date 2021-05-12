package com.example.anteproyectoidea.dto;

public class ProductosCantidad {

    private ProductoDTO productoDTO;
    private int cantidad;


    public ProductosCantidad(ProductoDTO productoDTO, int cantidad) {
        this.productoDTO = productoDTO;
        this.cantidad = cantidad;
    }

    public ProductoDTO getProductoDTO() {
        return productoDTO;
    }

    public void setProductoDTO(ProductoDTO productoDTO) {
        this.productoDTO = productoDTO;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
