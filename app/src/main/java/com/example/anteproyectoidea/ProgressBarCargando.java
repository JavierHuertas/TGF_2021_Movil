package com.example.anteproyectoidea;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.TextView;

public class ProgressBarCargando {

    Activity activity;
    AlertDialog alertDialog;


    public ProgressBarCargando(Activity miActivity){
        activity = miActivity;

    }

    public void StarProgressBar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.activity_progress_bar_cargando, null));
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.show();

    }

    public void finishProgressBar(){
        alertDialog.dismiss();
    }
}