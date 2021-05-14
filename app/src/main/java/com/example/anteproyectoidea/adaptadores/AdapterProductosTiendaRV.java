package com.example.anteproyectoidea.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anteproyectoidea.R;
import com.example.anteproyectoidea.dto.ProductoDTO;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterProductosTiendaRV extends RecyclerView.Adapter<AdapterProductosTiendaRV.ViewHolder>{

    private List<ProductoDTO> productos;
    private AdapterProductosTiendaRV.OnButtonListenerClick onClickButton;

    public AdapterProductosTiendaRV(List<ProductoDTO> productos, AdapterProductosTiendaRV.OnButtonListenerClick onClickButton) {
        this.productos = productos;
        this.onClickButton=onClickButton;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView nombre , precio;
        private Button btnComprar;
        private ImageView imagenProducto;
        private ImageView monstrarApp;
        AdapterProductosTiendaRV.OnButtonListenerClick onClickButton;


        public ViewHolder(@NonNull View itemView , AdapterProductosTiendaRV.OnButtonListenerClick onClickButton) {
            super(itemView);

            nombre = itemView.findViewById(R.id.listProducNombretienda);
            precio = itemView.findViewById(R.id.listProducPreciotienda);
            btnComprar = itemView.findViewById(R.id.listProducComprartienda);
            imagenProducto = itemView.findViewById(R.id.listProducImagentienda);
            monstrarApp = itemView.findViewById(R.id.monstrarAppicn);
            this.onClickButton = onClickButton;
            btnComprar.setOnClickListener(this);

            //Picasso.get().load(imagenUri).into(imagen);

        }

        @Override
        public void onClick(View v) { onClickButton.onButtonClick(getAbsoluteAdapterPosition());
        }
    }

    @NonNull
    @Override
    public AdapterProductosTiendaRV.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tienda_productos, parent, false);


        return new AdapterProductosTiendaRV.ViewHolder(view, onClickButton);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProductosTiendaRV.ViewHolder holder, int position) {
        Picasso.get().load(productos.get(position).getUrlImagen()).into(holder.imagenProducto);
        if(productos.get(position).getMostrarApp()){
           holder.monstrarApp.setImageResource(R.drawable.ic_si_mosntrar);
        }else{
            holder.monstrarApp.setImageResource(R.drawable.ic_no_mosntrar);
        }

        holder.nombre.setText(productos.get(position).getNombre());
        holder.precio.setText(String.valueOf(productos.get(position).getPrecio()+" €"));
        //holder.imagenProducto.setPadding(5,5,5,5);



    }



    @Override
    public int getItemCount() {
        return productos.size();
    }

    public interface OnButtonListenerClick{
        void onButtonClick(int posicion);

    }

}



