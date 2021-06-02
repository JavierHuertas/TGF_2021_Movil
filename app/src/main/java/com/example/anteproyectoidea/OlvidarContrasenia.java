package com.example.anteproyectoidea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.anteproyectoidea.logins.login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class OlvidarContrasenia extends AppCompatActivity {

    private TextInputLayout email;
    private RelativeLayout btnConfirmar;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvidar_contrasenia);
        email = findViewById(R.id.changePasswordEmail);
        btnConfirmar = findViewById(R.id.btnCambiarContraseña);
        context = this;
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!email.getEditText().getText().toString().isEmpty()){

                        MaterialAlertDialogBuilder alerta = new MaterialAlertDialogBuilder(context);
                        alerta.setTitle("Cambiar contraseña");
                        alerta.setMessage("Se enviara un correo a \n"+email.getEditText().getText().toString() +"\n volveras a la pagina de inicio");
                        alerta.setPositiveButton("Confirmar",(dialog, which) -> {
                            //Toast.makeText(getContext(),FirebaseAuth.getInstance().getCurrentUser().getEmail()+"",Toast.LENGTH_LONG).show();
                            FirebaseAuth.getInstance().sendPasswordResetEmail(email.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        FirebaseAuth.getInstance().signOut();
                                        Intent login = new Intent(getApplicationContext(), com.example.anteproyectoidea.logins.login.class);
                                        MaterialAlertDialogBuilder cerrarventana = new MaterialAlertDialogBuilder(context);
                                        cerrarventana.setTitle("Operacion correcta");
                                        cerrarventana.setMessage("Se ha enviado un correo a \n"+email.getEditText().getText().toString()+"\n\n esta actividad se cerrara");
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
                            Toast.makeText(context,"operacion cancelada",Toast.LENGTH_LONG).show();

                        });
                        alerta.show();

                    }
                }

        });


    }
}