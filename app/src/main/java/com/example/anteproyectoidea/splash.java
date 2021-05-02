package com.example.anteproyectoidea;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
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
        prueba.setText("Gola cambio");

        Thread sqlHilo = new Thread() {
            @Override
            public void run() {
                Connection ctn = null;

                try {


                    prueba.setText("pedro");
                    Class.forName("com.mysql.jdbc.Driver");
                   // jdbc:google:mysql://<instance connection name>/<database>
                    ctn = DriverManager.getConnection("jdbc:google:mysql:////jardinerias-paca:europe-west1:bokytake/Ferreteria", "root", "bokytake");
                    String stsql = "Select * From prueba";
                    Statement st = ctn.createStatement();
                    ResultSet rs = st.executeQuery(stsql);

                    //Log.i("preuba",rs.getString(1));
                    prueba.setText("pedro");
                    prueba.setText(rs.getString(0));

                } catch (ClassNotFoundException e) {
                    prueba.setText("error clase");
                } catch (SQLException e) {
                    prueba.setText("error conexion"+e.getMessage());
                }

            }


        };
        sqlHilo.run();




        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                Intent main = new Intent(getApplicationContext(), Registro.class);
                startActivity(main);
                finish();
            }
        };

        Timer timer = new Timer();

        timer.schedule(task, 15000);



        }



}