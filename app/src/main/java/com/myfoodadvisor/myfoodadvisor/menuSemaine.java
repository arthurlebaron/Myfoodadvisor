package com.myfoodadvisor.myfoodadvisor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myfoodadvisor.myfoodadvisor.Entities.User;

import java.util.ArrayList;
import java.util.List;


public class menuSemaine extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {


    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;

    private SharedPreferences prefs;

    private TextView menus[] = new TextView[14];
    private String regimeUser;
    private List<String> Recettes = new ArrayList<String>();
    private List<String> menuSemaine = new ArrayList<String>();
    private TextView test;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_semaine);

    }

    @Override
    protected void onResume() {
        FacebookSdk.setApplicationId("297204487433924");
        FacebookSdk.sdkInitialize(getApplicationContext());

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();
        prefs = getSharedPreferences("Utilisateur", MODE_PRIVATE);

        String pseudo = prefs.getString("Pseudo/email",null);
        mDatabase= FirebaseDatabase.getInstance();

        menus[0] = (TextView) findViewById(R.id.menuLundi);

        menus[1] = (TextView) findViewById(R.id.menuLundi2);

        menus[2] = (TextView) findViewById(R.id.menuMardi);

        menus[3] = (TextView) findViewById(R.id.menuMardi2);

        menus[4] = (TextView) findViewById(R.id.menuMerc);

        menus[5] = (TextView) findViewById(R.id.menuMerc2);

        menus[6] = (TextView) findViewById(R.id.menuJeudi);

        menus[7] = (TextView) findViewById(R.id.menuJeudi2);

        menus[8] = (TextView) findViewById(R.id.menuVendr);

        menus[9] = (TextView) findViewById(R.id.menuVendr2);

        menus[10] = (TextView) findViewById(R.id.menuSam);

        menus[11] = (TextView) findViewById(R.id.menuSam2);

        menus[12] = (TextView) findViewById(R.id.menuDim);

        menus[13] = (TextView) findViewById(R.id.menuDim2);



        menus[0].setText(prefs.getString("lundi",null));
        menus[1].setText(prefs.getString("lundi2",null));
        menus[2].setText(prefs.getString("mardi",null));
        menus[3].setText(prefs.getString("mardi2",null));
        menus[4].setText(prefs.getString("mercredi",null));
        menus[5].setText(prefs.getString("mercredi2",null));
        menus[6].setText(prefs.getString("jeudi",null));
        menus[7].setText(prefs.getString("jeudi2",null));
        menus[8].setText(prefs.getString("vendredi",null));
        menus[9].setText(prefs.getString("vendredi2",null));
        menus[10].setText(prefs.getString("samedi",null));
        menus[11].setText(prefs.getString("samedi2",null));
        menus[12].setText(prefs.getString("dimanche",null));
        menus[13].setText(prefs.getString("dimanche2",null));

        if(menus[0]==null) {
            mDatabase.getReference().child("users").child(pseudo).child("regime").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        mDatabase.getReference().child(dataSnapshot.getValue().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getValue() != null) {
                                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                        String recette = postSnapshot.getValue().toString();
                                        Log.d("DEBUG",recette);
                                        Recettes.add(recette);
                                    }
                                    Log.d("DEBUG",dataSnapshot.toString());
                                    Log.d("DEBUG",Recettes.toString());


                                    for (int i = 0; i < 14; i++) {
                                        if (Recettes.size() != 0) {
                                            int index = (int) Math.random() * Recettes.size();
                                            menuSemaine.add(Recettes.get(index));
                                            Recettes.remove(index);
                                        } else {
                                            menuSemaine.add("Plus de recette");
                                        }
                                    }
                                    prefs.edit().putString("lundi", menuSemaine.get(0)).apply();
                                    prefs.edit().putString("lundi2", menuSemaine.get(1)).apply();
                                    prefs.edit().putString("mardi", menuSemaine.get(2)).apply();
                                    prefs.edit().putString("mardi2", menuSemaine.get(3)).apply();
                                    prefs.edit().putString("mercredi", menuSemaine.get(4)).apply();
                                    prefs.edit().putString("mercredi2", menuSemaine.get(5)).apply();
                                    prefs.edit().putString("jeudi", menuSemaine.get(6)).apply();
                                    prefs.edit().putString("jeudi2", menuSemaine.get(7)).apply();
                                    prefs.edit().putString("vendredi", menuSemaine.get(8)).apply();
                                    prefs.edit().putString("vendredi2", menuSemaine.get(9)).apply();
                                    prefs.edit().putString("samedi", menuSemaine.get(10)).apply();
                                    prefs.edit().putString("samedi2", menuSemaine.get(11)).apply();
                                    prefs.edit().putString("dimanche", menuSemaine.get(12)).apply();
                                    prefs.edit().putString("dimanche2", menuSemaine.get(13)).apply();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }


        for( TextView menu  : menus)
        {
            final String menuName=menu.getText().toString();
            menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDatabase.getReference().child("recettes").child(menuName).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot==null | dataSnapshot.getValue()==null) {
                                Toast.makeText(getApplicationContext(), "Chargement recette"+dataSnapshot.toString(), Toast.LENGTH_LONG).show();
                            }
                            else {
                                prefs = getSharedPreferences("Utilisateur", MODE_PRIVATE);
                                prefs.edit().putString("Recette", menuName).apply();
                                Toast.makeText(getApplicationContext(), "Chargement recette", Toast.LENGTH_LONG).show();

                                Intent i = new Intent(menuSemaine.this, recette.class);
                                startActivity(i);
                                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            });
        }
        super.onResume();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_accueil) {
            Intent i = new Intent(menuSemaine.this, Acceuil.class);
            startActivity(i);
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            finish();

        } else if (id == R.id.nav_menu_semaine) {
            Intent i = new Intent(menuSemaine.this, menuSemaine.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_profil) {
            Intent i = new Intent(menuSemaine.this, mon_profil.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_deco) {
            prefs.edit().putString("facebook", "0").apply();
            mAuth.signOut();
            LoginManager.getInstance().logOut();
            finish();
            Intent i = new Intent(menuSemaine.this, Myfoodadvisor.class);
            startActivity(i);
        } else if (id == R.id.nav_proposition) {
            String username = prefs.getString("Pseudo/email", null);
            mRef.child("users").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null){
                        String autho = dataSnapshot.child("authorisation").getValue().toString();
                        if (autho.equals("oui")){
                            Intent i = new Intent(menuSemaine.this, proposition.class);
                            startActivity(i);
                            finish();
                        }else if (autho.equals("non")){
                            Intent i = new Intent(menuSemaine.this, nonauth.class);
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
            Intent i = new Intent(menuSemaine.this, MapsActivityCurrentPlace.class);
            startActivity(i);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
