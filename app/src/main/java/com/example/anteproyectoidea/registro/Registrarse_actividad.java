package com.example.anteproyectoidea.registro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.anteproyectoidea.R;
import com.example.anteproyectoidea.dto.UserDTO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Registrarse_actividad extends AppCompatActivity {

    private ImageView defaultUser;
    private FirebaseAuth mAuth;
    private EditText email,nombre,contraseñaUno,contraseñaDos,direcion;

    private static final int PERMISO_CODE=100;
    private static final int IMAGEN_GALLERY=101;
    private FirebaseFirestore db;
    private  Uri uri;
    private Uri defaultImage;
    private StorageReference mReference;

    private UserDTO userDTO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_registrarse_actividad);
        defaultUser = findViewById(R.id.ImagenUsuarioLogin);
        mReference = FirebaseStorage.getInstance().getReference();

        mReference.child("/Imagenusuario/default_users.png").getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                Picasso.get().load(task.getResult()).into(defaultUser);
            }
        });




        email = findViewById(R.id.editTextTextEmailAddress);
        nombre = findViewById(R.id.editTextTextPersonName);
        contraseñaUno = findViewById(R.id.editTextTextPassword);
        contraseñaDos = findViewById(R.id.editTextTextPassword2);
        direcion = findViewById(R.id.editTextDireccion);






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
            case R.id.buttonRegistrarse:

                if(comprobarCosasIntroducidas()==0){

                    String correo= email.getText().toString();
                    String contrasenia = contraseñaUno.getText().toString();

                    Registro.mAuth.createUserWithEmailAndPassword(correo, contrasenia )
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        userDTO = new UserDTO(Registro.mAuth.getUid(),nombre.getText().toString(),email.getText().toString(),direcion.getText().toString()," ");


                                        if(uri != null){

                                            mReference = mReference.child("Imagenusuario/"+uri.getLastPathSegment()+"User"+Registro.mAuth.getUid());

                                            UploadTask uploadTask = mReference.putFile(uri);

                                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {

                                                }
                                            });

                                            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    StorageMetadata snapshotMetadata = taskSnapshot.getMetadata();
                                                    Task<Uri> downloadUrl = mReference.getDownloadUrl();

                                                    downloadUrl.addOnSuccessListener(new OnSuccessListener<Uri>() {

                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                            String imageReference = uri.toString();
                                                            db.collection("users").document(Registro.mAuth.getUid()).update("imagenUri",imageReference);
                                                            userDTO.setImagenUri(imageReference);
                                                        }
                                                    });
                                                }
                                            });
                                        }




                                        db.collection("users").document(Registro.mAuth.getUid()).set(userDTO);



                                    } else {
                                        // If sign in fails, display a message to the user.

                                        task.getException().getMessage();
                                        Toast.makeText(getApplicationContext(), "Authentication failed."+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }




                break;

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==IMAGEN_GALLERY){
            if(resultCode== Activity.RESULT_OK && data != null){
                Uri foto = data.getData();
                uri = foto;
                defaultUser.setImageURI(foto);
            }else{
                Toast.makeText(this,"no has cogido nada de la galeria",Toast.LENGTH_SHORT).show();
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


    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private int comprobarCosasIntroducidas(){

        nombre.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        direcion.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        contraseñaUno.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        email.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        contraseñaDos.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        contraseñaDos.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        int comprobador =0;
        if(nombre.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"No has introdfucido ningun nombre",Toast.LENGTH_SHORT).show();
            nombre.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            comprobador =1;
        }

        if(direcion.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"No has introdfucido ningun nombre",Toast.LENGTH_SHORT).show();
            direcion.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            comprobador =1;
        }
        if(email.getText().toString().isEmpty()||!isEmailValid(email.getText().toString())){
            Toast.makeText(getApplicationContext(),"No has introducido ningun email",Toast.LENGTH_SHORT).show();
            email.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            comprobador =1;
        }
        if(contraseñaUno.getText().toString().isEmpty() || !(contraseñaUno.getText().toString().length()>6)){
            contraseñaUno.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            Toast.makeText(getApplicationContext(),"No has introducido ninguna Contraseña o es demasiado pequeña",Toast.LENGTH_SHORT).show();
            comprobador =1;
        }
        if(contraseñaDos.getText().toString().isEmpty()){
            contraseñaDos.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            Toast.makeText(getApplicationContext(),"No has introducido ninguna Contraseña",Toast.LENGTH_SHORT).show();
            comprobador =1;
        }
        if(!contraseñaDos.getText().toString().equals(contraseñaUno.getText().toString())){
            contraseñaDos.setText("");
            contraseñaDos.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            Toast.makeText(getApplicationContext(),"La contraseña no coincide",Toast.LENGTH_SHORT).show();
            comprobador =1;

        }
        return  comprobador;
    }
}