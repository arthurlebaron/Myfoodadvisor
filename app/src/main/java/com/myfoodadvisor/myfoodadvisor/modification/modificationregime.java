package com.myfoodadvisor.myfoodadvisor.modification;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myfoodadvisor.myfoodadvisor.Entities.User;
import com.myfoodadvisor.myfoodadvisor.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Utilisateur on 12/11/2017.
 */

public class modificationregime extends Activity implements View.OnClickListener{
    private SharedPreferences prefs;
    private Spinner Regime;
    private Button modifier;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private SharedPreferences prefs_bis;
    List<String> menuSemaine = new ArrayList<String>();
    List<String> Recettes = new ArrayList<String>();
    String jours[] = new String[14];


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modification_profil_deroulant);
        prefs = getSharedPreferences("Utilisateur", MODE_PRIVATE);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.6),(int)(height*.2));

        Regime =(Spinner) findViewById(R.id.deroulmodif);
        modifier = (Button) findViewById(R.id.deroulbtnmodifier);
        modifier.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference();
        jours[0]="lundi";

        jours[1]="lundi2";
        jours[2]="mardi";
        jours[3]="mardi2";
        jours[4]="mercredi";
        jours[5]="mercredi2";
        jours[6]="jeudi";
        jours[7]="jeudi2";
        jours[8]="vendredi";
        jours[9]="vendredi2";
        jours[10]="samedi";
        jours[11]="samedi2";
        jours[12]="dimanche";
        jours[13]="dimanche2";

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.item_regime,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Regime.setAdapter(adapter1);

    }
    int t=0;
    List<String> tmp = new ArrayList<String>();
    private void putSharedCourses()
    {
        for(t=0;t<=13;t++) {
            tmp.clear();
            final int k=t;
                mRef.child("recettes").child(menuSemaine.get(k)).child("ingredient1").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            tmp.add(dataSnapshot.getValue().toString());

                            mRef.child("recettes").child(menuSemaine.get(k)).child("ingredient2").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getValue() != null) {

                                        tmp.add(dataSnapshot.getValue().toString());
                                        mRef.child("recettes").child(menuSemaine.get(k)).child("ingredient3").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.getValue() != null) {

                                                    tmp.add(dataSnapshot.getValue().toString());
                                                    mRef.child("recettes").child(menuSemaine.get(k)).child("ingredient4").addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                            if (dataSnapshot.getValue() != null) {
                                                                prefs_bis = getSharedPreferences(jours[k], MODE_PRIVATE);
                                                                prefs_bis.edit().putString("ingredient1", tmp.get(0)).apply();
                                                                prefs_bis.edit().putString("ingredient2",  tmp.get(1)).apply();
                                                                prefs_bis.edit().putString("ingredient3",  tmp.get(2)).apply();
                                                                prefs_bis.edit().putString("ingredient4", dataSnapshot.getValue().toString()).apply();
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
    }

    @Override
    public void onClick(View view) {
        String username = prefs.getString("Pseudo/email", null);
        if (Regime.getSelectedItem().toString() != "Faire un choix"){
            mRef.child("users").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null){
                        final String username = prefs.getString("Pseudo/email", null);
                        String newage = dataSnapshot.child("age").getValue().toString();
                        String sexe = dataSnapshot.child("sexe").getValue().toString();
                        String taille = dataSnapshot.child("taille").getValue().toString();
                        String poids = dataSnapshot.child("poids").getValue().toString();
                        String lieu = dataSnapshot.child("lieu").getValue().toString();
                        String regime = Regime.getSelectedItem().toString();
                        String userId = mAuth.getCurrentUser().getUid();
                        String password = dataSnapshot.child("mp").getValue().toString();
                        String authorisation = dataSnapshot.child("authorisation").getValue().toString();
                        User newUser = new User(username, userId, password, newage, sexe, taille, poids, lieu, regime,authorisation);
                        Map<String,Object> update = new HashMap<>();
                        update.put(username,newUser);
                        mRef.child("users").updateChildren(update).addOnCompleteListener(modificationregime.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){

                                                mDatabase.getReference().child(Regime.getSelectedItem().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                            putSharedCourses();
                                                        }
                                                    }
                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {
                                                    }
                                                });

                                    finish();
                                }else {
                                    Toast.makeText(modificationregime.this, "Erreur dans la modification.",
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
        }else{
            finish();
        }
    }
}


