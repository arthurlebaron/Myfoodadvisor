package com.myfoodadvisor.myfoodadvisor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myfoodadvisor.myfoodadvisor.Entities.User;

import java.util.ArrayList;
import java.util.List;


public class menuSemaine extends AppCompatActivity  {


    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;

    private SharedPreferences prefs;

    private TextView menus[] = new TextView[14];
    private String regimeUser;
    private List<String> listRecettes = new ArrayList<String>();
    private TextView test;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_semaine);

        mAuth = FirebaseAuth.getInstance();
        prefs = getSharedPreferences("Utilisateur", MODE_PRIVATE);

        String pseudo = prefs.getString("Pseudo/email",null);
        mDatabase= FirebaseDatabase.getInstance();

        menus[0] = (TextView) findViewById(R.id.menuLundi);
        menus[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.getReference().child("recettes").child(menus[0].getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot==null | dataSnapshot.getValue()==null)
                            Toast.makeText(getApplicationContext(),"Pas encore de recette pour celle ci", Toast.LENGTH_LONG).show();
                        else {
                            prefs = getSharedPreferences("Utilisateur", MODE_PRIVATE);
                            prefs.edit().putString("Recette", menus[0].getText().toString()).apply();
                            Toast.makeText(getApplicationContext(), "Chargement recette" + menus[0].getText().toString(), Toast.LENGTH_LONG).show();
                            //Ajouter le chargement de la nouvelle View
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }
        });
        menus[1] = (TextView) findViewById(R.id.menuLundi2);
        menus[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.getReference().child("recettes").child(menus[1].getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot==null | dataSnapshot.getValue()==null)
                            Toast.makeText(getApplicationContext(),"Pas encore de recette pour celle ci", Toast.LENGTH_LONG).show();
                        else {
                            prefs = getSharedPreferences("Utilisateur", MODE_PRIVATE);
                            prefs.edit().putString("Recette", menus[1].getText().toString()).apply();
                            Toast.makeText(getApplicationContext(), "Chargement recette" + menus[1].getText().toString(), Toast.LENGTH_LONG).show();
                            //Ajouter le chargement de la nouvelle View
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }
        });
        menus[2] = (TextView) findViewById(R.id.menuMardi);
        menus[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.getReference().child("recettes").child(menus[2].getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot==null | dataSnapshot.getValue()==null)
                            Toast.makeText(getApplicationContext(),"Pas encore de recette pour celle ci", Toast.LENGTH_LONG).show();
                        else {
                            prefs = getSharedPreferences("Utilisateur", MODE_PRIVATE);
                            prefs.edit().putString("Recette", menus[2].getText().toString()).apply();
                            Toast.makeText(getApplicationContext(), "Chargement recette" + menus[2].getText().toString(), Toast.LENGTH_LONG).show();
                            //Ajouter le chargement de la nouvelle View
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }
        });
        menus[3] = (TextView) findViewById(R.id.menuMardi2);
        menus[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.getReference().child("recettes").child(menus[3].getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot==null | dataSnapshot.getValue()==null)
                            Toast.makeText(getApplicationContext(),"Pas encore de recette pour celle ci", Toast.LENGTH_LONG).show();
                        else {
                            prefs = getSharedPreferences("Utilisateur", MODE_PRIVATE);
                            prefs.edit().putString("Recette", menus[3].getText().toString()).apply();
                            Toast.makeText(getApplicationContext(), "Chargement recette" + menus[3].getText().toString(), Toast.LENGTH_LONG).show();
                            //Ajouter le chargement de la nouvelle View
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }
        });

        menus[4] = (TextView) findViewById(R.id.menuMerc);
        menus[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.getReference().child("recettes").child(menus[4].getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot==null | dataSnapshot.getValue()==null)
                            Toast.makeText(getApplicationContext(),"Pas encore de recette pour celle ci", Toast.LENGTH_LONG).show();
                        else {
                            prefs = getSharedPreferences("Utilisateur", MODE_PRIVATE);
                            prefs.edit().putString("Recette", menus[4].getText().toString()).apply();
                            Toast.makeText(getApplicationContext(), "Chargement recette" + menus[4].getText().toString(), Toast.LENGTH_LONG).show();
                            //Ajouter le chargement de la nouvelle View
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }
        });
        menus[5] = (TextView) findViewById(R.id.menuMerc2);
        menus[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.getReference().child("recettes").child(menus[5].getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot==null | dataSnapshot.getValue()==null)
                            Toast.makeText(getApplicationContext(),"Pas encore de recette pour celle ci", Toast.LENGTH_LONG).show();
                        else {
                            prefs = getSharedPreferences("Utilisateur", MODE_PRIVATE);
                            prefs.edit().putString("Recette", menus[5].getText().toString()).apply();
                            Toast.makeText(getApplicationContext(), "Chargement recette" + menus[5].getText().toString(), Toast.LENGTH_LONG).show();
                            //Ajouter le chargement de la nouvelle View
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }
        });

        menus[6] = (TextView) findViewById(R.id.menuJeudi);
        menus[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.getReference().child("recettes").child(menus[6].getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot==null | dataSnapshot.getValue()==null)
                            Toast.makeText(getApplicationContext(),"Pas encore de recette pour celle ci", Toast.LENGTH_LONG).show();
                        else {
                            prefs = getSharedPreferences("Utilisateur", MODE_PRIVATE);
                            prefs.edit().putString("Recette", menus[6].getText().toString()).apply();
                            Toast.makeText(getApplicationContext(), "Chargement recette" + menus[6].getText().toString(), Toast.LENGTH_LONG).show();
                            //Ajouter le chargement de la nouvelle View
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }
        });
        menus[7] = (TextView) findViewById(R.id.menuJeudi2);
        menus[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.getReference().child("recettes").child(menus[7].getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot==null | dataSnapshot.getValue()==null)
                            Toast.makeText(getApplicationContext(),"Pas encore de recette pour celle ci", Toast.LENGTH_LONG).show();
                        else {
                            prefs = getSharedPreferences("Utilisateur", MODE_PRIVATE);
                            prefs.edit().putString("Recette", menus[7].getText().toString()).apply();
                            Toast.makeText(getApplicationContext(), "Chargement recette" + menus[7].getText().toString(), Toast.LENGTH_LONG).show();
                            //Ajouter le chargement de la nouvelle View
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }
        });

        menus[8] = (TextView) findViewById(R.id.menuVendr);
        menus[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.getReference().child("recettes").child(menus[8].getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot==null | dataSnapshot.getValue()==null)
                            Toast.makeText(getApplicationContext(),"Pas encore de recette pour celle ci", Toast.LENGTH_LONG).show();
                        else {
                            prefs = getSharedPreferences("Utilisateur", MODE_PRIVATE);
                            prefs.edit().putString("Recette", menus[8].getText().toString()).apply();
                            Toast.makeText(getApplicationContext(), "Chargement recette" + menus[8].getText().toString(), Toast.LENGTH_LONG).show();
                            //Ajouter le chargement de la nouvelle View
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }
        });
        menus[9] = (TextView) findViewById(R.id.menuVendr2);
        menus[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.getReference().child("recettes").child(menus[9].getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot==null | dataSnapshot.getValue()==null)
                            Toast.makeText(getApplicationContext(),"Pas encore de recette pour celle ci", Toast.LENGTH_LONG).show();
                        else {
                            prefs = getSharedPreferences("Utilisateur", MODE_PRIVATE);
                            prefs.edit().putString("Recette", menus[9].getText().toString()).apply();
                            Toast.makeText(getApplicationContext(), "Chargement recette" + menus[9].getText().toString(), Toast.LENGTH_LONG).show();
                            //Ajouter le chargement de la nouvelle View
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }
        });

        menus[10] = (TextView) findViewById(R.id.menuSam);
        menus[10].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.getReference().child("recettes").child(menus[10].getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot==null | dataSnapshot.getValue()==null)
                            Toast.makeText(getApplicationContext(),"Pas encore de recette pour celle ci", Toast.LENGTH_LONG).show();
                        else {
                            prefs = getSharedPreferences("Utilisateur", MODE_PRIVATE);
                            prefs.edit().putString("Recette", menus[10].getText().toString()).apply();
                            Toast.makeText(getApplicationContext(), "Chargement recette" + menus[10].getText().toString(), Toast.LENGTH_LONG).show();
                            //Ajouter le chargement de la nouvelle View
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }
        });
        menus[11] = (TextView) findViewById(R.id.menuSam2);
        menus[11].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.getReference().child("recettes").child(menus[11].getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot==null | dataSnapshot.getValue()==null)
                            Toast.makeText(getApplicationContext(),"Pas encore de recette pour celle ci", Toast.LENGTH_LONG).show();
                        else {
                            prefs = getSharedPreferences("Utilisateur", MODE_PRIVATE);
                            prefs.edit().putString("Recette", menus[11].getText().toString()).apply();
                            Toast.makeText(getApplicationContext(), "Chargement recette" + menus[11].getText().toString(), Toast.LENGTH_LONG).show();
                            //Ajouter le chargement de la nouvelle View
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }
        });

        menus[12] = (TextView) findViewById(R.id.menuDim);
        menus[12].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.getReference().child("recettes").child(menus[12].getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot==null | dataSnapshot.getValue()==null)
                            Toast.makeText(getApplicationContext(),"Pas encore de recette pour celle ci", Toast.LENGTH_LONG).show();
                        else {
                            prefs = getSharedPreferences("Utilisateur", MODE_PRIVATE);
                            prefs.edit().putString("Recette", menus[12].getText().toString()).apply();
                            Toast.makeText(getApplicationContext(), "Chargement recette" + menus[12].getText().toString(), Toast.LENGTH_LONG).show();
                            //Ajouter le chargement de la nouvelle View
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }
        });
        menus[13] = (TextView) findViewById(R.id.menuDim2);
        menus[13].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.getReference().child("recettes").child(menus[13].getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot==null | dataSnapshot.getValue()==null)
                            Toast.makeText(getApplicationContext(),"Pas encore de recette pour celle ci", Toast.LENGTH_LONG).show();
                        else {
                            prefs = getSharedPreferences("Utilisateur", MODE_PRIVATE);
                            prefs.edit().putString("Recette", menus[13].getText().toString()).apply();
                            Toast.makeText(getApplicationContext(), "Chargement recette" + menus[13].getText().toString(), Toast.LENGTH_LONG).show();
                            //Ajouter le chargement de la nouvelle View
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }
        });



        mDatabase.getReference().child("users").child(pseudo).child("regime").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null)
                {
                    regimeUser = dataSnapshot.getValue().toString();
                    mDatabase.getReference().child(regimeUser).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null) {
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    String recette = postSnapshot.getValue().toString();
                                    listRecettes.add(recette);
                                }
                            }
                            for(TextView menu : menus)
                            {

                                if(listRecettes.size()!=0)
                                {
                                    int index = (int) Math.random()*listRecettes.size();
                                    menu.setText(listRecettes.get(index));
                                    listRecettes.remove(index);
                                }
                                else
                                {
                                    menu.setText("Plus de recette");
                                }
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
