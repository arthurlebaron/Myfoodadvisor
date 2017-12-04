package com.myfoodadvisor.myfoodadvisor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myfoodadvisor.myfoodadvisor.Entities.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Utilisateur on 07/11/2017.
 */

public class creationcompte extends AppCompatActivity implements View.OnClickListener {

    private EditText Pseudo;
    private EditText mp;
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
    List<String> Recettes = new ArrayList<String>();
    List<String> menuSemaine = new ArrayList<String>();


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.creationcompte);

        Pseudo =(EditText) findViewById(R.id.creerpseudo);
        mp =(EditText) findViewById(R.id.creermp);
        age =(EditText) findViewById(R.id.creerage);
        sexe =(Spinner) findViewById(R.id.creersexe);
        taille =(EditText) findViewById(R.id.creertaille);
        poids =(EditText) findViewById(R.id.creerpoids);
        lieu =(EditText) findViewById(R.id.creerlieu);
        regime =(Spinner) findViewById(R.id.creerregime);
        Valider =(Button) findViewById(R.id.creervalider);
        Valider.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference();

        prefs = getSharedPreferences("Utilisateur", MODE_PRIVATE);

        ArrayAdapter <CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.item_sexe,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexe.setAdapter(adapter1);

        ArrayAdapter <CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.item_regime,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regime.setAdapter(adapter2);


    }

    /*private void checkUsername(final String user, final CheckUserCallback callback){
        mRef.child("usernames").child(user).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    callback.isTaken();
                }else {
                    callback.isValid(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }*/

    private void registerUser(final String mail,final String password,final String Age,final String Sexe,final String Taille,final String Poids,final String Lieu,final String Regime, final String authorisation){
        // [START create_user_with_email]

        String email = mail +"@myfoodadvisor.ca";
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "createUserWithEmail:success");
                            //FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            final String userId = task.getResult().getUser().getUid();
                            User newUser = new User(mail, userId, password,Age,Sexe,Taille,Poids,Lieu,Regime, authorisation);

                            mRef.child("users").child(mail).setValue(newUser).addOnCompleteListener(creationcompte.this, new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        ///mRef.child("username").child(user).setValue(userId);

                                        mRef.child("users").child(mail).child("regime").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                if(dataSnapshot.getValue()!=null)
                                                {

                                                    mRef.child(Regime).addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                            if (dataSnapshot.getValue() != null) {
                                                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                                                    String recette = postSnapshot.getValue().toString();
                                                                    Recettes.add(recette);
                                                                }
                                                                for(int i=0;i<14;i++)
                                                                {
                                                                    if(Recettes.size()!=0)
                                                                    {
                                                                        int index = (int) Math.random()*Recettes.size();
                                                                        menuSemaine.add(Recettes.get(index));
                                                                        Recettes.remove(index);
                                                                    }
                                                                    else
                                                                    {
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

                                        prefs.edit().putString("Pseudo/email", mail).apply();

                                        Intent i = new Intent(creationcompte.this, Acceuil.class);
                                        startActivity(i);
                                        finish();
                                    }
                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(creationcompte.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // [START_EXCLUDE]
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]

       /* mAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(creationcompte.this,"Connexion impossible",Toast.LENGTH_SHORT).show();
                }

                final String userId = task.getResult().getUser().getUid();
                //final String password = mp.getText().toString();

                checkUsername(user, new CheckUserCallback() {
                    @Override
                    public void isValid(final String user) {

                        User newUser = new User(user, userId, password);
                        mRef.child("users").child(userId).setValue(newUser).addOnCompleteListener(creationcompte.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    mRef.child("username").child(user).setValue(userId);
                                    prefs.edit().putString("Pseudo", user).apply();
                                    prefs.edit().putString("Password", password).apply();

                                    Intent i = new Intent(creationcompte.this, Acceuil.class);
                                    startActivity(i);
                                    finish();
                                }
                            }
                        });

                    }

                    @Override
                    public void isTaken() {
                        Toast.makeText(creationcompte.this,"Veuillez choisir un autre pseudo",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });*/
    }


    @Override
    public void onClick(View view) {
        String user = Pseudo.getText().toString();
        String password = mp.getText().toString();
        String Age = age.getText().toString();
        String Sexe = sexe.getSelectedItem().toString();
        String Taille =taille.getText().toString();
        String Poids = poids.getText().toString();
        String Lieu = lieu.getText().toString();
        String Regime = regime.getSelectedItem().toString();
        String authorisation = "oui";

        if(!TextUtils.isEmpty(user)&& !TextUtils.isEmpty(password)&& !TextUtils.isEmpty(age.getText().toString())&& !TextUtils.isEmpty(Sexe)&& !TextUtils.isEmpty(taille.getText().toString())&& !TextUtils.isEmpty(poids.getText().toString())&& !TextUtils.isEmpty(Lieu)&& !TextUtils.isEmpty(Regime)){
            registerUser(user,password,Age,Sexe,Taille,Poids,Lieu,Regime,authorisation);
        }
    }

    interface CheckUserCallback{
        void isValid(String user);
        void isTaken();
    }

}
