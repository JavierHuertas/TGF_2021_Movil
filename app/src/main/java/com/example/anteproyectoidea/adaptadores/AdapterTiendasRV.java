package com.example.anteproyectoidea.adaptadores;


import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anteproyectoidea.R;
import com.example.anteproyectoidea.ui.TiendaUsuarios;
import com.example.anteproyectoidea.dto.TiendaDTO;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
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
        holder.googleMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+String.valueOf(tienda.getLatitud()) +","+String.valueOf(tienda.getLongitud())+"&mode=w");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");

                holder.itemView.getContext().startActivity(mapIntent);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(holder.itemView.getContext(), TiendaUsuarios.class);
               intent.putExtra("keyTienda",tienda.getKey());
               intent.putExtra("nombreTienda",tienda.getNombreComercio());
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @NonNull
    @Override
    public TiendasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_tienda_listview,parent,false);
        return new TiendasViewHolder(view);
    }

    class  TiendasViewHolder extends RecyclerView.ViewHolder {
        CardView tienda;
        ImageView tindaLogo;
        TextView nombreTienda,direccionTienda;
        Button googleMaps;
        public TiendasViewHolder(@NonNull View itemView) {
            super(itemView);
            tienda = itemView.findViewById(R.id.card);
            nombreTienda = itemView.findViewById(R.id.listTiendasName);
            direccionTienda = itemView.findViewById(R.id.listTiendaDireccion);
            tindaLogo = itemView.findViewById(R.id.listTiendaLogo);
            googleMaps = itemView.findViewById(R.id.listTiendabtnUno);
        }
       // public void onclick(int position){
         //   getSnapshots().getSnapshot(position).getReference().delete()
        //}
    }
    public  class ShopHolder extends RecyclerView.ViewHolder{
        CardView tienda;
        Button googleMaps;
        ImageView tindaLogo;
        TextView nombreTienda,direccionTienda;

        public ShopHolder(@NonNull View itemView) {
            super(itemView);
            tienda = itemView.findViewById(R.id.card);
            nombreTienda = itemView.findViewById(R.id.listTiendasName);
            direccionTienda = itemView.findViewById(R.id.listTiendaDireccion);
            tindaLogo = itemView.findViewById(R.id.listTiendaLogo);
            googleMaps = itemView.findViewById(R.id.listTiendabtnUno);

            tienda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });


        }
    }

}
