package com.example.anteproyectoidea.registro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anteproyectoidea.dialogos.ProgressBarCargando;
import com.example.anteproyectoidea.R;
import com.example.anteproyectoidea.dto.UserDTO;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class RegistrarseUsuarios extends AppCompatActivity {

    private ImageView defaultUser;
    private FirebaseAuth mAuth;
    private TextInputLayout email, nombre, apellido,contraseñaUno, contraseñaDos;
    private double latitud, longitud;
    private static final int PERMISO_CODE = 100;
    private static final int IMAGEN_GALLERY = 101;
    private static  int PERMISO_CODE_CAMERA = 2;
    private FirebaseFirestore db;
    private Uri uri;
    private String defaultImagen ="https://firebasestorage.googleapis.com/v0/b/jardinerias-paca.appspot.com/o/Imagenusuario%2Fdefault_users.png?alt=media&token=a3487ccd-64f3-4872-8c0d-6335cbbf8b64";
    private StorageReference mReference;
    private FusedLocationProviderClient client;
    private UserDTO userDTO;
    private Bitmap imageBitmap;
    private TextView abrirCamara;
    private LocationManager locManager;
    private LocationListener locationListenerGPS;
    private Boolean comprobarCamara;
    private Context contexto;
    //MaterialAlertDialogBuilder correcto;
    private ProgressBarCargando progressBarCargando = new ProgressBarCargando(RegistrarseUsuarios.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_registrarse_usuarios);
        contexto = this;
        defaultUser = findViewById(R.id.ImagenUsuarioLogin);
        mReference = FirebaseStorage.getInstance().getReference();
        abrirCamara = findViewById(R.id.abrircamara);
        comprobarCamara = true;
        abrirCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, PERMISO_CODE_CAMERA);
            }
        });
       /*correcto = new MaterialAlertDialogBuilder(getBaseContext());
        correcto.setMessage("Cuenta creada exitosamente");
        correcto.setPositiveButton(("Aceptar"),(dialog, which) -> {
            //Toast.makeText(getContext(),"operacion cancelada",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), Registro.class);
            startActivity(intent);
        });*/

        mReference.child("/Imagenusuario/default_users.png").getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                Picasso.get().load(task.getResult()).into(defaultUser);
            }
        });

        apellido = findViewById(R.id.editTextTextPersonSurname);
        email = findViewById(R.id.editTextTextEmailAddress);
        nombre = findViewById(R.id.editTextTextPersonName);
        contraseñaUno = findViewById(R.id.editTextTextPassword);
        contraseñaDos = findViewById(R.id.editTextTextPassword2);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            //return error  = "SIN PERMISOS";
        }else {
                //con permisos
            locationListenerGPS = new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    latitud = location.getLatitude();
                    longitud = location.getLongitude();
                    //Toast.makeText(getApplicationContext(),longitud+" "+ latitud,Toast.LENGTH_SHORT).show();
                }


            };
            locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1500,10,locationListenerGPS);


        }


    }


    public void cambiarImagen(View view) {

        switch (view.getId()) {

            case R.id.ImagenUsuarioLogin:

                if (ActivityCompat.checkSelfPermission(RegistrarseUsuarios.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    abrirGaleria();
                } else {
                    ActivityCompat.requestPermissions(RegistrarseUsuarios.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISO_CODE);
                }
                break;
            case R.id.buttonRegistrarse:

                if (comprobarCosasIntroducidas() == 0) {

                    String correo = email.getEditText().getText().toString().trim();
                    String contrasenia = contraseñaUno.getEditText().getText().toString();

                    Registro.mAuth.createUserWithEmailAndPassword(correo, contrasenia)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        userDTO = new UserDTO(Registro.mAuth.getUid(),"usuario", nombre.getEditText().getText().toString().trim(), apellido.getEditText().getText().toString().trim(),email.getEditText().getText().toString().trim(), defaultImagen, latitud, longitud);
                                        //Toast.makeText(getApplicationContext(),apellido.getEditText().getText().toString().trim(),Toast.LENGTH_SHORT).show();
                                        if(comprobarCamara) {
                                            if (uri != null) {
                                                mReference = mReference.child("Imagenusuario/" + uri.getLastPathSegment() + "User" + Registro.mAuth.getUid());
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
                                                                db.collection("users").document(Registro.mAuth.getUid()).update("imagenUri", imageReference);
                                                                userDTO.setImagenUri(imageReference);
                                                                locManager.removeUpdates(locationListenerGPS);
                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                        }else{

                                            mReference = mReference.child("Imagenusuario/" + "User" + Registro.mAuth.getUid());
                                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                            byte[] datas = baos.toByteArray();

                                            mReference.putBytes(datas).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    mReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                            String imageReference = uri.toString();
                                                            db.collection("users").document(Registro.mAuth.getUid()).update("imagenUri", imageReference);
                                                            //tiendaDTO.setLogoTienda("hola");
                                                        }
                                                    });
                                                }
                                            });




                                        }

                                        progressBarCargando.StarProgressBar();
                                        db.collection("users").document(Registro.mAuth.getUid()).set(userDTO);
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {

                                                FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        MaterialAlertDialogBuilder cerrarventana = new MaterialAlertDialogBuilder(contexto);
                                                        cerrarventana.setTitle("Operacion correcta");
                                                        cerrarventana.setIcon(R.drawable.ic_ok);
                                                        cerrarventana.setMessage("Se ha envidado un correo de verificacion a tu cuenta");
                                                        cerrarventana.setPositiveButton(("Aceptar"),(dialog, which) -> {
                                                            //Toast.makeText(getContext(),"operacion cancelada",Toast.LENGTH_LONG).show();
                                                            irNuevaActividad();
                                                            try {
                                                                finalize();
                                                            } catch (Throwable throwable) {
                                                                throwable.printStackTrace();
                                                            }
                                                        });
                                                        cerrarventana.show();;
                                                    }
                                                });




                                                progressBarCargando.finishProgressBar();

                                            }
                                        }, 3000);


                                      //  correcto.show();

                                    } else {
                                        // If sign in fails, display a message to the user.

                                        task.getException().getMessage();
                                        Toast.makeText(getApplicationContext(), "Authentication failed." + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                break;
        }
    }

    private void irNuevaActividad() {
        Intent intent = new Intent(getApplicationContext(), Registro.class);
        startActivity(intent);
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

        if (requestCode == PERMISO_CODE_CAMERA && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            defaultUser.setImageBitmap(imageBitmap);
            comprobarCamara=false;

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

        nombre.setErrorEnabled(false);
        //direcion.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        contraseñaUno.setErrorEnabled(false);
        apellido.setErrorEnabled(false);
        email.setErrorEnabled(false);
        contraseñaDos.setErrorEnabled(false);
        contraseñaDos.setErrorEnabled(false);
        int comprobador =0;
        if(apellido.getEditText().getText().toString().trim().isEmpty()){
            //Toast.makeText(getApplicationContext(),"No has introdfucido ningun nombre",Toast.LENGTH_SHORT).show();
            apellido.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            apellido.setError("No has introdfucido ningun apellido");
            comprobador =1;
        }
        if(nombre.getEditText().getText().toString().trim().isEmpty()){
            //Toast.makeText(getApplicationContext(),"No has introdfucido ningun nombre",Toast.LENGTH_SHORT).show();
            nombre.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            nombre.setError("No has introdfucido ningun nombre");
            comprobador =1;
        }

        /*if(direcion.getText().toString().isEmpty()){
            //Toast.makeText(getApplicationContext(),"No has introdfucido ningun nombre",Toast.LENGTH_SHORT).show();
            direcion.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            direcion.setError("No has entroducido ninguna direcion");
            comprobador =1;
        }*/
        if(email.getEditText().getText().toString().trim().isEmpty()){
            //Toast.makeText(getApplicationContext(),"No has introducido ningun email",Toast.LENGTH_SHORT).show();
            email.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            email.setError("no has intricucido niungun email");
            comprobador =1;
        }
        if(!isEmailValid(email.getEditText().getText().toString().trim())){
            email.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            email.setError("Email no valido");
            comprobador =1;
        }
        if(contraseñaUno.getEditText().getText().toString().trim().isEmpty() ){
            contraseñaUno.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            //Toast.makeText(getApplicationContext(),"No has introducido ninguna Contraseña o es demasiado pequeña",Toast.LENGTH_SHORT).show();
            comprobador =1;
            contraseñaUno.setError("No has introducido ninguna Contraseña");
        }
        if(!(contraseñaUno.getEditText().getText().toString().trim().length()>6)){
            contraseñaUno.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            //Toast.makeText(getApplicationContext(),"No has introducido ninguna Contraseña o es demasiado pequeña",Toast.LENGTH_SHORT).show();
            contraseñaUno.setError("La contraseña es demasiado pequeña minimo 6 caracteres");
            comprobador =1;
        }
        if(contraseñaDos.getEditText().getText().toString().trim().isEmpty()){
            contraseñaDos.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            //Toast.makeText(getApplicationContext(),"No has introducido ninguna Contraseña",Toast.LENGTH_SHORT).show();
            contraseñaDos.setError("No has introducido ninguna Contraseña");
            comprobador =1;
        }
        if(!contraseñaDos.getEditText().getText().toString().trim().equals(contraseñaUno.getEditText().getText().toString().trim())){
            contraseñaDos.getEditText().setText("");
            contraseñaDos.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            //Toast.makeText(getApplicationContext(),"La contraseña no coincide",Toast.LENGTH_SHORT).show();
            contraseñaDos.setError("La contraseña no coincide");
            comprobador =1;

        }
        return  comprobador;
    }
}