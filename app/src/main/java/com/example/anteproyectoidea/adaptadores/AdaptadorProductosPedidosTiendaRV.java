package com.example.anteproyectoidea.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anteproyectoidea.R;
import com.example.anteproyectoidea.dto.PedidoDTO;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


public class AdaptadorProductosPedidosTiendaRV extends RecyclerView.Adapter<AdaptadorProductosPedidosTiendaRV.ViewHolder> {




    private List<PedidoDTO> pedidos;

    public AdaptadorProductosPedidosTiendaRV(List<PedidoDTO> pedidos) {
        this.pedidos = pedidos;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nombreTienda, estado , precioTotal , idPedido, fechaPedido;
        private ListView productos;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nombreTienda = itemView.findViewById(R.id.pedidoTextNombreTienda);
            estado = itemView.findViewById(R.id.pedidoEstadoitem);
            precioTotal = itemView.findViewById(R.id.pedidoPrecioTotal);
            idPedido = itemView.findViewById(R.id.textPedidoConId);
            fechaPedido = itemView.findViewById(R.id.pedidoFechaPedidoitem);
            productos = itemView.findViewById(R.id.pedidosProductos);
            //productos.setMinimumHeight(150);

        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_pedidos_usuario, parent, false);

        return new AdaptadorProductosPedidosTiendaRV.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.idPedido.setText("Pedido con id: "+pedidos.get(position).getId());
        holder.estado.setText(pedidos.get(position).getEstados());
        int anio,mes,dia;
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(pedidos.get(position).getFreserva());

        anio = gregorianCalendar.get(Calendar.YEAR);
        mes = gregorianCalendar.get(Calendar.MONTH);
        dia = gregorianCalendar.get(Calendar.DAY_OF_MONTH);
        holder.fechaPedido.setText(dia+"/"+mes+"/"+anio);
        holder.precioTotal.setText(pedidos.get(position).getImporte()+" €");
        holder.nombreTienda.setText("Usuario "+  pedidos.get(position).getEmail());
        AdapterProductosPedidosUsuario adapter = new AdapterProductosPedidosUsuario(holder.itemView.getContext(),pedidos.get(position).getProductosCantidads());
        holder.productos.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return pedidos.size();
    }



}
