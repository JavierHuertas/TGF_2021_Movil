package com.example.anteproyectoidea.ui.editarperfil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.anteproyectoidea.MainActivity;
import com.example.anteproyectoidea.R;
import com.example.anteproyectoidea.dialogos.ProgressBarCargando;
import com.example.anteproyectoidea.dto.UserDTO;
import com.example.anteproyectoidea.logins.login;
import com.example.anteproyectoidea.registro.Registro;
import com.example.anteproyectoidea.registro.RegistroTienda;
import com.example.anteproyectoidea.utils.RecuperarDTOs;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;

public class EditarPerfilFragment extends Fragment {

    TextInputLayout editarNombreUsuario,editarApellidoUsuario;
    ImageView editarImagenUsuario;
    Button editarContraseña,btnConfirmarCambios;
    private static int PERMISO_CODE=100;
    private  Uri uri;
    private String imagenActual;
    private static  int PERMISO_CODE_CAMERA = 101;
    private EditarPerfiViewModel editarPerfiViewModel;
    private TextView abrircamara,esGooglebro;
    private Bitmap imageBitmap;
    private FirebaseFirestore db;
    private StorageReference mReference;
    private Uri uriNewPhoto;
    private Boolean cambioImagen;
    private Boolean sacadoCamara;
    private ProgressBarCargando progressBarCargando = new ProgressBarCargando(getActivity());
    NavigationView mNavigationView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        editarPerfiViewModel = new ViewModelProvider(this).get(EditarPerfiViewModel.class);

        cambioImagen = false;
        //Toast.makeText(getActivity(),"preuba usuario"+usuario.getNombre().toString(),Toast.LENGTH_LONG).show();
        View root = inflater.inflate(R.layout.fragment_editar_usuario, container, false);
        editarNombreUsuario = root.findViewById(R.id.editNombreUsuario);
        editarApellidoUsuario = root.findViewById(R.id.editApellidoUsuario);
        editarImagenUsuario = root.findViewById(R.id.imagenEditarusuario);
        editarContraseña = root.findViewById(R.id.btnChangePass);
        sacadoCamara = true;

        mNavigationView = root.findViewById(R.id.nav_view);

