package com.example.anteproyectoidea.adaptadores;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anteproyectoidea.R;
import com.example.anteproyectoidea.dto.TiendaDTO;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class AdapterTiendasRV extends FirestoreRecyclerAdapter<TiendaDTO, AdapterTiendasRV.TiendasViewHolder> {


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdapterTiendasRV(@NonNull FirestoreRecyclerOptions<TiendaDTO> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull TiendasViewHolder holder, int position, @NonNull TiendaDTO tienda) {

        String uriImagne =tienda.getLogoTienda();
        Picasso.get().load(uriImagne).into(holder.tindaLogo);

        holder.direccionTienda.setText(tienda.getLocalizacion());
        holder.nombreTienda.setText(tienda.getNombreComercio());

    }

    @NonNull
    @Override
    public TiendasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_tienda_listview,parent,false);
        return new TiendasViewHolder(view);
    }

    class  TiendasViewHolder extends RecyclerView.ViewHolder {
        ImageView tindaLogo;
        TextView nombreTienda,direccionTienda;
        public TiendasViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreTienda = itemView.findViewById(R.id.listTiendasName);
            direccionTienda = itemView.findViewById(R.id.listTiendaDireccion);
            tindaLogo = itemView.findViewById(R.id.listTiendaLogo);
        }
    }

}
