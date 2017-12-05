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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class coursesSemaine extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {


    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private SharedPreferences prefs;
    private SharedPreferences prefs_bis;
    private List<String> courses = new ArrayList<String>();
    private List<String> courses_added = new ArrayList<String>();
    private List<String> jours = new ArrayList<String>();
    private TextView test;
    private String tableau[] = new String[20];
    private int qt[] = new int[20];
    private String ingre="ingredient";
    private TextView txtview[] = new TextView[20];


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_semaine);
    }

    @Override
    protected void onResume() {

        FacebookSdk.setApplicationId("297204487433924");
        FacebookSdk.sdkInitialize(getApplicationContext());

        courses_added.clear();
        courses.clear();
        Arrays.fill(qt,0);
        Arrays.fill(tableau,null);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();
        prefs = getSharedPreferences("Utilisateur", MODE_PRIVATE);

        String pseudo = prefs.getString("Pseudo/email",null);
        mDatabase= FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference();
        jours.add("lundi");
        jours.add("lundi2");
        jours.add("mardi");
        jours.add("mardi2");
        jours.add("mercredi");
        jours.add("mercredi2");
        jours.add("jeudi");
        jours.add("jeudi2");
        jours.add("vendredi");
        jours.add("vendredi2");
        jours.add("samedi");
        jours.add("samedi2");
        jours.add("dimanche");
        jours.add("dimanche2");
        txtview[0]= (TextView) findViewById(R.id.ingredient1);
        txtview[1]=(TextView) findViewById(R.id.ingredient2);
        txtview[2]=(TextView) findViewById(R.id.ingredient3);
        txtview[3]= (TextView) findViewById(R.id.ingredient4);
        txtview[4]= (TextView) findViewById(R.id.ingredient5);
        txtview[5]=(TextView) findViewById(R.id.ingredient6);
        txtview[6]= (TextView) findViewById(R.id.ingredient7);
        txtview[7]= (TextView) findViewById(R.id.ingredient8);
        txtview[8]=(TextView) findViewById(R.id.ingredient9);
        txtview[9]=(TextView) findViewById(R.id.ingredient10);
        txtview[10]= (TextView) findViewById(R.id.ingredient11);
        txtview[11]= (TextView) findViewById(R.id.ingredient12);
        txtview[12]= (TextView) findViewById(R.id.ingredient13);
        txtview[13]= (TextView) findViewById(R.id.ingredient14);
        txtview[14]= (TextView) findViewById(R.id.ingredient15);
        txtview[15]=(TextView) findViewById(R.id.ingredient16);
        txtview[16]=(TextView) findViewById(R.id.ingredient17);
        txtview[17]=(TextView) findViewById(R.id.ingredient18);
        txtview[18]= (TextView) findViewById(R.id.ingredient19);
        txtview[19]=(TextView) findViewById(R.id.ingredient20);

        for(String day: jours)
        {
            prefs_bis=getSharedPreferences(day,MODE_PRIVATE);
            courses.add(prefs_bis.getString("ingredient1",""));
            courses.add(prefs_bis.getString("ingredient2",""));
            courses.add(prefs_bis.getString("ingredient3",""));
            courses.add(prefs_bis.getString("ingredient4",""));



        }
        Log.d("DEV",courses.toString());
        Collections.sort(courses);
        Log.d("DEV",courses.toString());
        int i=-1;
        for(String obj:courses)
        {
            if(!courses_added.contains(obj))
            {i++;
                Log.d("DEV","ADDED"+obj);
                courses_added.add(obj);
                tableau[i]=obj;
                qt[i]=1;
            }
            else
            {
                qt[i]=qt[i]+1;
            }
        }
        Log.d("DEV", Arrays.toString(tableau));
        Log.d("DEV", Arrays.toString(qt));
        for(int t=0;t<i;t++)
        {
            txtview[t].setText(tableau[t]+"   x"+qt[t]);
            Log.d("DEV",tableau[t]+"x"+qt[t]+"////"+t+"-"+i);
        }

        super.onResume();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_accueil) {
            Intent i = new Intent(coursesSemaine.this, Acceuil.class);
            startActivity(i);
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            finish();

        } else if (id == R.id.nav_menu_semaine) {
            Intent i = new Intent(coursesSemaine.this, coursesSemaine.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_profil) {
            Intent i = new Intent(coursesSemaine.this, mon_profil.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_deco) {
            prefs.edit().putString("facebook", "0").apply();
            mAuth.signOut();
            LoginManager.getInstance().logOut();
            finish();
            Intent i = new Intent(coursesSemaine.this, Myfoodadvisor.class);
            startActivity(i);
        } else if (id == R.id.nav_proposition) {
            String username = prefs.getString("Pseudo/email", null);
            mRef.child("users").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null){
                        String autho = dataSnapshot.child("authorisation").getValue().toString();
                        if (autho.equals("oui")){
                            Intent i = new Intent(coursesSemaine.this, proposition.class);
                            startActivity(i);
                            finish();
                        }else if (autho.equals("non")){
                            Intent i = new Intent(coursesSemaine.this, nonauth.class);
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
            Intent i = new Intent(coursesSemaine.this, MapsActivityCurrentPlace.class);
            startActivity(i);
            finish();
        }
        else if (id == R.id.nav_courses) {
            Intent i = new Intent(coursesSemaine.this, coursesSemaine.class);
            startActivity(i);
            finish();
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
