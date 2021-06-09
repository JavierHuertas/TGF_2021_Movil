package com.example.anteproyectoidea.logins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.anteproyectoidea.MainActivity;
import com.example.anteproyectoidea.MainTienda;
import com.example.anteproyectoidea.OlvidarContrasenia;
import com.example.anteproyectoidea.dialogos.ProgressBarCargando;
import com.example.anteproyectoidea.R;
import com.example.anteproyectoidea.registro.Registro;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class login extends AppCompatActivity {

    private FirebaseFirestore db;
    private TextInputLayout testEmail,testContra;
    boolean EsTienda ;
    private TextView changePassword;
    private FirebaseUser user;
    private String tipo;
    private LottieAnimationView animation;
    private ProgressBarCargando progressBarCargando = new ProgressBarCargando(login.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        testEmail = findViewById(R.id.emailLogin);
        testContra = findViewById(R.id.passwordlogin);
        changePassword = findViewById(R.id.changePassword);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nuevo = new Intent(getApplicationContext(), OlvidarContrasenia.class);
                startActivity(nuevo);
            }
        });
        db = FirebaseFirestore.getInstance();

        animation = findViewById(R.id.loginAnimation);



    }





    private void goodAnimation(Boolean tienda){
        animation.setAnimation(R.raw.spinner_only);
        animation.playAnimation();
        animation.setAnimation(R.raw.spinner_good);
        animation.playAnimation();
        animation.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                if(tienda){
                    goHomeShop();
                }else {
                    goHomeUser();
                }


            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }


    public void login(View view) {

        if(Registro.mAuth.getCurrentUser() !=null) {
            Registro.mAuth.signOut();
        }

        if(comprobarCosasIntroducidas()==0) {

            if (isEmailValid(testEmail.getEditText().getText().toString().trim())) {
                Registro.mAuth.signInWithEmailAndPassword(testEmail.getEditText().getText().toString().trim(), testContra.getEditText().getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()) {
                            String uid = Registro.mAuth.getCurrentUser().getUid();
                            user = Registro.mAuth.getCurrentUser();
                            //user.isEmailVerified()
                            if (user.isEmailVerified()) {
                                db.collection("shops").whereEqualTo("key", uid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.getResult().isEmpty()) {
                                            goodAnimation(false);
                                        } else {
                                            goodAnimation(true);
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(getApplicationContext(), "Tu email no se ha verificado comprueba tu correo", Toast.LENGTH_LONG).show();
                            }
                        } else {

                            errorAnimation(task.getException().getMessage());
                        }
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Tu email no es valido", Toast.LENGTH_LONG).show();

            }

        }
    }



    public void goHomeUser(){
        progressBarCargando.StarProgressBar();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBarCargando.finishProgressBar();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("esGoogle",false);
                progressBarCargando.finishProgressBar();
                startActivity(intent);
               finish();
            }
        }, 2000);



    }
    public void goHomeShop(){

        progressBarCargando.StarProgressBar();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBarCargando.finishProgressBar();
                Intent intent = new Intent(getApplicationContext(), MainTienda.class);
                progressBarCargando.finishProgressBar();
                startActivity(intent);
                finish();
            }
        }, 2000);



        //progressBarCargando.finishProgressBar();
        Toast.makeText(getApplicationContext(),"es tienda",Toast.LENGTH_SHORT).show();

    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void errorAnimation( String excepcion){
        animation.setAnimation(R.raw.spinner_only);
        animation.playAnimation();
        animation.setAnimation(R.raw.spinner_fail);
        animation.playAnimation();
        animation.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                Toast.makeText(getApplicationContext(),excepcion,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private int comprobarCosasIntroducidas(){

        testEmail.setErrorEnabled(false);
        //direcion.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        testContra.setErrorEnabled(false);
        int comprobador =0;
        if(testEmail.getEditText().getText().toString().trim().isEmpty()){
            //Toast.makeText(getApplicationContext(),"No has introducido ningun email",Toast.LENGTH_SHORT).show();
            testEmail.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            testEmail.setError("no has intricucido niungun email");
            comprobador =1;
        }
        if(!isEmailValid(testEmail.getEditText().getText().toString().trim())){
            testEmail.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            testEmail.setError("Email no valido");
            comprobador =1;
        }
        if(testContra.getEditText().getText().toString().trim().isEmpty()){
            testContra.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            //Toast.makeText(getApplicationContext(),"No has introducido ninguna Contraseña o es demasiado pequeña",Toast.LENGTH_SHORT).show();
            comprobador =1;
            testContra.setError("No has introducido ninguna Contraseña");
        }
        if(!(testContra.getEditText().getText().toString().trim().length()>6)){
            testContra.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            //Toast.makeText(getApplicationContext(),"No has introducido ninguna Contraseña o es demasiado pequeña",Toast.LENGTH_SHORT).show();
            testContra.setError("La contraseña es demasiado pequeña minimo 6 caracteres");
            comprobador =1;
        }
        return  comprobador;
    }

}