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

    public AdapterProductosRV(List<ProductoDTO> productos) {
        this.productos = productos;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nombre , precio;
        private Button btnComprar;
        private ImageView imagenProducto;



        public TextView getNombre() {
            return nombre;
        }

        public void setNombre(TextView nombre) {
            this.nombre = nombre;
        }

        public TextView getPrecio() {
            return precio;
        }

        public void setPrecio(TextView precio) {
            this.precio = precio;
        }

        public Button getBtnComprar() {
            return btnComprar;
        }

        public void setBtnComprar(Button btnComprar) {
            this.btnComprar = btnComprar;
        }

        public ImageView getImagenProducto() {
            return imagenProducto;
        }

        public void setImagenProducto(ImageView imagenProducto) {
            this.imagenProducto = imagenProducto;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.listProducNombre);
            precio = itemView.findViewById(R.id.listProducPrecio);
            btnComprar = itemView.findViewById(R.id.listProducComprar);
            imagenProducto = itemView.findViewById(R.id.listProducImagen);

            btnComprar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(),productos.get(getAbsoluteAdapterPosition()).getNombre(),Toast.LENGTH_SHORT);
                }
            });

            //Picasso.get().load(imagenUri).into(imagen);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_productos_tienda, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(productos.get(position).getUrlImagen()).into(holder.imagenProducto);
        holder.nombre.setText(productos.get(position).getNombre());
        holder.precio.setText(String.valueOf(productos.get(position).getPrecio()));
        holder.imagenProducto.setPadding(5,5,5,5);
        holder.imagenProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(),"hola prueba "+productos.get(position).getNombre(),Toast.LENGTH_SHORT).show();
            }
        });


    }



    @Override
    public int getItemCount() {
        return productos.size();
    }


}
