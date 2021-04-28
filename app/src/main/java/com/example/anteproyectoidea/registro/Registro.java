package com.example.anteproyectoidea.registro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.anteproyectoidea.EmpresaRegistrarLogin;
import com.example.anteproyectoidea.MainActivity;
import com.example.anteproyectoidea.R;
import com.example.anteproyectoidea.dto.UserDTO;
import com.example.anteproyectoidea.logins.login;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

public class Registro extends AppCompatActivity {

    public static GoogleSignInClient mGoogleSingInClient;
    private final static int RC_SIGN_IN=100;
    public static FirebaseAuth mAuth;
    private FirebaseFirestore db;

   /* @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("Google",true);
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
        db = FirebaseFirestore.getInstance();
        permisosGPS();


    }

    private void permisosGPS() {
        int permisionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);

        if (permisionCheck == PackageManager.PERMISSION_DENIED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){


            }else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},1);


            }
        }
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
                Intent intent2 = new Intent(getApplicationContext(), login.class);
                startActivity(intent2);

                break;
            case R.id.btnregistrar:
                Toast.makeText(getApplicationContext(),"Rellena todos los campos",Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                    Intent intent = new Intent(getApplicationContext(), Registrarse_actividad.class);
                    intent.putExtra("mAuth",mAuth.toString());
                    startActivity(intent);
                break;
            case R.id.btnRegistrarEmpresa:
                Intent empresaIntent = new Intent(getApplicationContext(), EmpresaRegistrarLogin.class);

                startActivity(empresaIntent);
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
                           // public UserDTO(String key, String nombre, String email, String direccion, String imagenUri)

                            //Location location = new Location( LocationManager.GPS_PROVIDER);

                            //double latitud = location.getLatitude();
                            //double longitud = location.getLongitude();

                            UserDTO userDTO = new UserDTO(mAuth.getCurrentUser().getUid(),account.getDisplayName(),account.getEmail(),account.getPhotoUrl().toString(),0,0);

                            db.collection("usersGoogle").document(mAuth.getCurrentUser().getUid()).set(userDTO);

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                            intent.putExtra("esGoogle",true);
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

    

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //FirebaseAuth.getInstance().signOut();

    }
}
