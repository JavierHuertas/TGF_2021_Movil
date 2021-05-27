package com.example.anteproyectoidea.ui.editarperfil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.example.anteproyectoidea.dto.UserDTO;
import com.example.anteproyectoidea.logins.login;
import com.example.anteproyectoidea.utils.RecuperarDTOs;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class EditarPerfilFragment extends Fragment {

    TextInputLayout editarNombreUsuario,editarApellidoUsuario;
    ImageView editarImagenUsuario;
    Button editarContraseña;
    private static int PERMISO_CODE=100;
    private EditarPerfiViewModel editarPerfiViewModel;
    private Uri uriNewPhoto;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        editarPerfiViewModel = new ViewModelProvider(this).get(EditarPerfiViewModel.class);


        //Toast.makeText(getActivity(),"preuba usuario"+usuario.getNombre().toString(),Toast.LENGTH_LONG).show();
        View root = inflater.inflate(R.layout.fragment_editar_usuario, container, false);
        editarNombreUsuario = root.findViewById(R.id.editNombreUsuario);
        editarApellidoUsuario = root.findViewById(R.id.editApellidoUsuario);
        editarImagenUsuario = root.findViewById(R.id.imagenEditarusuario);
        editarContraseña = root.findViewById(R.id.btnChangePass);
        editarImagenUsuario.setOnClickListener(this::abrirGaleria);
        editarContraseña.setOnClickListener(this::changePasword);
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
                    uriNewPhoto = foto;
                    editarImagenUsuario.setImageURI(foto);
                }else{
                    Toast.makeText(getContext(),"no has cogido nada de la galeria",Toast.LENGTH_SHORT).show();
                }
            }
        }
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