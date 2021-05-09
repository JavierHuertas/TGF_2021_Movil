package com.example.anteproyectoidea;

import android.graphics.Color;
import android.os.Bundle;

import com.example.anteproyectoidea.adaptadores.AdapterProductos;
import com.example.anteproyectoidea.adaptadores.AdapterProductosRV;
import com.example.anteproyectoidea.dto.ProductoDTO;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TiendaUsuarios extends AppCompatActivity {

    private AdapterProductosRV adaptador;
    private List<ProductoDTO> lista;
    private RecyclerView listaProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda_usuarios);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        NestedScrollView scrollview = (NestedScrollView) findViewById(R.id.scrollViewUp);
        scrollview.setNestedScrollingEnabled(true);
        toolbar.setBackgroundColor(getResources().getColor(R.color.Carne));
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle("tienda");
        toolBarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.logo));
        toolBarLayout.setExpandedTitleColor(getResources().getColor(R.color.logo));
        toolBarLayout.setBackgroundColor(getResources().getColor(R.color.Carne));
        LinearLayoutManager manager = new LinearLayoutManager(this);

        listaProductos = findViewById(R.id.listProductos);
        //listaProductos.setLayoutManager(manager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        getProductos();

    }

    public void getProductos(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.43:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BokyTakeAPI bokyTakeAPI = retrofit.create(BokyTakeAPI.class);

        Call<List<ProductoDTO>> llamada = bokyTakeAPI.getProductos("keytienda1");

        llamada.enqueue(new Callback<List<ProductoDTO>>() {
            @Override
            public void onResponse(Call<List<ProductoDTO>> call, Response<List<ProductoDTO>> response) {
                if(response.isSuccessful()){
                    lista = response.body();

                   // for (int x =0;x<lista.size();x++){
                     //   Toast.makeText(getApplicationContext(),"Nombre producto "+lista.get(x).getUrlImagen(),Toast.LENGTH_SHORT).show();

                    //}
                    adaptador = new AdapterProductosRV(lista);
                    Toast.makeText(getApplicationContext(),"HOLA???"+adaptador.getItemCount(),Toast.LENGTH_SHORT).show();

                    listaProductos.setAdapter(adaptador);
                }
            }

            @Override
            public void onFailure(Call<List<ProductoDTO>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"fallito",Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}