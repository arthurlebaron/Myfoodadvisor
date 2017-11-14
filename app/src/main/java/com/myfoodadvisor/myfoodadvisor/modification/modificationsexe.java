package com.myfoodadvisor.myfoodadvisor.modification;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Utilisateur on 12/11/2017.
 */

public class modificationsexe extends Activity implements View.OnClickListener{
    private SharedPreferences prefs;
    private Spinner Sexe;
    private Button modifier;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;

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

        Sexe =(Spinner) findViewById(R.id.deroulmodif);
        modifier = (Button) findViewById(R.id.deroulbtnmodifier);
        modifier.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference();

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.item_sexe,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Sexe.setAdapter(adapter1);

    }

    @Override
    public void onClick(View view) {
        String username = prefs.getString("Pseudo/email", null);
        if (Sexe.getSelectedItem().toString() != "Faire un choix"){
            mRef.child("users").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null){
                        String username = prefs.getString("Pseudo/email", null);
                        String newage = dataSnapshot.child("age").getValue().toString();
                        String sexe = Sexe.getSelectedItem().toString();
                        String taille = dataSnapshot.child("taille").getValue().toString();
                        String poids = dataSnapshot.child("poids").getValue().toString();
                        String lieu = dataSnapshot.child("lieu").getValue().toString();
                        String regime = dataSnapshot.child("regime").getValue().toString();
                        String userId = mAuth.getCurrentUser().getUid();
                        String password = dataSnapshot.child("mp").getValue().toString();
                        User newUser = new User(username, userId, password, newage, sexe, taille, poids, lieu, regime);
                        Map<String,Object> update = new HashMap<>();
                        update.put(username,newUser);
                        mRef.child("users").updateChildren(update).addOnCompleteListener(modificationsexe.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    finish();
                                }else {
                                    Toast.makeText(modificationsexe.this, "Erreur dans la modification.",
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
        }else {
            finish();
        }
    }
}
