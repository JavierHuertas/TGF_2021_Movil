package com.example.anteproyectoidea;

import android.graphics.Color;
import android.os.Bundle;

import com.example.anteproyectoidea.adaptadores.AdapterProductos;
import com.example.anteproyectoidea.adaptadores.AdapterProductosRV;
import com.example.anteproyectoidea.dto.ProductoDTO;
import com.example.anteproyectoidea.dto.UserDTO;
import com.example.anteproyectoidea.dto.UserDTOAPI;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TiendaUsuarios extends AppCompatActivity implements AdapterProductosRV.OnButtonListenerClick {

    private AdapterProductosRV adaptador;
    private List<ProductoDTO> lista;
    private RecyclerView listaProductos;
    private SwipeRefreshLayout refrescarPorductos;
    private Retrofit retrofit;

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
        refrescarPorductos = findViewById(R.id.refreshProductos);
       retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.conexionAPI))
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        UserDTOAPI user = new UserDTOAPI(FirebaseAuth.getInstance().getCurrentUser().getUid(),"preuba",FirebaseAuth.getInstance().getCurrentUser().getEmail());
        
        cogerDatos(user);
        

        listaProductos = findViewById(R.id.listProductos);
        //listaProductos.setLayoutManager(manager);

        refrescarPorductos.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getProductos();
                ponerTodoOK();
                //refrescarPorductos.setEnabled(false);
            }
        });



        //refrescarPorductos.setEnabled(true);
        //refrescarPorductos.setRefreshing(false);
        //
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

    private void cogerDatos(UserDTOAPI user) {
        BokyTakeAPI bokyTakeAPI = retrofit.create(BokyTakeAPI.class);

        Call<Map<String,String>> llamada = bokyTakeAPI.crearUsuario(user);

        llamada.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if(response.isSuccessful()){

                }else{
                    Toast.makeText(getApplicationContext(),"fallito",Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(getApplicationContext(),"Exito "+response.body().get("message").toString(),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
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


    public void getProductos(){

        BokyTakeAPI bokyTakeAPI = retrofit.create(BokyTakeAPI.class);

        Call<List<ProductoDTO>> llamada = bokyTakeAPI.getProductos("keytienda1");

        llamada.enqueue(new Callback<List<ProductoDTO>>() {
            @Override
            public void onResponse(Call<List<ProductoDTO>> call, Response<List<ProductoDTO>> response) {
                if(response.isSuccessful()){
                    lista = response.body();


                    adaptador = new AdapterProductosRV(lista,TiendaUsuarios.this::onButtonClick);
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


    @Override
    public void onButtonClick(int posicion) {
        DialogHacerPedido hacerPedido = new DialogHacerPedido();
        hacerPedido.show(getSupportFragmentManager(),"example dialog");
    }
}