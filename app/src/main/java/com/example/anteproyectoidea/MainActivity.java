package com.example.anteproyectoidea;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anteproyectoidea.registro.Registro;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView r;
    private TextView f;
    private ImageView t;
    private StorageReference mReference;
    private FirebaseFirestore db;
    private String name;
    private String email;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = FirebaseFirestore.getInstance();
        mReference = FirebaseStorage.getInstance().getReference();

        Bundle bundle = getIntent().getExtras();
        Boolean esGoogle =bundle.getBoolean("esGoogle");

        Toast.makeText(getApplicationContext(),esGoogle+"",Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(),Registro.mAuth.getUid(),Toast.LENGTH_LONG).show();

        NavigationView navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        r = header.findViewById(R.id.EmailUsuario);
        f = header.findViewById(R.id.NombreUsuario);
        t = header.findViewById(R.id.ImagenUsuario);



        if(esGoogle){
            //Login google
            db.collection("usersGoogle").document(Registro.mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()){
                        String name2 =  documentSnapshot.getString("nombre");
                        String email2 = documentSnapshot.getString("email");
                        Uri uri2 = Uri.parse(documentSnapshot.getString("imagenUri"));

                        r.setText(email2);
                        f.setText(name2);
                        Picasso.get().load(uri2).into(t);
                    }else{
                        Toast.makeText(getApplicationContext(),"Hay un error buscar solucioness",Toast.LENGTH_LONG).show();
                    }
                }
            });
            Toast.makeText(getApplicationContext(),"email"+ email,Toast.LENGTH_LONG).show();

        }else{
            db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()){

                       String name2 =  documentSnapshot.getString("nombre");
                       String email2 = documentSnapshot.getString("email");
                       Uri uri2 = Uri.parse(documentSnapshot.getString("imagenUri"));
                        Toast.makeText(getApplicationContext(),"email"+ email2,Toast.LENGTH_LONG).show();
                        r.setText(email2);
                        f.setText(name2);
                        Picasso.get().load(uri2).into(t);

                    }else{
                        Toast.makeText(getApplicationContext(),"Hay un error buscar solucioness",Toast.LENGTH_LONG).show();
                    }
                }
            });



        }







        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                onBackPressed();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public String[] infoUsuarioNoGoogle(String email,String nombre,String uri){

        String[] array = {email,nombre,uri};

        return array;
    }

    }
