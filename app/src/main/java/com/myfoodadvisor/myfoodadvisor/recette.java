package com.myfoodadvisor.myfoodadvisor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.Key;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class recette extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {


    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;

    private SharedPreferences prefs;
    private String recette;
    private List<String> listIngredients = new ArrayList<>();
    private String prix;

    private TextView nomRct;
    private TextView tps_cui;
    private TextView tps_prep;

    private ListView ingredients;


    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.setApplicationId("297204487433924");
        FacebookSdk.sdkInitialize(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recette);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();
        prefs = getSharedPreferences("Utilisateur", MODE_PRIVATE);

        recette = prefs.getString("Recette",null);
        mDatabase= FirebaseDatabase.getInstance();

        nomRct = (TextView) findViewById(R.id.nomRecette);
        tps_cui = (TextView) findViewById(R.id.tps_cui);
        tps_prep = (TextView) findViewById(R.id.tps_prep);
        ingredients = (ListView) findViewById(R.id.Lingredients);

        nomRct.setText(recette);

        mDatabase.getReference().child("recettes").child(recette).child("temps_cui").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null)
                {
                    tps_cui.setText(dataSnapshot.getValue().toString());

                    mDatabase.getReference().child("recettes").child(recette).child("temps_prep").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null) {
                            tps_prep.setText(dataSnapshot.getValue().toString());

                            for(int i=1;i<10;i++) {
                                String name= "ingredient"+i;
                                Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG).show();
                                mDatabase.getReference().child("recettes").child(recette).child("ingrédients").child(name).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.getValue()!=null) {
                                            listIngredients.add(dataSnapshot.getValue().toString());
                                        }
                                        }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });
                            }
                                }
                            }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                }
                });
                    Toast.makeText(getApplicationContext(), listIngredients.toString(), Toast.LENGTH_LONG).show();
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(recette.this, android.R.layout.simple_list_item_1, listIngredients);
                ingredients.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_accueil) {
            Intent i = new Intent(recette.this, Acceuil.class);
            startActivity(i);
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            finish();

        } else if (id == R.id.nav_menu_semaine) {
            Intent i = new Intent(recette.this, recette.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_profil) {
            Intent i = new Intent(recette.this, mon_profil.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_deco) {
            mAuth.signOut();
            LoginManager.getInstance().logOut();
            finish();
            Intent i = new Intent(recette.this, Myfoodadvisor.class);
            startActivity(i);
        } else if (id == R.id.nav_proposition) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
