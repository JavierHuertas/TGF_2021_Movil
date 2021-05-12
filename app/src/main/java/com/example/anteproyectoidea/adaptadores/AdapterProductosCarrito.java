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

import com.example.anteproyectoidea.R;
import com.example.anteproyectoidea.dto.ProductosCantidad;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterProductosCarrito extends BaseAdapter {

    private ArrayList<ProductosCantidad> productosCatidad;
    private Context context;

    public AdapterProductosCarrito(ArrayList<ProductosCantidad> productosCatidad, Context context) {
        this.productosCatidad = productosCatidad;
        this.context = context;
    }

    @Override
    public int getCount() {
        return productosCatidad.size();
    }

    @Override
    public Object getItem(int position) {
        return productosCatidad.get(position);
    }

    @Override
    public long getItemId(int position) {
        return productosCatidad.get(position).getProductoDTO().getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inf = LayoutInflater.from(context);
        convertView= inf.inflate(R.layout.items_cart, null);

        ImageButton borrar = convertView.findViewById(R.id.carritoBorrar);

        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productosCatidad.remove(position);
                notifyDataSetChanged();
            }
        });

        TextView nombre = convertView.findViewById(R.id.nombreCarrito);
        TextView precioTotal = convertView.findViewById(R.id.precioTotalProductoCarrito);
        TextView cantidad = convertView.findViewById(R.id.cantidadCarrito);
        ImageView imagen = convertView.findViewById(R.id.carritoImagen);

        nombre.setText(productosCatidad.get(position).getProductoDTO().getNombre());
        cantidad.setText(productosCatidad.get(position).getCantidad()+"");
        Picasso.get().load(productosCatidad.get(position).getProductoDTO().getUrlImagen()).into(imagen);
        double precio = productosCatidad.get(position).getProductoDTO().getPrecio() * productosCatidad.get(position).getCantidad();
        precioTotal.setText(precio+" €");



        return convertView;
    }
}
