package com.myfoodadvisor.myfoodadvisor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myfoodadvisor.myfoodadvisor.Entities.Proposition;
import com.myfoodadvisor.myfoodadvisor.Entities.User;
import com.myfoodadvisor.myfoodadvisor.modification.modificationage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by arthu on 03/12/2017.
 */

public class proposition extends AppCompatActivity {

    private EditText recette;
    private EditText proposition;
    private Button valprop;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        FacebookSdk.setApplicationId("297204487433924");
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposition);

        prefs = getSharedPreferences("Utilisateur", MODE_PRIVATE);
        final String username = prefs.getString("Pseudo/email", null);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference();

        recette = (EditText) findViewById(R.id.nom_recette);
        proposition = (EditText) findViewById(R.id.description);
        valprop = (Button) findViewById(R.id.propval);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_accueil) {
                    Intent i = new Intent(proposition.this, Acceuil.class);
                    startActivity(i);

                } else if (id == R.id.nav_menu_semaine) {
                    Intent i = new Intent(proposition.this, menuSemaine.class);
                    startActivity(i);
                    finish();
                } else if (id == R.id.nav_profil) {
                    Intent i = new Intent(proposition.this, mon_profil.class);
                    startActivity(i);
                    finish();
                } else if (id == R.id.nav_deco) {
                    prefs.edit().putString("facebook", "0").apply();
                    mAuth.signOut();
                    LoginManager.getInstance().logOut();
                    finish();
                    Intent i = new Intent(proposition.this, Myfoodadvisor.class);
                    startActivity(i);
                } else if (id == R.id.nav_proposition) {
                    String username = prefs.getString("Pseudo/email", null);
                    mRef.child("users").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null){
                                String autho = dataSnapshot.child("authorisation").getValue().toString();
                                if (autho.equals("oui")){
                                    Intent i = new Intent(proposition.this, proposition.class);
                                    startActivity(i);
                                    finish();
                                }else if (autho.equals("non")){
                                    Intent i = new Intent(proposition.this, nonauth.class);
                                    startActivity(i);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else if (id == R.id.nav_map) {
                    Intent i = new Intent(proposition.this, MapsActivityCurrentPlace.class);
                    startActivity(i);
                    finish();
                }

                // DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                //drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        valprop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = prefs.getString("Pseudo/email", null);
                String nom_recette = recette.getText().toString();
                String description = proposition.getText().toString();
                String Id = nom_recette + username ;

                Proposition prop = new Proposition(nom_recette, description);

                mRef.child("Proposition").child(Id).setValue(prop).addOnCompleteListener(proposition.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

                            String username = prefs.getString("Pseudo/email", null);

                            mRef.child("users").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getValue() != null){
                                        String username = prefs.getString("Pseudo/email", null);
                                        String newage = dataSnapshot.child("age").getValue().toString();
                                        String sexe = dataSnapshot.child("sexe").getValue().toString();
                                        String taille = dataSnapshot.child("taille").getValue().toString();
                                        String poids = dataSnapshot.child("poids").getValue().toString();
                                        String lieu = dataSnapshot.child("lieu").getValue().toString();
                                        String regime = dataSnapshot.child("regime").getValue().toString();
                                        String userId = mAuth.getCurrentUser().getUid();
                                        String password = "pasdemotdepasse";
                                        String authorisation = "non";
                                        User newUser = new User(username, userId, password, newage, sexe, taille, poids, lieu, regime, authorisation);
                                        Map<String,Object> update = new HashMap<>();
                                        update.put(username,newUser);
                                        mRef.child("users").updateChildren(update).addOnCompleteListener(proposition.this, new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    Intent i = new Intent(proposition.this, Acceuil.class);
                                                    startActivity(i);
                                                    finish();
                                                }else {
                                                    Toast.makeText(proposition.this, "Erreur dans la modification.",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                });
            }
        });
    }

}
