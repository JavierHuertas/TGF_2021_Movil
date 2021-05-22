package com.example.anteproyectoidea.adaptadores;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anteproyectoidea.R;
import com.example.anteproyectoidea.dto.ProductoDTO;
import com.example.anteproyectoidea.dto.ProductosCantidad;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterProductosPedidosUsuario  extends BaseAdapter {

        private Context context;
        private List<ProductosCantidad> productosCantidads;

        public AdapterProductosPedidosUsuario(Context context, List<ProductosCantidad> productosCantidads) {
            this.context = context;
            this.productosCantidads = productosCantidads;
        }

        @Override
        public int getCount() {
            return productosCantidads.size();
        }

        @Override
        public Object getItem(int position) {
            return productosCantidads.get(position);
        }

        @Override
        public long getItemId(int position) {
            return productosCantidads.get(position).getProductoDTO().getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inf = LayoutInflater.from(context);
            convertView= inf.inflate(R.layout.item_pedidos_productos_pedidos, null);

            TextView nombre = convertView.findViewById(R.id.pedidonombrePedidoproducto);
            TextView cantidad = convertView.findViewById(R.id.pedidoCantidadPedido);
            TextView precio = convertView.findViewById(R.id.pedidoprecioTotalProducto);
            ImageView imagen = convertView.findViewById(R.id.productoPedidoImagen);

            cantidad.setText(productosCantidads.get(position).getCantidad()+"/Und");
            nombre.setText(productosCantidads.get(position).getProductoDTO().getNombre());
            precio.setText(productosCantidads.get(position).getProductoDTO().getPrecio() * productosCantidads.get(position).getCantidad()+" €");
            Uri imagenUri = Uri.parse(productosCantidads.get(position).getProductoDTO().getUrlImagen());
            Picasso.get().load(imagenUri).into(imagen);



            return convertView;



        }
    }

