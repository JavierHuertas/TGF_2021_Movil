package com.example.anteproyectoidea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.anteproyectoidea.logins.LoginEmpresa;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class EmpresaRegistrarLogin extends AppCompatActivity {

    private RelativeLayout empresaLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa_registrar_login);
        getSupportActionBar().hide();
    empresaLogin = findViewById(R.id.latoutEmpresaLogin);
    elegirFondo(empresaLogin);



    }

    public void hacerclick(View v){

        Intent intent;
        if(v.getId() == R.id.loginEmpresa){
            //inicar sesion empresas
            intent = new Intent(getApplicationContext(), LoginEmpresa.class);
            startActivity(intent);

            Toast.makeText(getApplicationContext(),"Iniciar actividad LoginEmpresa",Toast.LENGTH_SHORT).show();
        }else{
            //registrase empresa
            intent = new Intent(getApplicationContext(), RegistroTienda.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(),"Iniciar actividad registrase",Toast.LENGTH_SHORT).show();
        }

    }

    private void elegirFondo(RelativeLayout empresaLogin) {
        int numero = new Random().nextInt(4);

        switch (numero){
            case 0:
               empresaLogin.setBackground(getDrawable(R.drawable.fondo_login_empresa0));
                break;
            case 1:
                empresaLogin.setBackground(getDrawable(R.drawable.fondo_login_empresa1));
                break;
            case 2:
                empresaLogin.setBackground(getDrawable(R.drawable.fondo_login_empresa2));
                break;
            case 3:
                empresaLogin.setBackground(getDrawable(R.drawable.fondo_login_empresa3));
                break;
            case 4:
                empresaLogin.setBackground(getDrawable(R.drawable.fondo_login_empresa4));
                break;
        }

    }


}