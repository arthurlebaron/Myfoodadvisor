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
    private List<String> listRecettes = new ArrayList<String>();
    private TextView test;


    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.setApplicationId("297204487433924");
        FacebookSdk.sdkInitialize(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_semaine);

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

        mDatabase.getReference().child("users").child(pseudo).child("regime").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null)
                {
                    regimeUser = dataSnapshot.getValue().toString();
                    mDatabase.getReference().child(regimeUser).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null) {
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    String recette = postSnapshot.getValue().toString();
                                    listRecettes.add(recette);
                                }
                            }
                            for(TextView menu : menus)
                            {

                                if(listRecettes.size()!=0)
                                {
                                    int index = (int) Math.random()*listRecettes.size();
                                    menu.setText(listRecettes.get(index));
                                    listRecettes.remove(index);
                                }
                                else
                                {
                                    menu.setText("Plus de recette");
                                }
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
            mAuth.signOut();
            LoginManager.getInstance().logOut();
            finish();
            Intent i = new Intent(menuSemaine.this, Myfoodadvisor.class);
            startActivity(i);
        } else if (id == R.id.nav_proposition) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
