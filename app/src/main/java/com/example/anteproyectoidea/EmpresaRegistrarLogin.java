package com.example.anteproyectoidea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.anteproyectoidea.registro.RegistroTienda;
import com.example.anteproyectoidea.logins.LoginEmpresa;

public class EmpresaRegistrarLogin extends AppCompatActivity {

    private RelativeLayout empresaLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa_registrar_login);
        getSupportActionBar().hide();
   // empresaLogin = findViewById(R.id.latoutEmpresaLogin);
        // elegirFondo(empresaLogin);



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




}