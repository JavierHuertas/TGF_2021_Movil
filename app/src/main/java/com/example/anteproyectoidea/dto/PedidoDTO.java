package com.example.anteproyectoidea.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PedidoDTO {

    private Integer id;
    private String estado;
    private String TiendaDTO;
    private UserDTOAPI UsuarioPedidoDTO;
    private Date freservas;
    private Date frecogida;
    private Float importe;
    private List<ProductosCantidad> productoses;

    public List<ProductosCantidad> getProductosCantidads() {
        return productoses;
    }

    public void setProductosCantidads(List<ProductosCantidad> productosCantidads) {
        this.productoses = productosCantidads;
    }


    public PedidoDTO(Integer id, String estados, String tiendaDTO, UserDTOAPI usuarioPedidoDTO, Date freservas, Date frecogida, Float importe, List<ProductosCantidad> productosCantidads) {
        this.id = id;
        estado = estados;
        TiendaDTO = tiendaDTO;
        UsuarioPedidoDTO = usuarioPedidoDTO;
        this.freservas = freservas;
        this.frecogida = frecogida;
        this.importe = importe;
        this.productoses = productosCantidads;
    }

    public PedidoDTO(Integer id, String estados, String tiendaDTO, UserDTOAPI usuarioPedidoDTO, Date freservas, Date frecogida, Float importe) {
        this.id = id;
        estado = estados;
        TiendaDTO = tiendaDTO;
        UsuarioPedidoDTO = usuarioPedidoDTO;
        this.freservas = freservas;
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
        return TiendaDTO;
    }

    public void setTiendaDTO(String tiendaDTO) {
        TiendaDTO = tiendaDTO;
    }

    public UserDTOAPI getUsuarioPedidoDTO() {
        return UsuarioPedidoDTO;
    }

    public void setUsuarioPedidoDTO(UserDTOAPI usuarioPedidoDTO) {
        UsuarioPedidoDTO = usuarioPedidoDTO;
    }

    public Date getFreservas() {
        return freservas;
    }

    public void setFreservas(Date freservas) {
        this.freservas = freservas;
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
}
