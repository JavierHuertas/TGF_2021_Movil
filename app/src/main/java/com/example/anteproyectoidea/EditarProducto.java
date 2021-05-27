package com.example.anteproyectoidea;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anteproyectoidea.dto.ProductoDTO;
import com.example.anteproyectoidea.registro.Registro;
import com.example.anteproyectoidea.registro.RegistroTienda;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class EditarProducto extends AppCompatActivity {


    private Boolean editar;
    private String urlImagen;
    private Uri foto;
    Bitmap imageBitmap;
    private StorageReference mReference;
    private static final int PERMISO_CODE = 100;
    private static final int PERMISO_CODE_CAMERA = 101;
    private TextInputLayout nombre, descripcion;
    private ImageView imagen;
    TextView openCamara;
    private NumberPicker cantidad;
    private TextInputLayout precio;
    private CheckBox mosntrar;
    private ProductoDTO productoEditar,productoNuevo;
    private RelativeLayout btnConfirmar, btnCancelar;
    private Retrofit retrofit;
    private Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_producto);
        getSupportActionBar().hide();
        urlImagen=null;
        mReference =FirebaseStorage.getInstance().getReference();
        btnConfirmar = findViewById(R.id.btnAceptar);
        btnCancelar = findViewById(R.id.btnCancelar);
        imagen = findViewById(R.id.editarimagenProdcuto);
        btnConfirmar.setOnClickListener(this::btnAceptar);
        btnCancelar.setOnClickListener(this::btnCancelar);
        imagen.setOnClickListener(this::clickImagen);
        btnCancelar = findViewById(R.id.btnCancelar);
        nombre = findViewById(R.id.editarnombreProducto);
        descripcion = findViewById(R.id.editardescripcionProducto);
        precio = findViewById(R.id.editarprecioProducto);
        mosntrar = findViewById(R.id.editarVisibleProducto);
        cantidad = findViewById(R.id.editarcantidaTienda);
        openCamara = findViewById(R.id.openCamera);
        cantidad.setMinValue(1);
        context = this;
        cantidad.setMaxValue(9999);

        openCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    startActivityForResult(takePictureIntent, PERMISO_CODE_CAMERA);

            }
        });

        Bundle bundle = getIntent().getExtras();
        editar =  bundle.getBoolean("esEditar");

        retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.conexionAPI))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        if(editar){
            //editar Producto
            productoEditar = (ProductoDTO) bundle.get("editar");
            meterValores();
        }else{
            //crear Producto

        }


    }

    private void meterValores() {
        precio.getEditText().setText(String.valueOf(productoEditar.getPrecio()));
        nombre.getEditText().setText(productoEditar.getNombre());
        descripcion.getEditText().setText(productoEditar.getDescripcion());
        Picasso.get().load(productoEditar.getUrlImagen()).into(imagen);
        cantidad.setValue(productoEditar.getCantidad());
        if (productoEditar.getMostrarApp()) {
            mosntrar.setChecked(true);
        } else {
            mosntrar.setChecked(false);
        }
    }

    public void btnAceptar(View v) {
        BokyTakeAPI bokyTakeAPI = retrofit.create(BokyTakeAPI.class);
        if(editar){
        productoEditar.setPrecio(Double.parseDouble(precio.getEditText().getText().toString()));
        productoEditar.setCantidad(cantidad.getValue());
        productoEditar.setDescripcion(descripcion.getEditText().getText().toString());
        productoEditar.setNombre(nombre.getEditText().getText().toString());
        productoEditar.setMostrarApp(mosntrar.isChecked());
        Call<Map<String, Object>> llamada = bokyTakeAPI.editarProducto(productoEditar);
        llamada.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> calll, Response<Map<String, Object>> response) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Editar Producto");
                builder.setMessage(response.body().get("message").toString());
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                //Toast.makeText(getApplicationContext(), "fallitook", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Ha habido un error de servidor", Toast.LENGTH_SHORT).show();
            }
        });
        }else{
            //String nombre, double precio, int cantidad, Boolean mostrarApp, String descripcion, String urlImg
            productoNuevo = new ProductoDTO(nombre.getEditText().getText().toString(),Double.parseDouble( precio.getEditText().getText().toString()),cantidad.getValue(),mosntrar.isChecked(),descripcion.getEditText().getText().toString(), (urlImagen==null)?"https://firebasestorage.googleapis.com/v0/b/jardinerias-paca.appspot.com/o/imagenProdcutos%2Fabrir-caja.png?alt=media&token=ec96957b-efb0-4097-ac32-fd5056db1d13":urlImagen);

            Call<Map<String, Object>> llamada = bokyTakeAPI.nuevoProducto(productoNuevo,FirebaseAuth.getInstance().getUid());

            llamada.enqueue(new Callback<Map<String, Object>>() {
                @Override
                public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Nuevo Producto");
                    builder.setMessage("Producto creado");
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onBackPressed();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

                @Override
                public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                    Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    public void btnCancelar(View view) {
        onBackPressed();
    }

    public void clickImagen(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            abrirGaleria();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISO_CODE);
        }
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PERMISO_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PERMISO_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                foto = data.getData();
                //urlImagen = foto;
                //Toast.makeText(this,data.getData().toString() +" pqwer", Toast.LENGTH_SHORT).show();
                imagen.setImageURI(foto);
                subirImagen(true);
            } else {
                Toast.makeText(this, "no has elegido nada de la galeria", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == PERMISO_CODE_CAMERA && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imagen.setImageBitmap(imageBitmap);
            subirImagen(false);

        }
    }

    public  String subirImagen(Boolean camara) {
        if(camara) {
            if (foto != null) {
                Date fecha = new Date();
                mReference = mReference.child("/imagenProdcutos/" + "Tienda" + Registro.mAuth.getUid() + "/Producto" + "-" + fecha.getTime());
                mReference.putFile(foto).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        mReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                final Uri downloadUrl = uri;
                                urlImagen = downloadUrl.toString();
                                //Toast.makeText(context, "supuestaURL "+urlImagen , Toast.LENGTH_SHORT).show();
                                if (editar) {
                                    productoEditar.setUrlImagen(urlImagen);
                                }
                            }
                        });
                    }
                });
            }
            return urlImagen;
        }else{
            String timeStamp = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
            mReference = mReference.child("/imagenProdcutos/" + "Tienda" + Registro.mAuth.getUid() + "/Producto" + "-" + timeStamp);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] datas = baos.toByteArray();

            mReference.putBytes(datas).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            final Uri downloadUrl = uri;
                            urlImagen = downloadUrl.toString();
                            //Toast.makeText(context, "supuestaURL "+urlImagen , Toast.LENGTH_SHORT).show();
                            if (editar) {
                                productoEditar.setUrlImagen(urlImagen);
                            }
                        }
                    });
                }
            });

            return urlImagen;
        }
    }




}




