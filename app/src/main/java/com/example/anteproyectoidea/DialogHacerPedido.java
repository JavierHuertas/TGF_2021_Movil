package com.example.anteproyectoidea;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogHacerPedido extends AppCompatDialogFragment{

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view =inflater.inflate(R.layout.dialog_buy,null);

        builder.setView(view)
                .setTitle("Hola")
                .create();

    return builder.create();
    }
}
