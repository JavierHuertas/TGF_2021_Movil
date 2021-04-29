package com.example.anteproyectoidea;

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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.anteproyectoidea.dto.TiendaDTO;
import com.example.anteproyectoidea.dto.UserDTO;
import com.example.anteproyectoidea.logins.LoginEmpresa;
import com.example.anteproyectoidea.registro.Registrarse_actividad;
import com.example.anteproyectoidea.registro.Registro;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

public class RegistroTienda extends AppCompatActivity {


    private EditText direccionTienda;
    private RelativeLayout findAddres,btnRegistrarse;
    private StorageReference mReference;
    private  Uri uri;
    private FirebaseFirestore db;
    private EditText nombreDuenio, nombreEstabelecimiento,contraseñaUno,contraseñaDos, direccion ,email;
    private ImageView LogoTienda;
    private final  int PERMISO_CODE = 150;
    private TiendaDTO tiendaDTO;
    private final String ImagenDefault = "https://firebasestorage.googleapis.com/v0/b/jardinerias-paca.appspot.com/o/imagenTiendas%2Ftienda.png?alt=media&token=95b7b55a-b968-4ae9-844b-bf0f04a6566d";
    private LatLng latLng;
    private static int AUTOCOMPLETE_REQUEST_CODE = 1;
    private double longitudTienda;
    private double latitudTienda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_tienda);
        mReference = FirebaseStorage.getInstance().getReference();
        db = FirebaseFirestore.getInstance();
        String key = getString(R.string.google_api_key) ;


        nombreDuenio = findViewById(R.id.editTextNombreDuenio);
        nombreEstabelecimiento = findViewById(R.id.editTextNombreNegocio);
        contraseñaUno = findViewById(R.id.editTextTextPasswordTienda);
        contraseñaDos = findViewById(R.id.editTextTextPassword2Tienda);
        direccionTienda = findViewById(R.id.editTextDireccionTienda);
        email = findViewById(R.id.editTextTextEmailAddressTienda);
        btnRegistrarse = findViewById(R.id.buttonRegistrarseTienda);
        Places.initialize(getApplicationContext(),key);
        PlacesClient placesClient = Places.createClient(this);

        findAddres = findViewById(R.id.intetnDirecion);

        findAddres.setOnClickListener(this::hacerClick);
        btnRegistrarse.setOnClickListener(this::registrarse);
        direccionTienda.setOnClickListener(this::hacerClick);
        LogoTienda = findViewById(R.id.imagenLogoTienda);
        LogoTienda.setOnClickListener(this::cambiarImagen);
        ponerImagnetiendaDefault();



    }

    private void ponerImagnetiendaDefault() {
        mReference.child("/imagenTiendas/tienda.png").getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                Picasso.get().load(task.getResult()).into(LogoTienda);
            }
        });
    }


    void hacerClick(View view){
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.ADDRESS,Place.Field.LAT_LNG);

        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fields).build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

    void cambiarImagen(View view){
        if (ActivityCompat.checkSelfPermission(RegistroTienda.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            abrirGaleria();
        } else {
            ActivityCompat.requestPermissions(RegistroTienda.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISO_CODE);
        }
    }

    void registrarse(View view){

        String correo = email.getText().toString();
        String contrasenia = contraseñaUno.getText().toString();

        Registro.mAuth.createUserWithEmailAndPassword(correo, contrasenia)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //public TiendaDTO(String key, String nombreDueño, String email, String logoTienda, String nombreComercio, String localizacion,double longitud, double latitud) {
                           // userDTO = new UserDTO(Registro.mAuth.getUid(), nombre.getText().toString(), email.getText().toString(), " ", latitud, longitud);
                            longitudTienda = latLng.longitude;
                            latitudTienda = latLng.latitude;
                            Toast.makeText(getApplicationContext(),longitudTienda+" "+latitudTienda,Toast.LENGTH_SHORT).show();

                            tiendaDTO = new TiendaDTO(Registro.mAuth.getUid(),nombreDuenio.getText().toString(),email.getText().toString(),ImagenDefault,nombreEstabelecimiento.getText().toString(),direccionTienda.getText().toString(),longitudTienda,latitudTienda );
                            if (uri != null) {
                                mReference = mReference.child("imagenTiendas/" + uri.getLastPathSegment() + "Tienda" + Registro.mAuth.getUid());
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
                                                tiendaDTO.setLogoTienda(imageReference);

                                            }
                                        });
                                    }
                                });
                            }
                            db.collection("shops").document(Registro.mAuth.getUid()).set(tiendaDTO);
                            Registro.mAuth.signOut();
                            Intent intent = new Intent(getApplicationContext(), LoginEmpresa.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.

                            task.getException().getMessage();
                            Toast.makeText(getApplicationContext(), "Authentication failed." + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    private void abrirGaleria() {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent,PERMISO_CODE);
        }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                latLng =  place.getLatLng();

                direccionTienda.setText(place.getAddress());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
            return;
        }
        if(requestCode==PERMISO_CODE){
            if(resultCode== Activity.RESULT_OK && data != null){
                Uri foto = data.getData();
                uri = foto;
                LogoTienda.setImageURI(foto);
            }else{
                Toast.makeText(this,"no has cogido nada de la galeria",Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private int comprobarCosasIntroducidas(){

        nombreEstabelecimiento.getBackground().setColorFilter(getResources().getColor(R.color.logo) , PorterDuff.Mode.SRC_ATOP);
        nombreDuenio.getBackground().setColorFilter(getResources().getColor(R.color.logo) , PorterDuff.Mode.SRC_ATOP);
        direccion.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        contraseñaUno.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        email.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        contraseñaDos.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        contraseñaDos.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        int comprobador =0;
        if(nombreDuenio.getText().toString().isEmpty()){
            //Toast.makeText(getApplicationContext(),"No has introdfucido ningun nombre",Toast.LENGTH_SHORT).show();
            nombreDuenio.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            nombreDuenio.setError("No has introdfucido ningun nombre ");
            comprobador =1;
        }
        if(nombreEstabelecimiento.getText().toString().isEmpty()){
            //Toast.makeText(getApplicationContext(),"No has introdfucido ningun nombre",Toast.LENGTH_SHORT).show();
            nombreEstabelecimiento.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            nombreEstabelecimiento.setError("No has introdfucido ningun nombre al establecimiento");
            comprobador =1;
        }

        if(direccionTienda.getText().toString().isEmpty()){
            //Toast.makeText(getApplicationContext(),"No has introdfucido ningun nombre",Toast.LENGTH_SHORT).show();
            direccionTienda.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            direccionTienda.setError("No has entroducido ninguna direcion");
            comprobador =1;
        }
        if(email.getText().toString().isEmpty()){
            //Toast.makeText(getApplicationContext(),"No has introducido ningun email",Toast.LENGTH_SHORT).show();
            email.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            email.setError("no has intricucido niungun email");
            comprobador =1;
        }
        if(!isEmailValid(email.getText().toString())){
            email.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            email.setError("Email no valido");
            comprobador =1;
        }
        if(contraseñaUno.getText().toString().isEmpty() ){
            contraseñaUno.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            //Toast.makeText(getApplicationContext(),"No has introducido ninguna Contraseña o es demasiado pequeña",Toast.LENGTH_SHORT).show();
            comprobador =1;
            contraseñaUno.setError("No has introducido ninguna Contraseña");
        }
        if(!(contraseñaUno.getText().toString().length()>6)){
            contraseñaUno.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            //Toast.makeText(getApplicationContext(),"No has introducido ninguna Contraseña o es demasiado pequeña",Toast.LENGTH_SHORT).show();
            contraseñaUno.setError("La contraseña es demasiado pequeña minimo 6 caracteres");
            comprobador =1;
        }
        if(contraseñaDos.getText().toString().isEmpty()){
            contraseñaDos.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            //Toast.makeText(getApplicationContext(),"No has introducido ninguna Contraseña",Toast.LENGTH_SHORT).show();
            contraseñaDos.setError("No has introducido ninguna Contraseña");
            comprobador =1;
        }
        if(!contraseñaDos.getText().toString().equals(contraseñaUno.getText().toString())){
            contraseñaDos.setText("");
            contraseñaDos.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            //Toast.makeText(getApplicationContext(),"La contraseña no coincide",Toast.LENGTH_SHORT).show();
            contraseñaDos.setError("La contraseña no coincide");
            comprobador =1;

        }
        return  comprobador;
    }


}