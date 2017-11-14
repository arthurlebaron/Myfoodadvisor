package com.myfoodadvisor.myfoodadvisor.modification;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Utilisateur on 12/11/2017.
 */

public class modificationpoids extends Activity implements View.OnClickListener{
    private SharedPreferences prefs;
    private EditText valeur;
    private Button modifier;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modification_profil);
        prefs = getSharedPreferences("Utilisateur", MODE_PRIVATE);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.6),(int)(height*.2));

        valeur = (EditText) findViewById(R.id.valmodif);
        modifier = (Button) findViewById(R.id.btnmodifier);
        modifier.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference();

    }

    @Override
    public void onClick(View view) {
        String username = prefs.getString("Pseudo/email", null);
        if (valeur.getText().length() != 0){
            mRef.child("users").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null){
                        String username = prefs.getString("Pseudo/email", null);
                        String newage = dataSnapshot.child("age").getValue().toString();
                        String sexe = dataSnapshot.child("sexe").getValue().toString();
                        String taille = dataSnapshot.child("taille").getValue().toString();
                        String poids = valeur.getText().toString();
                        String lieu = dataSnapshot.child("lieu").getValue().toString();
                        String regime = dataSnapshot.child("regime").getValue().toString();
                        String userId = mAuth.getCurrentUser().getUid();
                        String password = dataSnapshot.child("mp").getValue().toString();
                        User newUser = new User(username, userId, password, newage, sexe, taille, poids, lieu, regime);
                        Map<String,Object> update = new HashMap<>();
                        update.put(username,newUser);
                        mRef.child("users").updateChildren(update).addOnCompleteListener(modificationpoids.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    finish();
                                }else {
                                    Toast.makeText(modificationpoids.this, "Erreur dans la modification.",
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
