package com.example.anteproyectoidea.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PedidoDTO {

    private Integer id;
    private String estado;
    private String tienda;
    private String email;
    private UserDTOAPI UsuarioPedidoDTO;
    private Date freserva;
    private Date frecogida;
    private Float importe;
    private List<ProductosCantidad> productoses;

    public List<ProductosCantidad> getProductosCantidads() {
        return productoses;
    }

    public void setProductosCantidads(List<ProductosCantidad> productosCantidads) {
        this.productoses = productosCantidads;
    }


    public PedidoDTO(Integer id, String estados, String tiendaDTO, UserDTOAPI usuarioPedidoDTO, Date freserva, Date frecogida, Float importe, List<ProductosCantidad> productosCantidads) {
        this.id = id;
        estado = estados;
        tienda = tiendaDTO;
        UsuarioPedidoDTO = usuarioPedidoDTO;
        this.freserva = freserva;
        this.frecogida = frecogida;
        this.importe = importe;
        this.productoses = productosCantidads;
    }

    public PedidoDTO(Integer id, String estados, String tiendaDTO, UserDTOAPI usuarioPedidoDTO, Date freserva, Date frecogida, Float importe) {
        this.id = id;
        estado = estados;
        tienda = tiendaDTO;
        UsuarioPedidoDTO = usuarioPedidoDTO;
        this.freserva = freserva;
        this.frecogida = frecogida;
        this.importe = importe;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEstados() {
        return estado;
    }

    public void setEstados(String estados) {
        estado = estados;
    }

    public String getTiendaDTO() {
        return tienda;
    }

    public void setTiendaDTO(String tiendaDTO) {
        tienda = tiendaDTO;
    }

    public UserDTOAPI getUsuarioPedidoDTO() {
        return UsuarioPedidoDTO;
    }

    public void setUsuarioPedidoDTO(UserDTOAPI usuarioPedidoDTO) {
        UsuarioPedidoDTO = usuarioPedidoDTO;
    }

    public Date getFreserva() {
        return freserva;
    }

    public void setFreserva(Date freserva) {
        this.freserva = freserva;
    }

    public Date getFrecogida() {
        return frecogida;
    }

    public void setFrecogida(Date frecogida) {
        this.frecogida = frecogida;
    }

    public Float getImporte() {
        return importe;
    }

    public void setImporte(Float importe) {
        this.importe = importe;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
