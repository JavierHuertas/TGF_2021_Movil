package com.example.anteproyectoidea;

import com.example.anteproyectoidea.dto.ProductoDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BokyTakeAPI {

    @GET("/productos/tienda/{idTienda}")
    Call<List<ProductoDTO>> getProductos(@Path("idTienda") String idTienda);

}
