package com.example.anteproyectoidea;

import com.example.anteproyectoidea.dto.PedidoDTO;
import com.example.anteproyectoidea.dto.ProductoDTO;
import com.example.anteproyectoidea.dto.ProductosCantidad;
import com.example.anteproyectoidea.dto.TiendaDTOAPI;
import com.example.anteproyectoidea.dto.UserDTOAPI;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BokyTakeAPI {

    @GET("/productos/tienda/{idTienda}")
    Call<List<ProductoDTO>> getProductos(@Path("idTienda") String idTienda);

    @POST("/usuarios/nuevo")
    Call<Map<String,String>> crearUsuario(@Body UserDTOAPI user);

    @POST("/tienda/new")
    Call<Map<String,String>> crearTienda(@Body TiendaDTOAPI tienda);

    @POST("/productos/tienda/{idTienda}/nuevo")
    Call<Map<String,Object>> nuevoProducto(@Body ProductoDTO nuevo,@Path("idTienda") String idTienda);

    @PUT("/productos/editar")
    Call<Map<String,Object>> editarProducto(@Body ProductoDTO productoDTO);

    @POST("/pedidos/nuevo/{idTienda}/{idUsuario}")
    Call<Map<String,Object>> nuevoPedido(@Path("idTienda") String idTienda, @Path("idUsuario") String idUsuario,@Body List<ProductosCantidad> pedido);

    @GET("/pedidos/usuario/{idUsuario}")
    Call<List<PedidoDTO>> getPedidousuario(@Path("idUsuario") String idUsuario);

    @GET("/pedidos/tienda/{idTienda}")
    Call<List<PedidoDTO>> getPedidosTienda(@Path("idTienda") String idTienda);


}
