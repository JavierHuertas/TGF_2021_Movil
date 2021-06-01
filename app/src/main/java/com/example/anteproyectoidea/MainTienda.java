package com.example.anteproyectoidea;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.anteproyectoidea.adaptadores.AdapterProductosRV;
import com.example.anteproyectoidea.adaptadores.AdapterProductosTiendaRV;
import com.example.anteproyectoidea.dialogos.DialogHacerPedido;
import com.example.anteproyectoidea.dto.ProductoDTO;
import com.example.anteproyectoidea.registro.Registro;
import com.example.anteproyectoidea.ui.TiendaUsuarios;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
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
    private TextInputLayout busquedaPorNombre;
    private List<ProductoDTO> listafiltro = new ArrayList<>();
    LinearLayout tieneProductos,noTieneProductos;
    private Button filtroname;
    private SwipeRefreshLayout refrescarPorductos;
    private Retrofit retrofit;
    private Button nuevoDeCero;
    private FloatingActionButton newProducto,pedidos,volver;
    CollapsingToolbarLayout toolBarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tienda);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        toolBarLayout = findViewById(R.id.toolbar_layout_tienda);
        findNameShop();
        nuevoDeCero = findViewById(R.id.crearNuevosinNotiene);
        nuevoDeCero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnProductoNuevo(null);
            }
        });
        busquedaPorNombre =findViewById(R.id.buscarPorNombre);
        newProducto = findViewById(R.id.newProducto);
        tieneProductos = findViewById(R.id.tieneProductos);
        noTieneProductos= findViewById(R.id.noTieneProductos);
        newProducto.setOnClickListener(this::btnProductoNuevo);
        filtroname = findViewById(R.id.buscarfiltro1);
        filtroname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ponerTodoOK();
                getProductos();
            }
        });

        pedidos = findViewById(R.id.pedidosTienda);
        pedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PedidosTienda.class);
                startActivity(intent);
            }
        });

        volver = findViewById(R.id.vovlerAtras);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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

    private void findNameShop() {
      String nameShop = "error";

        FirebaseFirestore.getInstance().collection("shops").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                   toolBarLayout.setTitle(documentSnapshot.getString("nombreComercio"));


                    //Picasso.get().load(uri2).into();
                }else{
                    Toast.makeText(getApplicationContext(),"Hay un error buscar solucioness",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void getProductos(){

        BokyTakeAPI bokyTakeAPI = retrofit.create(BokyTakeAPI.class);

        Call<List<ProductoDTO>> llamada = bokyTakeAPI.getProductos(FirebaseAuth.getInstance().getCurrentUser().getUid());

        llamada.enqueue(new Callback<List<ProductoDTO>>() {
            @Override
            public void onResponse(Call<List<ProductoDTO>> call, Response<List<ProductoDTO>> response) {
                if(response.isSuccessful()){
                    lista = response.body();

                    if(busquedaPorNombre.getEditText().getText().toString().isEmpty()){
                        listafiltro = lista;
                    }else{
                        for(ProductoDTO filtro:lista){
                            if(filtro.getNombre().contains(busquedaPorNombre.getEditText().getText().toString())){
                                listafiltro.add(filtro);
                            }
                        }
                    }

                    if(lista.isEmpty()){
                        noTieneProductos.setVisibility(View.VISIBLE);
                        tieneProductos.setVisibility(View.GONE);
                    }else {
                        noTieneProductos.setVisibility(View.GONE);
                        tieneProductos.setVisibility(View.VISIBLE);
                        adaptador = new AdapterProductosTiendaRV(listafiltro, MainTienda.this::onButtonClick);
                        listaProductos.setAdapter(adaptador);
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

    public void ponerTodoOK(){
        listafiltro.clear();
        refrescarPorductos.setEnabled(false);
        refrescarPorductos.setEnabled(true);
        refrescarPorductos.setRefreshing(false);
        //refrescarPorductos.setRefreshing(true);
    }

    public void btnProductoNuevo(View v){
        Toast.makeText(getApplicationContext(),"nuevo Producto",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(),EditarProducto.class);
        intent.putExtra("esEditar",false);
        startActivity(intent);
    }

    @Override
    public void onButtonClick(int posicion) {
        Intent intent = new Intent(getApplicationContext(),EditarProducto.class);
        intent.putExtra("editar",lista.get(posicion));
        intent.putExtra("esEditar",true);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getProductos();
    }
}