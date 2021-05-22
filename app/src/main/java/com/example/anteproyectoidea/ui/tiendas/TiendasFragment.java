package com.example.anteproyectoidea.ui.tiendas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anteproyectoidea.MainActivity;
import com.example.anteproyectoidea.R;
import com.example.anteproyectoidea.adaptadores.AdapterTiendas;
import com.example.anteproyectoidea.adaptadores.AdapterTiendasRV;
import com.example.anteproyectoidea.dto.TiendaDTO;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TiendasFragment extends Fragment {

    private FirebaseFirestore db;
    private TextInputLayout tiendaPorNombre;
    private Button botonBuscarNombre;
    private TiendasViewModel tiendasViewModel;
    private AdapterTiendasRV adapterRV;
    private RecyclerView tiendasView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        tiendasViewModel = new ViewModelProvider(this).get(TiendasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        tiendasView = root.findViewById(R.id.tiendaList);
        db = FirebaseFirestore.getInstance();
        tiendaPorNombre = root.findViewById(R.id.buscarPorNombreTienda);
        botonBuscarNombre = root.findViewById(R.id.buscarfiltro2);

        botonBuscarNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allShops();
            }
        });
        allShops();


        tiendasViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }


    public void allShops(){
        Query query;
        if(tiendaPorNombre.getEditText().getText().toString().isEmpty()){
            query = FirebaseFirestore.getInstance()
                    .collection("shops");
        }else {
            query = FirebaseFirestore.getInstance().collection("shops").whereEqualTo("nombreComercio",tiendaPorNombre.getEditText().getText().toString());
        }




        FirestoreRecyclerOptions<TiendaDTO> options = new FirestoreRecyclerOptions.Builder<TiendaDTO>().setQuery(query,TiendaDTO.class).build();
        adapterRV = new AdapterTiendasRV(options);
        tiendasView.setAdapter(adapterRV);
        adapterRV.startListening();






    }
}