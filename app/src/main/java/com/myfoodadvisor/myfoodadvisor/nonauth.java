package com.myfoodadvisor.myfoodadvisor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by arthu on 03/12/2017.
 */

public class nonauth extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;

    private SharedPreferences prefs;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        FacebookSdk.setApplicationId("297204487433924");
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nonauthorise);

        prefs = getSharedPreferences("Utilisateur", MODE_PRIVATE);
        final String username = prefs.getString("Pseudo/email", null);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_accueil) {
                    Intent i = new Intent(nonauth.this, Acceuil.class);
                    startActivity(i);

                } else if (id == R.id.nav_menu_semaine) {
                    Intent i = new Intent(nonauth.this, menuSemaine.class);
                    startActivity(i);
                    finish();
                } else if (id == R.id.nav_profil) {
                    Intent i = new Intent(nonauth.this, mon_profil.class);
                    startActivity(i);
                    finish();
                } else if (id == R.id.nav_deco) {
                    prefs.edit().putString("facebook", "0").apply();
                    mAuth.signOut();
                    LoginManager.getInstance().logOut();
                    finish();
                    Intent i = new Intent(nonauth.this, Myfoodadvisor.class);
                    startActivity(i);
                } else if (id == R.id.nav_proposition) {
                    String username = prefs.getString("Pseudo/email", null);
                    mRef.child("users").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null){
                                String autho = dataSnapshot.child("authorisation").getValue().toString();
                                if (autho.equals("oui")){
                                    Intent i = new Intent(nonauth.this, proposition.class);
                                    startActivity(i);
                                    finish();
                                }else if (autho.equals("non")){
                                    Intent i = new Intent(nonauth.this, nonauth.class);
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
                    Intent i = new Intent(nonauth.this, MapsActivityCurrentPlace.class);
                    startActivity(i);
                    finish();
                }

                // DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                //drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
}
