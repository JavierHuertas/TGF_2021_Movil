package com.example.anteproyectoidea;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Registrarse_actividad extends AppCompatActivity {

    ImageView defaultUser;

    private static final int PERMISO_CODE=100;
    private static final int IMAGEN_GALLERY=101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse_actividad);
        defaultUser = findViewById(R.id.ImagenUsuarioLogin);
        defaultUser.setImageResource(R.drawable.default_users);
    }


    public void cambiarImagen(View view){

        switch (view.getId()){

            case R.id.ImagenUsuarioLogin:

                if(ActivityCompat.checkSelfPermission(Registrarse_actividad.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                    abrirGaleria();
                }else{
                    ActivityCompat.requestPermissions(Registrarse_actividad.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISO_CODE);
                }
                break;

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==IMAGEN_GALLERY){
            if(resultCode== Activity.RESULT_OK && data != null){
                Uri foto = data.getData();
                defaultUser.setImageURI(foto);
            }else{
                Toast.makeText(this,"no has oogido nada de la galeria",Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PERMISO_CODE){
            if(permissions.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                abrirGaleria();
            }else{

            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private void abrirGaleria(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGEN_GALLERY);
    }
    
}