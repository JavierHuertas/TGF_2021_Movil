package com.example.anteproyectoidea.adaptadores;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anteproyectoidea.R;
import com.example.anteproyectoidea.dto.ProductoDTO;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterProductos extends BaseAdapter {

    private Context context;
    private List<ProductoDTO> productoDTOS;

    public AdapterProductos(Context context, List<ProductoDTO> productoDTOS) {
        this.context = context;
        this.productoDTOS = productoDTOS;
    }

    @Override
    public int getCount() {
        return productoDTOS.size();
    }

    @Override
    public Object getItem(int position) {
        return productoDTOS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return productoDTOS.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inf = LayoutInflater.from(context);
        convertView= inf.inflate(R.layout.item_productos_tienda, null);

        TextView nombre = convertView.findViewById(R.id.listProducNombre);
        TextView precio = convertView.findViewById(R.id.listProducPrecio);
        ImageView imagen = convertView.findViewById(R.id.listProducImagen);
        Button btnComprar = convertView.findViewById(R.id.listProducComprar);

        nombre.setText(productoDTOS.get(position).getNombre());
        precio.setText(String.valueOf(productoDTOS.get(position).getPrecio()));
        Uri imagenUri = Uri.parse(productoDTOS.get(position).getUrlImagen());
        Picasso.get().load(imagenUri).into(imagen);

        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"prueba",Toast.LENGTH_SHORT).show();
            }
        });



        return convertView;



    }
}
