package com.example.anteproyectoidea;

import com.example.anteproyectoidea.dto.ProductoDTO;
import com.example.anteproyectoidea.dto.UserDTO;
import com.example.anteproyectoidea.dto.UserDTOAPI;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BokyTakeAPI {

    @GET("/productos/tienda/{idTienda}")
    Call<List<ProductoDTO>> getProductos(@Path("idTienda") String idTienda);

    @POST("/usuarios/nuevo")
    Call<Map<String,String>> crearUsuario(@Body UserDTOAPI user);

    @POST("/usuarios/nuevo")
    Call<Map<String,String>> crearTienda(@Body TiendaDTOAPI tienda);

}
