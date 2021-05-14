package com.example.anteproyectoidea;

import android.os.Bundle;

import com.example.anteproyectoidea.adaptadores.AdapterProductosRV;
import com.example.anteproyectoidea.adaptadores.AdapterProductosTiendaRV;
import com.example.anteproyectoidea.dialogos.DialogHacerPedido;
import com.example.anteproyectoidea.dto.ProductoDTO;
import com.example.anteproyectoidea.ui.TiendaUsuarios;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainTienda extends AppCompatActivity implements AdapterProductosTiendaRV.OnButtonListenerClick{

    private RecyclerView listaProductos;
    private AdapterProductosTiendaRV adaptador;
    private List<ProductoDTO> lista;
    private SwipeRefreshLayout refrescarPorductos;
    private Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tienda);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        CollapsingToolbarLayout toolBarLayout = findViewById(R.id.toolbar_layout_tienda);
        toolBarLayout.setTitle("bolita");
        refrescarPorductos = findViewById(R.id.refreshProductostienda);
        retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.conexionAPI))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        listaProductos = findViewById(R.id.listaProductostienda);

        refrescarPorductos.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getProductos();
                ponerTodoOK();
                //refrescarPorductos.setEnabled(false);
            }
        });
        getProductos();
       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    public void getProductos(){

        BokyTakeAPI bokyTakeAPI = retrofit.create(BokyTakeAPI.class);

        Call<List<ProductoDTO>> llamada = bokyTakeAPI.getProductos("keytienda1");

        llamada.enqueue(new Callback<List<ProductoDTO>>() {
            @Override
            public void onResponse(Call<List<ProductoDTO>> call, Response<List<ProductoDTO>> response) {
                if(response.isSuccessful()){
                    lista = response.body();


                    adaptador = new AdapterProductosTiendaRV(lista ,  MainTienda.this::onButtonClick);
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

    public void ponerTodoOK(){
        refrescarPorductos.setEnabled(false);
        refrescarPorductos.setEnabled(true);
        refrescarPorductos.setRefreshing(false);
        //refrescarPorductos.setRefreshing(true);
    }
    @Override
    public void onButtonClick(int posicion) {
        //Toast.makeText(getApplicationContext(),"nombre producto "+lista.get(posicion).getNombre(),Toast.LENGTH_SHORT).show();
        //hacerPedido.precioProducto.setText(lista.get(posicion).getPrecio()+" €");
        //hacerPedido.nombreProducto.setText(lista.get(posicion).getNombre());
    }
}