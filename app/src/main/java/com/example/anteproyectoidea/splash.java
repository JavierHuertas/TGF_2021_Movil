package com.example.anteproyectoidea;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;


import com.example.anteproyectoidea.registro.Registro;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Timer;
import java.util.TimerTask;



public class splash extends AppCompatActivity {

    TextView prueba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        prueba = findViewById(R.id.preubajdbc);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                Intent main = new Intent(getApplicationContext(), Registro.class);
                startActivity(main);
                finish();
            }
        };

        Timer timer = new Timer();

        timer.schedule(task, 3000);



        }



}