package com.example.anteproyectoidea.logins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.anteproyectoidea.MainActivity;
import com.example.anteproyectoidea.R;
import com.example.anteproyectoidea.registro.Registro;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    private EditText testEmail,testContra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        testEmail = findViewById(R.id.emailLogin);
        testContra = findViewById(R.id.passwordlogin);
    }


    public void login(View view) {

        if(isEmailValid(testEmail.getText().toString())){

            Registro.mAuth.signInWithEmailAndPassword(testEmail.getText().toString(),testContra.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){


                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("esGoogle",false);
                        startActivity(intent);

                    }else{
                        
                    }
                }
            });
        }

    }


    public void goHome(FirebaseAuth M){
        /*String name= bundle.getString("nombre");
        String email = bundle.getString("email");
        String uriFoto = bundle.getString("imagen");

         */



    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}