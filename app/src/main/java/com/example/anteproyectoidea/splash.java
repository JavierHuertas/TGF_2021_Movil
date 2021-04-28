package com.example.anteproyectoidea;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;


import com.example.anteproyectoidea.registro.Registro;

import java.util.Timer;
import java.util.TimerTask;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();


        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent main = new Intent(getApplicationContext(), Registro.class);
                startActivity(main);
                finish();
            }
        };

        Timer timer = new Timer();

        timer.schedule(task, 2000);



        }



}