package com.example.anteproyectoidea.ui;

import android.os.Bundle;

import com.example.anteproyectoidea.BokyTakeAPI;
import com.example.anteproyectoidea.R;
import com.example.anteproyectoidea.dialogos.DialogCarrito;
import com.example.anteproyectoidea.dialogos.DialogHacerPedido;
import com.example.anteproyectoidea.adaptadores.AdapterProductosRV;
import com.example.anteproyectoidea.dto.ProductoDTO;
import com.example.anteproyectoidea.dto.ProductosCantidad;
import com.example.anteproyectoidea.dto.UserDTOAPI;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TiendaUsuarios extends AppCompatActivity implements AdapterProductosRV.OnButtonListenerClick, DialogHacerPedido.DialogoPedidoListener {

    private AdapterProductosRV adaptador;
    private List<ProductoDTO> lista;
    private List<ProductoDTO> listaCorrecta = new ArrayList<>();
    private List<ProductoDTO> listafiltrada = new ArrayList<>();
    private RecyclerView listaProductos;
    private LinearLayout noProductos;
    private TextInputLayout filtro;
    private Button filtrar;
    private Button refresh;
    private SwipeRefreshLayout refrescarPorductos;
    private Retrofit retrofit;
    private String tiendaKey;
    private String tiendaNombre;
    FloatingActionButton fab;
    private ArrayList<ProductosCantidad> pedidoActual = new ArrayList<ProductosCantidad>();
    DialogHacerPedido hacerPedido;
    DialogCarrito carrito;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda_usuarios);
        tiendaKey = getIntent().getStringExtra("keyTienda");
        tiendaNombre= getIntent().getStringExtra("nombreTienda");
        Toast.makeText(getApplicationContext(),tiendaKey,Toast.LENGTH_SHORT).show();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        NestedScrollView scrollview = (NestedScrollView) findViewById(R.id.scrollViewUp);
        scrollview.setNestedScrollingEnabled(true);

        toolbar.setBackgroundColor(getResources().getColor(R.color.Carne));
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(tiendaNombre);

        toolBarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.logo));
        toolBarLayout.setExpandedTitleColor(getResources().getColor(R.color.logo));
        toolBarLayout.setBackgroundColor(getResources().getColor(R.color.Carne));

        filtro = findViewById(R.id.buscarPorNombre);
        filtrar = findViewById(R.id.buscarfiltro1);
        filtrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listafiltrada.clear();
                getProductos();
                ponerTodoOK();
            }
        });


        LinearLayoutManager manager = new LinearLayoutManager(this);
        refrescarPorductos = findViewById(R.id.refreshProductos);
       retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.conexionAPI))
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        UserDTOAPI user = new UserDTOAPI(FirebaseAuth.getInstance().getCurrentUser().getUid(),"preuba",FirebaseAuth.getInstance().getCurrentUser().getEmail());
        
        cogerDatos(user);
        

        listaProductos = findViewById(R.id.listProductos);
        noProductos = findViewById(R.id.noProductos);
        refresh = findViewById(R.id.recargarSinProducto);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getProductos();
                ponerTodoOK();
            }
        });
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
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!pedidoActual.isEmpty()) {
                    carrito = new DialogCarrito();
                    carrito.setProductosPedido(pedidoActual);
                    carrito.setIdUsuario(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    carrito.setIdTienda(tiendaKey);
                    carrito.show(getSupportFragmentManager(), "example dialog");
                }else{
                    Toast.makeText(getApplicationContext(),"No tienes articulos en el carrito",Toast.LENGTH_SHORT).show();
                }
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

        Call<List<ProductoDTO>> llamada = bokyTakeAPI.getProductos(tiendaKey);

        llamada.enqueue(new Callback<List<ProductoDTO>>() {
            @Override
            public void onResponse(Call<List<ProductoDTO>> call, Response<List<ProductoDTO>> response) {
                if(response.isSuccessful()){
                    listaCorrecta.clear();
                    lista = response.body();

                    for (ProductoDTO pdto: lista){
                        if(pdto.getMostrarApp()){
                            listaCorrecta.add(pdto);
                        }
                    }

                    if(filtro.getEditText().getText().toString().isEmpty()){
                        if(!listaCorrecta.isEmpty()) {
                            listaProductos.setVisibility(View.VISIBLE);
                            noProductos.setVisibility(View.GONE);
                            adaptador = new AdapterProductosRV(listaCorrecta, TiendaUsuarios.this::onButtonClick);
                            listaProductos.setAdapter(adaptador);
                        }else{
                            listaProductos.setVisibility(View.GONE);
                            noProductos.setVisibility(View.VISIBLE);
                        }
                    }else{
                        for(ProductoDTO filtrado:listaCorrecta){
                            if(filtrado.getNombre().contains(filtro.getEditText().getText().toString())){
                                listafiltrada.add(filtrado);
                            }
                            if(!listafiltrada.isEmpty()) {
                                listaProductos.setVisibility(View.VISIBLE);
                                noProductos.setVisibility(View.GONE);
                                adaptador = new AdapterProductosRV(listafiltrada, TiendaUsuarios.this::onButtonClick);
                                listaProductos.setAdapter(adaptador);
                            }else{
                                listaProductos.setVisibility(View.GONE);
                                noProductos.setVisibility(View.VISIBLE);
                            }
                        }
                    }


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
        //Toast.makeText(getApplicationContext(),"nombre producto "+lista.get(posicion).getNombre(),Toast.LENGTH_SHORT).show();
        hacerPedido = new DialogHacerPedido();
        hacerPedido.producto = lista.get(posicion);
        hacerPedido.show(getSupportFragmentManager(),"example dialog");

       //hacerPedido.precioProducto.setText(lista.get(posicion).getPrecio()+" €");
        //hacerPedido.nombreProducto.setText(lista.get(posicion).getNombre());
    }

    @Override
    public void applyPedido(ProductosCantidad productosCantidad) {
        if(pedidoActual.isEmpty()) {

            pedidoActual.add(productosCantidad);

        }else{

                if(pedidoActual.contains(productosCantidad)){
                    pedidoActual.remove(productosCantidad);
                    carrito.productosCarrito.notifyDataSetChanged();
                    productosCantidad.setCantidad(productosCantidad.getCantidad()+ productosCantidad.getCantidad());
                    pedidoActual.add(productosCantidad);

                }else{
                    pedidoActual.add(productosCantidad);



                }

        }

        reCargar();

    }

    private void reCargar() {

        Toast.makeText(getApplicationContext(),"numero de items metidos"+ pedidoActual.size(),Toast.LENGTH_SHORT).show();

        fab.setImageDrawable(getDrawable(R.drawable.cart_shopping_items));

    }
}