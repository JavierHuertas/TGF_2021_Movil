package com.example.anteproyectoidea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import kotlin.jvm.internal.MagicApiIntrinsics;

public class Registro extends AppCompatActivity {

    private GoogleSignInClient mGoogleSingInClient;
    private final static int RC_SIGN_IN=100;
    private FirebaseAuth mAuth;

   /* @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        getSupportActionBar().hide();
        crearPeticion();
        mAuth= FirebaseAuth.getInstance();

    }

    public void iniciar(View view) {

        switch (view.getId()){
            case R.id.btnGoogle:
                Toast.makeText(getApplicationContext(),"google",Toast.LENGTH_SHORT).show();
                // Configure Google Sign In
                mGoogleSingInClient.signOut();
                signInGoogle();
                break;
            case R.id.btnEmailPasww:
                Toast.makeText(getApplicationContext(),"Email y contraseña",Toast.LENGTH_SHORT).show();


                break;
            case R.id.btnregistrar:
                Toast.makeText(getApplicationContext(),"Rellena todos los campos",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),Registrarse_actividad.class);
                    startActivity(intent);
                break;

        }
    }

    private void crearPeticion() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSingInClient = GoogleSignIn.getClient(this,gso);
        //Toast.makeText(getApplicationContext(),"google"+mGoogleSingInClient.getApi().getClientKey(),Toast.LENGTH_SHORT).show();

    }

    private void signInGoogle() {
        Intent signInIntent = mGoogleSingInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            if(task.isSuccessful()){
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                if(account!=null) {
                    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            intent.putExtra("email",account.getEmail().toString());
                            intent.putExtra("nombre",account.getDisplayName().toString());
                            String uriImagen = account.getPhotoUrl().toString();
                            intent.putExtra("imagen",uriImagen);
                            startActivity(intent);
                        }
                    });
                }
                //firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately

            }}
            else{

                Toast.makeText(getApplicationContext(),"no ha elegido ninguna cuenta de google",Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.



                        }

                        // ...
                    }
                });
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //FirebaseAuth.getInstance().signOut();

    }
}
