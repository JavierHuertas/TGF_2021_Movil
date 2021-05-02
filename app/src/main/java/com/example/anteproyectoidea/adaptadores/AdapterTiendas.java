package com.example.anteproyectoidea.adaptadores;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anteproyectoidea.R;
import com.example.anteproyectoidea.dto.TiendaDTO;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterTiendas extends BaseAdapter {

    private ArrayList<TiendaDTO> tiendasDTOs;
    private Context contexto;

    public AdapterTiendas(ArrayList<TiendaDTO> tiendasDTOs, Context contexto) {
        this.tiendasDTOs = tiendasDTOs;
        this.contexto = contexto;
    }

    @Override
    public int getCount() {
        return tiendasDTOs.size();
    }

    @Override
    public Object getItem(int position) {
        return tiendasDTOs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inf = LayoutInflater.from(contexto);
        convertView= inf.inflate(R.layout.item_tienda_listview, null);

        TextView nombreDuenio = convertView.findViewById(R.id.listTiendasName);
        TextView direccion = convertView.findViewById(R.id.listTiendaDireccion);
        ImageView fotoLogo = convertView.findViewById(R.id.listTiendaLogo);

        nombreDuenio.setText(tiendasDTOs.get(position).getNombreComercio());
        direccion.setText(tiendasDTOs.get(position).getLocalizacion());
        Picasso.get().load(Uri.parse(tiendasDTOs.get(position).getLogoTienda())).into(fotoLogo);



        return convertView;
    }
}
