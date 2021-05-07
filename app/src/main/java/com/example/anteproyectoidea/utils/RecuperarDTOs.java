package com.example.anteproyectoidea.utils;


import android.net.Uri;
import android.widget.Toast;

import com.example.anteproyectoidea.dto.UserDTO;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class RecuperarDTOs {

   private FirebaseFirestore db = FirebaseFirestore.getInstance();
    UserDTO userdevolver = null;
    public RecuperarDTOs() {

        db = FirebaseFirestore.getInstance();

    }

    public UserDTO recuperarUsuarioDTO(String key){

        db.collection("users").document(key).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot docu) {
                if(docu.exists()){

                    userdevolver = new UserDTO(docu.getString("key"),docu.getString("tipo"),docu.getString("nombre"),docu.getString("email"),docu.getString("imagenUri"),docu.getDouble("latitud"),docu.getDouble("longitud"));
                }else{
                    userdevolver=null;
                }
            }
        });

        return userdevolver;
    }
}
