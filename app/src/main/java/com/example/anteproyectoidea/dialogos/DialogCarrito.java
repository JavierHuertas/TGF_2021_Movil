package com.example.anteproyectoidea.dialogos;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.anteproyectoidea.R;
import com.example.anteproyectoidea.adaptadores.AdapterProductosCarrito;
import com.example.anteproyectoidea.dto.ProductosCantidad;

import java.util.ArrayList;

public class DialogCarrito extends AppCompatDialogFragment {

    public ArrayList<ProductosCantidad> productosPedido;
    public ListView listaPedidos;
    public TextView preciototal;
    public AdapterProductosCarrito productosCarrito;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view =inflater.inflate(R.layout.dialog_cart,null);
        listaPedidos= view.findViewById(R.id.itemsCarts);
        preciototal = view.findViewById(R.id.totalPedido);

        productosCarrito = new AdapterProductosCarrito(productosPedido,builder.getContext());
        listaPedidos.setAdapter(productosCarrito);


        builder.setTitle("SuCompra").setView(view)
                .setPositiveButton("Realizar pedido", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();


        return builder.create();



    }

    public ArrayList<ProductosCantidad> getProductosPedido() {
        return productosPedido;
    }

    public void setProductosPedido(ArrayList<ProductosCantidad> productosPedido) {
        this.productosPedido = productosPedido;
    }

    public AdapterProductosCarrito getProductosCarrito() {
        return productosCarrito;
    }

    public void setProductosCarrito(AdapterProductosCarrito productosCarrito) {
        this.productosCarrito = productosCarrito;
    }
}
