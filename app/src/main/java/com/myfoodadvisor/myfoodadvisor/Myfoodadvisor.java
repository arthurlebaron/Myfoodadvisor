package com.myfoodadvisor.myfoodadvisor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class Myfoodadvisor extends AppCompatActivity {
    CallbackManager callbackManager;
    LoginButton loginButton;
    Button valider;
    Button creation;
    TextView pseudo;
    TextView mp;

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


        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.login_button);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // Connection réussi
                String id = loginResult.getAccessToken().getUserId();
                Profile profile = Profile.getCurrentProfile();

                Intent i = new Intent(Myfoodadvisor.this, Acceuil.class);
                startActivity(i);

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


}

