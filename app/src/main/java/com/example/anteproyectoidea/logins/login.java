package com.example.anteproyectoidea.logins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.example.anteproyectoidea.MainActivity;
import com.example.anteproyectoidea.R;
import com.example.anteproyectoidea.registro.Registro;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class login extends AppCompatActivity {

    private FirebaseFirestore db;
    private EditText testEmail,testContra;
    boolean EsTienda ;
    private FirebaseUser user;
    private String tipo;
    private LottieAnimationView animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        testEmail = findViewById(R.id.emailLogin);
        testContra = findViewById(R.id.passwordlogin);
        db = FirebaseFirestore.getInstance();
        animation = findViewById(R.id.loginAnimation);
        empezarAnimacion(animation);

    }

    private void empezarAnimacion(LottieAnimationView animation) {
        animation.setAnimation(R.raw.spinner_only);

        animation.playAnimation();



    }
    private void goodAnimation(){
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


                goHomeUser();

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

        if(isEmailValid(testEmail.getText().toString())){

            Registro.mAuth.signInWithEmailAndPassword(testEmail.getText().toString(),testContra.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {



                    if(task.isSuccessful()){
                        String uid = Registro.mAuth.getCurrentUser().getUid();
                        user = Registro.mAuth.getCurrentUser();

                        if(true){

                            db.collection("shops").whereEqualTo("key",uid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                            if(task.getResult().isEmpty()){

                                                goodAnimation();

                                            }else{
                                                goHomeShop();
                                            }
                                }
                            });
                        }
                    }else{
                       errorAnimation(task.getException().getMessage());
                    }
                }
            });
        }else{


        }

    }



    public void comprobarCheckShop(){






    }

    public void goHomeUser(){
        //goodAnimation();
        Toast.makeText(getApplicationContext(),"No es tienda",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("esGoogle",false);
        startActivity(intent);
    }
    public void goHomeShop(){
        //intent tiendas
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
}