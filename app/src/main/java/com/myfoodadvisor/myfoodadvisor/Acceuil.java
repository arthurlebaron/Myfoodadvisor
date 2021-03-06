package com.myfoodadvisor.myfoodadvisor;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import jp.wasabeef.picasso.transformations.CropSquareTransformation;


/**
 * Created by Palencar on 07/11/2017.
 */

public class Acceuil extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private PendingIntent pendingIntent;
    private SharedPreferences prefs;
    private String jour;
    private TextView repamidi;
    private TextView repasoir;
    private TextView tpsprepamidi;
    private TextView tpsprepasoir;
    private ImageView imagemidi;
    private ImageView imagesoir;


    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.setApplicationId("297204487433924");
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);

        prefs = getSharedPreferences("Utilisateur", MODE_PRIVATE);
        String username = prefs.getString("Pseudo/email", null);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference();

        repamidi =(TextView) findViewById(R.id.repamidi);
        repasoir =(TextView) findViewById(R.id.repasoir);
        tpsprepamidi =(TextView) findViewById(R.id.tpsprepamidi);
        tpsprepasoir =(TextView) findViewById(R.id.tpsprepasoir);
        imagemidi =(ImageView) findViewById(R.id.imagemidi);
        imagesoir =(ImageView) findViewById(R.id.imagesoir);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

       // alarmMethod(0,0,11,0); //11h00 AM
       // alarmMethod(0,0,6,1); //6h00 PM

        GregorianCalendar calendar =new GregorianCalendar();
        calendar.setTime(new Date());
        int today =calendar.get(calendar.DAY_OF_WEEK);

        switch (today) {
            case GregorianCalendar.MONDAY:
                //on est lundi
                jour = "lund";
                repamidi.setText(prefs.getString("lundi", "lasagne"));
                repasoir.setText(prefs.getString("lundi2", "riz"));
                if (prefs.getString("lundi", null) != null && prefs.getString("lundi2", null)!= null){
                    mRef.child("recettes").child(prefs.getString("lundi", null)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null){
                                tpsprepamidi.setText(dataSnapshot.child("temps_cui").getValue().toString() + " min");
                                //temps_prep:
                                affiche_image(prefs.getString("lundi",null),imagemidi);
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                    mRef.child("recettes").child(prefs.getString("lundi2", null)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null){
                                tpsprepasoir.setText(dataSnapshot.child("temps_cui").getValue().toString() + " min");
                                //temps_prep:
                                affiche_image(prefs.getString("lundi2",null),imagesoir);                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
                break;
            case GregorianCalendar.TUESDAY:
                //on est mardi
                jour = "mardi";
                repamidi.setText(prefs.getString("mardi", null));
                repasoir.setText(prefs.getString("mardi2", null));
                if (prefs.getString("mardi", null) != null && prefs.getString("mardi2", null)!= null){
                    mRef.child("recettes").child(prefs.getString("mardi", null)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.d("DEV",dataSnapshot.toString());
                            if (dataSnapshot.getValue() != null){
                                tpsprepamidi.setText("cuisson: " + dataSnapshot.child("temps_cui").getValue().toString() + " min");
                                affiche_image(prefs.getString("mardi",null),imagemidi);                         }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                    mRef.child("recettes").child(prefs.getString("mardi2", null)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null){
                                tpsprepasoir.setText("cuisson: " + dataSnapshot.child("temps_cui").getValue().toString() + " min");
                                affiche_image(prefs.getString("mardi2",null),imagesoir);
                                 }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
                break;
            case GregorianCalendar.WEDNESDAY:
                //on est mardi
                jour = "mercredi";
                repamidi.setText(prefs.getString("mercredi", null));
                repasoir.setText(prefs.getString("mercredi2", null));
                if (prefs.getString("mercredi", null) != null && prefs.getString("mercredi2", null)!= null){
                    mRef.child("recettes").child(prefs.getString("mercredi", null)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null){
                                tpsprepamidi.setText(dataSnapshot.child("temps_cui").getValue().toString() + " min");
                                //temps_prep:
                                affiche_image(prefs.getString("mercredi",null),imagemidi);                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                    mRef.child("recettes").child(prefs.getString("mercredi2", null)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null){
                                tpsprepasoir.setText(dataSnapshot.child("temps_cui").getValue().toString()  + " min");
                                //temps_prep:
                                affiche_image(prefs.getString("mercredi2",null),imagesoir);                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
                break;
            case GregorianCalendar.THURSDAY:
                //on est mardi
                jour = "jeudi";
                repamidi.setText(prefs.getString("jeudi", null));
                repasoir.setText(prefs.getString("jeudi2", null));
                if (prefs.getString("jeudi", null) != null && prefs.getString("jeudi2", null)!= null){
                    mRef.child("recettes").child(prefs.getString("jeudi", null)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null){
                                tpsprepamidi.setText(dataSnapshot.child("temps_cui").getValue().toString() + " min");
                                //temps_prep:
                                affiche_image(prefs.getString("jeudi",null),imagemidi);                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                    mRef.child("recettes").child(prefs.getString("jeudi2", null)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null){
                                tpsprepasoir.setText(dataSnapshot.child("temps_cui").getValue().toString() + " min");
                                //temps_prep:
                                affiche_image(prefs.getString("jeudi2",null),imagesoir);                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
                break;
            case GregorianCalendar.FRIDAY:
                //on est mardi
                jour = "vendredi";
                repamidi.setText(prefs.getString("vendredi", null));
                repasoir.setText(prefs.getString("vendredi2", null));
                if (prefs.getString("mercredi", null) != null && prefs.getString("mercredi2", null)!= null){
                    mRef.child("recettes").child(prefs.getString("vendredi", null)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null){
                                tpsprepamidi.setText(dataSnapshot.child("temps_cui").getValue().toString() + " min");
                                //temps_prep:
                                affiche_image(prefs.getString("vendredi",null),imagemidi);                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                    mRef.child("recettes").child(prefs.getString("vendredi2", null)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null){
                                tpsprepasoir.setText(dataSnapshot.child("temps_cui").getValue().toString() + " min");
                                //temps_prep:
                                affiche_image(prefs.getString("vendredi2",null),imagesoir);                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
                break;
            case GregorianCalendar.SATURDAY:
                //on est mardi
                jour = "samedi";
                repamidi.setText(prefs.getString("samedi", null));
                repasoir.setText(prefs.getString("samedi2", null));
                if (prefs.getString("samedi", null) != null && prefs.getString("samedi2", null)!= null){
                    mRef.child("recettes").child(prefs.getString("samedi", null)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null){
                                tpsprepamidi.setText(dataSnapshot.child("temps_cui").getValue().toString() + " min");
                                //temps_prep:
                                affiche_image(prefs.getString("samedi",null),imagemidi);                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                    mRef.child("recettes").child(prefs.getString("samedi2", null)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null){
                                tpsprepasoir.setText(dataSnapshot.child("temps_cui").getValue().toString() + " min");
                                //temps_prep:
                                affiche_image(prefs.getString("samedi2",null),imagesoir);                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
                break;
            case GregorianCalendar.SUNDAY:
                //on est mardi
                jour = "dimanche";
                repamidi.setText(prefs.getString("dimanche", null));
                repasoir.setText(prefs.getString("dimanche2", null));
                if (prefs.getString("dimanche", null) != null && prefs.getString("dimanche2", null)!= null){
                    mRef.child("recettes").child(prefs.getString("dimanche", null)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null){
                                tpsprepamidi.setText(dataSnapshot.child("temps_cui").getValue().toString() + " min");
                                //temps_prep:
                                affiche_image(prefs.getString("dimanche",null),imagemidi);                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                    mRef.child("recettes").child(prefs.getString("dimanche2", null)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null){
                                tpsprepasoir.setText(dataSnapshot.child("temps_cui").getValue().toString()+ " min");
                                //temps_prep:
                                affiche_image(prefs.getString("dimanche2",null),imagesoir);                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                    break;
                }
            ///etc etc

            default:
                //ça devrait pas erreur
                break;
        }
    }


    @Override
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
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_accueil) {
            Intent i = new Intent(Acceuil.this, Acceuil.class);
            startActivity(i);

        } else if (id == R.id.nav_menu_semaine) {
            Intent i = new Intent(Acceuil.this, menuSemaine.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_profil) {
            Intent i = new Intent(Acceuil.this, mon_profil.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_deco) {
            prefs.edit().putString("facebook", "0").apply();
            mAuth.signOut();
            LoginManager.getInstance().logOut();
            finish();
            Intent i = new Intent(Acceuil.this, Myfoodadvisor.class);
            startActivity(i);
        } else if (id == R.id.nav_proposition) {
            String username = prefs.getString("Pseudo/email", null);
            mRef.child("users").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null){
                        String autho = dataSnapshot.child("authorisation").getValue().toString();
                        if (autho.equals("oui")){
                            Intent i = new Intent(Acceuil.this, proposition.class);
                            startActivity(i);
                            finish();
                        }else if (autho.equals("non")){
                            Intent i = new Intent(Acceuil.this, nonauth.class);
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
            Intent i = new Intent(Acceuil.this, MapsActivityCurrentPlace.class);
            startActivity(i);
            finish();
        }
        else if (id == R.id.nav_courses) {
            Intent i = new Intent(Acceuil.this, coursesSemaine.class);
            startActivity(i);
            finish();
        }

       // DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void alarmMethod(int s, int m, int h, int PAM){
        Calendar calendar = Calendar.getInstance();
        Intent myIntent = new Intent(this , NotificationService.class);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        pendingIntent = PendingIntent.getService(this, 0, myIntent, 0);

        calendar.set(Calendar.SECOND, s);
        calendar.set(Calendar.MINUTE, m);
        calendar.set(Calendar.HOUR, h);
        calendar.set(Calendar.AM_PM, PAM);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 60 * 24, pendingIntent);
        Log.d("D","alarmMethod");

        String name="boeuf bourguinon";

    }

    private void affiche_image (String nom, ImageView image){
        switch (nom){
            case "boeuf bourgignon":
                Picasso.with(getBaseContext()).load(R.drawable.boeuf_bourgignon).transform(new CropSquareTransformation()).into(image);
                break;
            case "boudin noir aux pommes":
                Picasso.with(getBaseContext()).load(R.drawable.boudin_noir_aux_pommes).transform(new CropSquareTransformation()).into(image);
                break;
            case "cassoulet":
                Picasso.with(getBaseContext()).load(R.drawable.cassoulet).transform(new CropSquareTransformation()).into(image);
                break;
            case "boulettes de viande":
                Picasso.with(getBaseContext()).load(R.drawable.boulettes_de_viande).transform(new CropSquareTransformation()).into(image);
                break;
            case "cordon bleu maison":
                Picasso.with(getBaseContext()).load(R.drawable.cordon_bleu_maison).transform(new CropSquareTransformation()).into(image);
                break;
            case "gratin de pates":
                Picasso.with(getBaseContext()).load(R.drawable.gratin_de_pates).transform(new CropSquareTransformation()).into(image);
                break;
            case "kebab":
                Picasso.with(getBaseContext()).load(R.drawable.kebab).transform(new CropSquareTransformation()).into(image);
                break;
            case "mcdo":
                Picasso.with(getBaseContext()).load(R.drawable.mcdo).transform(new CropSquareTransformation()).into(image);
                break;
            case "nems":
                Picasso.with(getBaseContext()).load(R.drawable.nems).transform(new CropSquareTransformation()).into(image);
                break;
            case "nouilles":
                Picasso.with(getBaseContext()).load(R.drawable.nouilles).transform(new CropSquareTransformation()).into(image);
                break;
            case "pates carbonara":
                Picasso.with(getBaseContext()).load(R.drawable.pates_carbonara).transform(new CropSquareTransformation()).into(image);
                break;
            case "pizza":
                Picasso.with(getBaseContext()).load(R.drawable.pizza).transform(new CropSquareTransformation()).into(image);
                break;
            case "poisson blanc riz":
                Picasso.with(getBaseContext()).load(R.drawable.poisson_blanc_riz).transform(new CropSquareTransformation()).into(image);
                break;
            case "poulet frites":
                Picasso.with(getBaseContext()).load(R.drawable.poulet_frites).transform(new CropSquareTransformation()).into(image);
                break;
            case "raclette":
                Picasso.with(getBaseContext()).load(R.drawable.raclette).transform(new CropSquareTransformation()).into(image);
                break;
            case "raviolis vapeur":
                Picasso.with(getBaseContext()).load(R.drawable.raviolis_vapeur).transform(new CropSquareTransformation()).into(image);
                break;
            case "risotto":
                Picasso.with(getBaseContext()).load(R.drawable.risotto).transform(new CropSquareTransformation()).into(image);
                break;
            case "riz dinde":
                Picasso.with(getBaseContext()).load(R.drawable.riz_dinde).transform(new CropSquareTransformation()).into(image);
                break;
            case "salade concombre feta":
                Picasso.with(getBaseContext()).load(R.drawable.salade_concombre_feta).transform(new CropSquareTransformation()).into(image);
                break;
            case "salade de choux":
                Picasso.with(getBaseContext()).load(R.drawable.salade_de_choux).transform(new CropSquareTransformation()).into(image);
                break;
            case "salade quinoa":
                Picasso.with(getBaseContext()).load(R.drawable.salade_quinoa).transform(new CropSquareTransformation()).into(image);
                break;
            case "sandwich club":
                Picasso.with(getBaseContext()).load(R.drawable.sandwich_club).transform(new CropSquareTransformation()).into(image);
                break;
            case "soupe minestrone":
                Picasso.with(getBaseContext()).load(R.drawable.soupe_minestrone).transform(new CropSquareTransformation()).into(image);
                break;
            case "sushi":
                Picasso.with(getBaseContext()).load(R.drawable.sushi).transform(new CropSquareTransformation()).into(image);
                break;
            case "tartiflette":
                Picasso.with(getBaseContext()).load(R.drawable.tartiflette).transform(new CropSquareTransformation()).into(image);
                break;
            case "fajitas":
                Picasso.with(getBaseContext()).load(R.drawable.fajitas).transform(new CropSquareTransformation()).into(image);
                break;
                default:
                    Picasso.with(getBaseContext()).load(R.drawable.defaut_img).transform(new CropSquareTransformation()).into(image);
        }
    }
}


