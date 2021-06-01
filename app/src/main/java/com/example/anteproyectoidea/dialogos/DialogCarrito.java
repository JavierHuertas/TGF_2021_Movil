package com.example.anteproyectoidea.dialogos;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.anteproyectoidea.BokyTakeAPI;
import com.example.anteproyectoidea.MainActivity;
import com.example.anteproyectoidea.R;
import com.example.anteproyectoidea.adaptadores.AdapterProductosCarrito;
import com.example.anteproyectoidea.dto.ProductosCantidad;
import com.example.anteproyectoidea.ui.TiendaUsuarios;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DialogCarrito extends AppCompatDialogFragment {

    public ArrayList<ProductosCantidad> productosPedido;
    public ListView listaPedidos;
    public TextView preciototal;
    private Retrofit retrofit;
    private Context context;
    private Activity actividad;
    public String idUsuario,idTienda;
    public AdapterProductosCarrito productosCarrito;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view =inflater.inflate(R.layout.dialog_cart,null);
        listaPedidos= view.findViewById(R.id.itemsCarts);
        preciototal = view.findViewById(R.id.totalPedido);
        retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.conexionAPI))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        productosCarrito = new AdapterProductosCarrito(TiendaUsuarios.pedidoActual,builder.getContext());
        listaPedidos.setAdapter(productosCarrito);


        builder.setTitle("SuCompra").setView(view)
                .setPositiveButton("Realizar pedido", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BokyTakeAPI bokyTakeAPI = retrofit.create(BokyTakeAPI.class);

                        Call<Map<String,Object>> creaccionPedido = bokyTakeAPI.nuevoPedido(idTienda,idUsuario,TiendaUsuarios.pedidoActual);

                        creaccionPedido.enqueue(new Callback<Map<String, Object>>() {
                            @Override
                            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {


                            }

                            @Override
                            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                                TiendaUsuarios.pedidoActual.clear();
                                actividad.onBackPressed();
                                /*Intent nuevo = new Intent(context,MainActivity.class);
                                startActivity(nuevo);*/

                            }
                        });


                    }
                })
                .setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();


        return builder.create();



    }


    public void setActividad(Activity actividad) {
        this.actividad = actividad;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdTienda() {
        return idTienda;
    }

    public void setIdTienda(String idTienda) {
        this.idTienda = idTienda;
    }

    public ArrayList<ProductosCantidad> getProductosPedido() {
        return productosPedido;
    }

    public void setProductosPedido(ArrayList<ProductosCantidad> productosPedido) {
        this.productosPedido = productosPedido;
    }

    public AdapterProductosCarrito getProductosCarrito() {
        return productosCarrito;
    }

    public void setProductosCarrito(AdapterProductosCarrito productosCarrito) {
        this.productosCarrito = productosCarrito;
    }
}
