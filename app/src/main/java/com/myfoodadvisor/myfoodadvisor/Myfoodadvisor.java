package com.myfoodadvisor.myfoodadvisor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Myfoodadvisor extends AppCompatActivity {
    CallbackManager callbackManager;
    LoginButton loginButton;
    Button valider;
    Button creation;
    TextView pseudo;
    TextView mp;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.setApplicationId("297204487433924");
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_myfoodadvisor);

        valider = (Button) findViewById(R.id.valider);
        creation =  (Button) findViewById(R.id.creation);
        pseudo = (TextView) findViewById(R.id.pseudo);
        mp = (TextView) findViewById(R.id.mp);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference();

        prefs = getSharedPreferences("Utilisateur", MODE_PRIVATE);

        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // Connection réussi
                //String id = loginResult.getAccessToken().getUserId();
                handleFacebookAccessToken(loginResult.getAccessToken());
                Toast.makeText(Myfoodadvisor.this, "Wait Please.",
                        Toast.LENGTH_LONG).show();
            }
            @Override
            public void onCancel() {
                // Connection annulée
            }
            @Override
            public void onError(FacebookException e) {
                // Erreur de connexion
            }
        });
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String finalpseudo = pseudo.getText().toString();
                String finalmp = mp.getText().toString();
                Log.d("variable",finalmp);
                if(!TextUtils.isEmpty(finalpseudo)&& !TextUtils.isEmpty(finalmp)){
                    checkConnexion(finalpseudo,finalmp);
                }
            }
        });
        creation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Myfoodadvisor.this, creationcompte.class);
                startActivity(i);

            }
        });
    }
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            Intent i = new Intent(Myfoodadvisor.this, Acceuil.class);
            startActivity(i);
            finish();
        }


    }

    // [START auth_with_facebook]
    private void handleFacebookAccessToken(AccessToken token) {
        //Log.d(TAG, "handleFacebookAccessToken:" + token);
        // [START_EXCLUDE silent]
       // showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Profile profile = Profile.getCurrentProfile();
                            String username = profile.getFirstName() + profile.getLastName();
                            mRef.child("users").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getValue()!= null){

                                        Profile profile = Profile.getCurrentProfile();
                                        String username = profile.getFirstName() + profile.getLastName();
                                        prefs.edit().putString("Pseudo/email", username).apply();
                                        prefs.edit().putString("facebook", "1").apply();
                                        Intent i = new Intent(Myfoodadvisor.this, Acceuil.class);
                                        startActivity(i);

                                    }else{
                                        Intent i = new Intent(Myfoodadvisor.this, creationfacebook.class);
                                        startActivity(i);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                        } else {
                            // If sign in fails, display a message to the user.
                           // Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(Myfoodadvisor.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // [START_EXCLUDE]
                       // hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_facebook]

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void checkConnexion(final String username, final String password){
        String email = username +"@myfoodadvisor.ca";
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithEmail:success");
                            //FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);

                            prefs.edit().putString("Pseudo/email", username).apply();

                            Intent i = new Intent(Myfoodadvisor.this, Acceuil.class);
                            startActivity(i);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Myfoodadvisor.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
       /* mAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mRef.child("usernames").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue() != null){
                            //callback.isTaken();
                            String ID = dataSnapshot.getValue().toString();
                            Log.d("variable",ID);
                            mRef.child("users").child(ID).child("mp").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.getValue() != null){
                                        String MP = dataSnapshot.getValue().toString();
                                        Log.d("variable",MP);
                                        if ( MP.equals(password)){
                                            Intent i = new Intent(Myfoodadvisor.this, Acceuil.class);
                                            startActivity(i);
                                        }else{
                                            Toast.makeText(Myfoodadvisor.this,"Erreur de password",Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }else {
                            //callback.isValid(user);
                            Toast.makeText(Myfoodadvisor.this,"Erreur de pseudo",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });*/

    }


}

