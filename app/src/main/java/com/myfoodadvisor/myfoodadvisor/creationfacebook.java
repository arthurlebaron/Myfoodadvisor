package com.myfoodadvisor.myfoodadvisor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.myfoodadvisor.myfoodadvisor.Entities.User;

/**
 * Created by Utilisateur on 09/11/2017.
 */

public class creationfacebook extends AppCompatActivity implements View.OnClickListener {
    private EditText age;
    private Spinner sexe;
    private EditText taille;
    private EditText poids;
    private EditText lieu;
    private Spinner regime;
    private Button Valider;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;

    private SharedPreferences prefs;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        FacebookSdk.setApplicationId("297204487433924");
        FacebookSdk.sdkInitialize(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.creationfacebook);

        age =(EditText) findViewById(R.id.fbage);
        sexe =(Spinner) findViewById(R.id.fbsexe);
        taille =(EditText) findViewById(R.id.fbtaille);
        poids =(EditText) findViewById(R.id.fbpoids);
        lieu =(EditText) findViewById(R.id.fblieu);
        regime =(Spinner) findViewById(R.id.fbregime);
        Valider =(Button) findViewById(R.id.fbvalider);
        Valider.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference();

        prefs = getSharedPreferences("Utilisateur", MODE_PRIVATE);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.item_sexe,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexe.setAdapter(adapter1);

        ArrayAdapter <CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.item_regime,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regime.setAdapter(adapter2);
    }
    private void createUser(final String Age,final String Sexe,final String Taille,final String Poids,final String Lieu,final String Regime) {
        if (mAuth.getCurrentUser()!= null) {
            String userId = mAuth.getCurrentUser().getUid();
            Profile profile = Profile.getCurrentProfile();
            String username = profile.getFirstName() + profile.getLastName();
            String password = "pasdemotdepasse";
            User newUser = new User(username, userId, password, Age, Sexe, Taille, Poids, Lieu, Regime);
            mRef.child("users").child(username).setValue(newUser).addOnCompleteListener(creationfacebook.this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        ///mRef.child("username").child(user).setValue(userId);
                        Profile profile = Profile.getCurrentProfile();
                        String username = profile.getFirstName() + profile.getLastName();
                        prefs.edit().putString("Pseudo/email", username).apply();

                        Intent i = new Intent(creationfacebook.this, Acceuil.class);
                        startActivity(i);
                        finish();
                    }
                }
            });
        }else {
            Intent i = new Intent(creationfacebook.this, Myfoodadvisor.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        String Age = age.getText().toString();
        String Sexe = sexe.getSelectedItem().toString();
        String Taille =taille.getText().toString();
        String Poids = poids.getText().toString();
        String Lieu = lieu.getText().toString();
        String Regime = regime.getSelectedItem().toString();

        if(!TextUtils.isEmpty(age.getText().toString())&& !TextUtils.isEmpty(Sexe)&& !TextUtils.isEmpty(taille.getText().toString())&& !TextUtils.isEmpty(poids.getText().toString())&& !TextUtils.isEmpty(Lieu)&& !TextUtils.isEmpty(Regime)){
            createUser(Age,Sexe,Taille,Poids,Lieu,Regime);
        }
    }
}
