package com.myfoodadvisor.myfoodadvisor;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myfoodadvisor.myfoodadvisor.modification.modificationage;
import com.myfoodadvisor.myfoodadvisor.modification.modificationlieu;
import com.myfoodadvisor.myfoodadvisor.modification.modificationpoids;
import com.myfoodadvisor.myfoodadvisor.modification.modificationregime;
import com.myfoodadvisor.myfoodadvisor.modification.modificationsexe;
import com.myfoodadvisor.myfoodadvisor.modification.modificationtaille;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by Utilisateur on 06/11/2017.
 */

public class mon_profil extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TextView pseudo;
    private TextView age;
    private TextView sexe;
    private TextView taille;
    private TextView poids;
    private TextView lieu;
    private TextView regime;

    private CardView mage;
    private CardView msexe;
    private CardView mtaille;
    private CardView mpoids;
    private CardView mlieu;
    private CardView mregime;
    private Button logout;

    private ImageView image;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;

    private SharedPreferences prefs;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monprofil);


    }

    @Override
    protected void onResume() {
        FacebookSdk.setApplicationId("297204487433924");
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onResume();
        setContentView(R.layout.activity_monprofil);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        pseudo =(TextView) findViewById(R.id.profpseudo);
        age =(TextView) findViewById(R.id.profage);
        sexe =(TextView) findViewById(R.id.profsexe);
        taille =(TextView) findViewById(R.id.proftaille);
        poids =(TextView) findViewById(R.id.profpoids);
        lieu =(TextView) findViewById(R.id.proflieu);
        regime =(TextView) findViewById(R.id.profregime);
        logout = (Button) findViewById(R.id.plogout);

        mage = (CardView)findViewById(R.id.modifage);
        msexe = (CardView)findViewById(R.id.modifsexe);
        mtaille = (CardView)findViewById(R.id.modiftaille);
        mpoids = (CardView)findViewById(R.id.modifpoids);
        mlieu = (CardView)findViewById(R.id.modiflieu);
        mregime = (CardView)findViewById(R.id.modifregime);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference();

        prefs = getSharedPreferences("Utilisateur", MODE_PRIVATE);
        String username = prefs.getString("Pseudo/email", null);

        image = (ImageView) findViewById(R.id.profimage);
        if (prefs.getString("facebook",null) == "1"){
            Picasso.with(getBaseContext()).load(mAuth.getCurrentUser().getPhotoUrl().toString()).transform(new CropCircleTransformation()).into(image);
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                LoginManager.getInstance().logOut();
                finish();
                Intent i = new Intent(mon_profil.this, Myfoodadvisor.class);
                startActivity(i);
            }
        });

        mage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mon_profil.this, modificationage.class);
                startActivity(i);
            }
        });
        msexe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mon_profil.this, modificationsexe.class);
                startActivity(i);
            }
        });
        mtaille.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mon_profil.this, modificationtaille.class);
                startActivity(i);
            }
        });
        mpoids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mon_profil.this, modificationpoids.class);
                startActivity(i);
            }
        });
        mlieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mon_profil.this, modificationlieu.class);
                startActivity(i);
            }
        });
        mregime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mon_profil.this, modificationregime.class);
                startActivity(i);
            }
        });


        pseudo.setText(username);

        mRef.child("users").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){
                    age.setText(dataSnapshot.child("age").getValue().toString()+" ans");
                    sexe.setText(dataSnapshot.child("sexe").getValue().toString());
                    taille.setText(dataSnapshot.child("taille").getValue().toString()+" Cm");
                    poids.setText(dataSnapshot.child("poids").getValue().toString()+" Kg");
                    lieu.setText(dataSnapshot.child("lieu").getValue().toString());
                    regime.setText(dataSnapshot.child("regime").getValue().toString());


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_accueil) {
            Intent i = new Intent(mon_profil.this, Acceuil.class);
            startActivity(i);
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            finish();

        } else if (id == R.id.nav_menu_semaine) {
            Intent i = new Intent(mon_profil.this, menuSemaine.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_profil) {
            Intent i = new Intent(mon_profil.this, mon_profil.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_deco) {
            prefs.edit().putString("facebook", "0").apply();
            mAuth.signOut();
            LoginManager.getInstance().logOut();
            finish();
            Intent i = new Intent(mon_profil.this, Myfoodadvisor.class);
            startActivity(i);
        } else if (id == R.id.nav_proposition) {
            String username = prefs.getString("Pseudo/email", null);
            mRef.child("users").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null){
                        String autho = dataSnapshot.child("authorisation").getValue().toString();
                        if (autho.equals("oui")){
                            Intent i = new Intent(mon_profil.this, proposition.class);
                            startActivity(i);
                            finish();
                        }else if (autho.equals("non")){
                            Intent i = new Intent(mon_profil.this, nonauth.class);
                            startActivity(i);
                            finish();
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else if (id == R.id.nav_map) {
            Intent i = new Intent(mon_profil.this, MapsActivityCurrentPlace.class);
            startActivity(i);
            finish();
        }
        else if (id == R.id.nav_courses) {
            Intent i = new Intent(mon_profil.this, menuSemaine.class);
            startActivity(i);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
