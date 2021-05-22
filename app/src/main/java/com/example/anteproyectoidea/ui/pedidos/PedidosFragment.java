package com.example.anteproyectoidea.ui.pedidos;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anteproyectoidea.BokyTakeAPI;
import com.example.anteproyectoidea.R;
import com.example.anteproyectoidea.adaptadores.AdaptadorProductosPedidosUsuarioRV;
import com.example.anteproyectoidea.adaptadores.AdapterProductosRV;
import com.example.anteproyectoidea.dto.PedidoDTO;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PedidosFragment extends Fragment {

    private AdaptadorProductosPedidosUsuarioRV adaptador;
    private List<PedidoDTO> pedidos;
    private List<PedidoDTO> pedidosFiltrados = new ArrayList<>();
    private TextView texto;
    private RecyclerView pedidosview;
    private Retrofit retrofit;
    private String filtro;
    private Spinner options;
    private PedidosViewModel pedidosViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        pedidosViewModel = new ViewModelProvider(this).get(PedidosViewModel.class);
        View root = inflater.inflate(R.layout.fragmen_pedidos_usuario, container, false);
        pedidosview = root.findViewById(R.id.pedidosUsuarios);
        texto = root.findViewById(R.id.mensajeNoHarelaizado);
        retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.conexionAPI))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        options = root.findViewById(R.id.option);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.estados_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        options.setAdapter(adapter);
        options.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filtro= parent.getItemAtPosition(position).toString();
                pedidosFiltrados.clear();
                cargarDatos();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                pedidosFiltrados.clear();
                filtro="";
            cargarDatos();
            }
        });
        cargarDatos();



        pedidosViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    private void cargarDatos() {
        BokyTakeAPI bokyTakeAPI = retrofit.create(BokyTakeAPI.class);

        Call<List<PedidoDTO>> llamada = bokyTakeAPI.getPedidousuario(FirebaseAuth.getInstance().getUid());


        llamada.enqueue(new Callback<List<PedidoDTO>>() {
            @Override
            public void onResponse(Call<List<PedidoDTO>> call, Response<List<PedidoDTO>> response) {
                pedidos = response.body();

                if(pedidos.isEmpty()){
                    texto.setVisibility(View.VISIBLE);
                    pedidosview.setVisibility(View.GONE);
                }else {
                    pedidosview.setVisibility(View.VISIBLE);
                    texto.setVisibility(View.GONE);
                    if(filtro.isEmpty() || filtro.equals("")||filtro.equalsIgnoreCase("Elige opcion filtrado")){
                        adaptador = new AdaptadorProductosPedidosUsuarioRV(pedidos);
                        pedidosview.setAdapter(adaptador);
                    }else{
                        for(PedidoDTO pedido : pedidos){
                            if(pedido.getEstados().equalsIgnoreCase(filtro)){
                                pedidosFiltrados.add(pedido);
                            }
                            if(pedidosFiltrados.isEmpty()){
                                texto.setText(Html.fromHtml("No hay ningun pedido en "+ "<b>" + filtro + "</b>"+"<br>¿Tienes algun pedido?"));
                                texto.setVisibility(View.VISIBLE);
                                pedidosview.setVisibility(View.GONE);
                            }
                            adaptador = new AdaptadorProductosPedidosUsuarioRV(pedidosFiltrados);
                            pedidosview.setAdapter(adaptador);
                        }
                    }


                }

            }

            @Override
            public void onFailure(Call<List<PedidoDTO>> call, Throwable t) {

            }
        });


    }
}