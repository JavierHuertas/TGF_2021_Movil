package com.example.anteproyectoidea.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anteproyectoidea.R;
import com.example.anteproyectoidea.dto.ProductoDTO;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterProductosRV extends RecyclerView.Adapter<AdapterProductosRV.ViewHolder>{

    private List<ProductoDTO> productos;
    private OnButtonListenerClick onClickButton;
    public AdapterProductosRV(List<ProductoDTO> productos,OnButtonListenerClick onClickButton) {
        this.productos = productos;
        this.onClickButton=onClickButton;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView nombre , precio;
        private Button btnComprar;
        private ImageView imagenProducto;
        OnButtonListenerClick onClickButton;


        public ViewHolder(@NonNull View itemView ,OnButtonListenerClick onClickButton) {
            super(itemView);

            nombre = itemView.findViewById(R.id.listProducNombre);
            precio = itemView.findViewById(R.id.listProducPrecio);
            btnComprar = itemView.findViewById(R.id.listProducComprar);
            imagenProducto = itemView.findViewById(R.id.listProducImagen);

            this.onClickButton = onClickButton;

            btnComprar.setOnClickListener(this);

            //Picasso.get().load(imagenUri).into(imagen);

        }

        @Override
        public void onClick(View v) {
        onClickButton.onButtonClick(getAbsoluteAdapterPosition());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_productos_tienda, parent, false);


        return new ViewHolder(view,onClickButton);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(productos.get(position).getUrlImagen()).into(holder.imagenProducto);
        holder.nombre.setText(productos.get(position).getNombre());
        holder.precio.setText(String.valueOf(productos.get(position).getPrecio()));
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
