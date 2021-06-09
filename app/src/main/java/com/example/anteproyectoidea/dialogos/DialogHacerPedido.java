package com.example.anteproyectoidea.dialogos;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.anteproyectoidea.R;
import com.example.anteproyectoidea.dto.ProductoDTO;
import com.example.anteproyectoidea.dto.ProductosCantidad;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Locale;

public class DialogHacerPedido extends AppCompatDialogFragment{


    public ImageView imagenProducto;
    public TextView nombreProducto,precioProducto,importeTotal,descripcion;
    public ProductoDTO producto;
    public NumberPicker cantidad;

    private DialogoPedidoListener dialogHacerPedidoListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view =inflater.inflate(R.layout.dialog_buy,null);
        cantidad = view.findViewById(R.id.elegirCantidad);
        imagenProducto= view.findViewById(R.id.imgProductoDialog);
        nombreProducto= view.findViewById(R.id.nombreArticuloDialog);
        importeTotal = view.findViewById(R.id.importeTotal);
        importeTotal.setText(producto.getPrecio()+" €");
        descripcion = view.findViewById(R.id.descripcionProductoDialog);
        descripcion.setText(producto.getDescripcion());
        precioProducto= view.findViewById(R.id.producPrecioDialog);
        precioProducto.setText(producto.getPrecio()+" €");
        nombreProducto.setText("precio");
        cantidad.setMinValue(1);
        cantidad.setMaxValue(producto.getCantidad());
        Picasso.get().load(producto.getUrlImagen()).into(imagenProducto);
        builder.setPositiveButton("añadir al carrito", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ProductosCantidad productosCantidad= new ProductosCantidad(producto,cantidad.getValue());
                dialogHacerPedidoListener.applyPedido(productosCantidad);
            }
        });
        builder.setNeutralButton("cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setView(view)
                .setTitle(producto.getNombre())
                .create();

        cantidad.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                double precio= newVal*producto.getPrecio();
                importeTotal.setText(precio+" €");
            }
        });

        /*cantidad.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return NumberFormat.getCurrencyInstance(Locale.CANADA).format((long)value).toString();
            }
        });*/
    return builder.create();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            dialogHacerPedidoListener = (DialogoPedidoListener) context;
        } catch (ClassCastException e) {
            throw  new ClassCastException(context.toString()+"tienes que implentar el metodo listenerr en la clase");
        }
    }

    public interface DialogoPedidoListener{
            void applyPedido(ProductosCantidad productosCantidad);
    }


}