        esGooglebro=root.findViewById(R.id.esGooglebro);
        editarImagenUsuario.setOnClickListener(this::abrirGaleria);
        editarContraseña.setOnClickListener(this::changePasword);
        uri = null;
        abrircamara = root.findViewById(R.id.sacarcamaraEditarPerfil);
        mReference = FirebaseStorage.getInstance().getReference();
        db = FirebaseFirestore.getInstance();
        esGooglebro.setVisibility(View.GONE);
        abrircamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, PERMISO_CODE_CAMERA);

            }
        });
        btnConfirmarCambios = root.findViewById(R.id.btnConfirmarCambios);
        btnConfirmarCambios.setOnClickListener(this::editUser);
        //editarNombreUsuario.setHint(usuario.toString());
        ponerDatos();



        editarPerfiViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }


    public void ponerDatos(){
        FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot docu) {
                 //usuario= docu.toObject(UserDTO.class);
                if(docu.exists()){
                    editarNombreUsuario.getEditText().setText(docu.getString("nombre"));
                    editarApellidoUsuario.getEditText().setText(docu.getString("apellidos"));
                    Picasso.get().load(Uri.parse(docu.getString("imagenUri"))).into(editarImagenUsuario);

                    if(docu.getString("tipo").equalsIgnoreCase("usuarioGoogle")){
                        editarContraseña.setVisibility(View.GONE);
                        btnConfirmarCambios.setVisibility(View.GONE);
                        editarImagenUsuario.setEnabled(false);
                        abrircamara.setEnabled(false);
                        editarNombreUsuario.setEnabled(false);
                        editarApellidoUsuario.setEnabled(false);
                        esGooglebro.setVisibility(View.VISIBLE);


                    }

                    //userdevolver = new UserDTO(docu.getString("key"),docu.getString("tipo"),docu.getString("nombre"),docu.getString("email"),docu.getString("imagenUri"),docu.getDouble("latitud"),docu.getDouble("longitud"));
                }else{
                    //userdevolver=null;
                }

            }
        });

    }

    private void abrirGaleria(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,PERMISO_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode ==PERMISO_CODE ){
            if(requestCode==PERMISO_CODE){
                if(resultCode== RESULT_OK && data != null){
                    Uri foto = data.getData();
                    uri = foto;
                    editarImagenUsuario.setImageURI(foto);
                    sacadoCamara=true;
                    cambioImagen = true;
                }else{
                    Toast.makeText(getContext(),"no has cogido nada de la galeria",Toast.LENGTH_SHORT).show();
                }
            }
        }

        if (requestCode == PERMISO_CODE_CAMERA && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            editarImagenUsuario.setImageBitmap(imageBitmap);
            sacadoCamara=false;
            cambioImagen = true;

        }



    }

    void editUser(View view){

        db.collection("users").document(Registro.mAuth.getUid()).update("nombre", editarNombreUsuario.getEditText().getText().toString());
        db.collection("users").document(Registro.mAuth.getUid()).update("apellidos", editarApellidoUsuario.getEditText().getText().toString());


        if(cambioImagen) {

            if (!sacadoCamara) {
                mReference = mReference.child("/Imagenusuario/" + Registro.mAuth.getUid());
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
                                imagenActual=imageReference;

                                db.collection("users").document(Registro.mAuth.getUid()).update("imagenUri", imageReference.toString());
                                //tiendaDTO.setLogoTienda("hola");
                            }
                        });
                    }
                });

            } else {

                if (uri != null) {
                    mReference = mReference.child("/Imagenusuario/" + Registro.mAuth.getUid());
                    UploadTask uploadTask = mReference.putFile(uri);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.i("fallo?", e.getMessage());
                        }
                    });
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            StorageMetadata snapshotMetadata = taskSnapshot.getMetadata();
                            Task<Uri> downloadUrl = mReference.getDownloadUrl();
                            downloadUrl.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri pe) {
                                    String imageReference = pe.toString();
                                    imagenActual=imageReference;
                                    db.collection("users").document(Registro.mAuth.getUid()).update("imagenUri", imageReference.toString());
                                    //tiendaDTO.setLogoTienda("hola");

                                }
                            });
                        }
                    });
                }








            }
        }


        db.collection("users").document(Registro.mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    String name2 =  documentSnapshot.getString("nombre");
                    String surname = documentSnapshot.getString("apellidos");
                    Uri uri2 = Uri.parse(documentSnapshot.getString("imagenUri"));

                    ((MainActivity)getActivity()).changeNavHeaderData(name2+" "+surname,uri2);
                }
            }
        });






                MaterialAlertDialogBuilder cerrarventana = new MaterialAlertDialogBuilder(getContext());
                cerrarventana.setTitle("Operacion correcta");
                cerrarventana.setMessage("Cambios realizados");
                cerrarventana.setPositiveButton(("Aceptar"),(dialog, which) -> {
                    //Toast.makeText(getContext(),"operacion cancelada",Toast.LENGTH_LONG).show();

                });
                cerrarventana.show();







    }


    public void subirFotoGaleria() {
        if (uri != null) {
            mReference = mReference.child("/Imagenusuario/" + Registro.mAuth.getUid());
            UploadTask uploadTask = mReference.putFile(uri);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.i("fallo?", e.getMessage());
                }
            });
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    StorageMetadata snapshotMetadata = taskSnapshot.getMetadata();
                    Task<Uri> downloadUrl = mReference.getDownloadUrl();
                    downloadUrl.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri pe) {
                            String imageReference = pe.toString();
                            db.collection("users").document(Registro.mAuth.getUid()).update("imagenUri", imageReference.toString());
                            //tiendaDTO.setLogoTienda("hola");

                        }
                    });
                }
            });
        }
    }

    public void subirFotoCamra(){
        mReference = mReference.child("/Imagenusuario/" + Registro.mAuth.getUid());
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
                        db.collection("users").document(Registro.mAuth.getUid()).update("imagenUri", imageReference.toString());
                        //tiendaDTO.setLogoTienda("hola");
                    }
                });
            }
        });
    }


    void changePasword(View view){
    MaterialAlertDialogBuilder alerta = new MaterialAlertDialogBuilder(getContext());
        alerta.setTitle("Cambiar contraseña");
        alerta.setMessage("Se enviara un correo a \n"+FirebaseAuth.getInstance().getCurrentUser().getEmail()+"\n La sesion actual se cerrara deseas cotinuar");
        alerta.setPositiveButton("Confirmar",(dialog, which) -> {
            //Toast.makeText(getContext(),FirebaseAuth.getInstance().getCurrentUser().getEmail()+"",Toast.LENGTH_LONG).show();
            FirebaseAuth.getInstance().sendPasswordResetEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        FirebaseAuth.getInstance().signOut();
                        Intent login = new Intent(getContext(),login.class);
                        MaterialAlertDialogBuilder cerrarventana = new MaterialAlertDialogBuilder(getContext());
                        cerrarventana.setTitle("Operacion correcta");
                        cerrarventana.setMessage("esta actividad se cerrara");
                        cerrarventana.setPositiveButton(("Aceptar"),(dialog, which) -> {
                            //Toast.makeText(getContext(),"operacion cancelada",Toast.LENGTH_LONG).show();
                            startActivity(login);
                            try {
                                finalize();
                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        });
                        cerrarventana.show();
                    }
                }
            });
        });
        alerta.setNegativeButton(("Cancelar"),(dialog, which) -> {
            Toast.makeText(getContext(),"operacion cancelada",Toast.LENGTH_LONG).show();

        });
        alerta.show();

    }




}