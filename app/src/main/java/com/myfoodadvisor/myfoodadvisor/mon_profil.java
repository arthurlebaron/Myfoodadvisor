package com.myfoodadvisor.myfoodadvisor;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by Utilisateur on 06/11/2017.
 */

public class mon_profil extends AppCompatActivity {
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

    private ImageView image;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;

    private SharedPreferences prefs;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monprofil);

        pseudo =(TextView) findViewById(R.id.profpseudo);
        age =(TextView) findViewById(R.id.profage);
        sexe =(TextView) findViewById(R.id.profsexe);
        taille =(TextView) findViewById(R.id.proftaille);
        poids =(TextView) findViewById(R.id.profpoids);
        lieu =(TextView) findViewById(R.id.proflieu);
        regime =(TextView) findViewById(R.id.profregime);

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
        if (prefs.getString("facebook",null) != null){
           Picasso.with(getBaseContext()).load(mAuth.getCurrentUser().getPhotoUrl().toString()).transform(new CropCircleTransformation()).into(image);
        }

        mage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i = new Intent(mon_profil.this, modifage.class);
               // startActivity(i);
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


}
