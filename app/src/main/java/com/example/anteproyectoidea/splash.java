package com.example.anteproyectoidea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;



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

        timer.schedule(task ,2000);

    }
}