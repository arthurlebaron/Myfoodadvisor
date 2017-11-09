package com.myfoodadvisor.myfoodadvisor.Entities;

/**
 * Created by Utilisateur on 08/11/2017.
 */

public class User {
    public String username;
    public String id;
    public String mp;
    public String age;
    public String  sexe;
    public String taille;
    public String poids;
    public String lieu;
    public String  regime;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String id, String mp, String age, String sexe, String taille, String poids, String lieu, String regime) {
        this.username = username;
        this.id = id;
        this.mp = mp;
        this.age = age;
        this.sexe = sexe;
        this.taille = taille;
        this.poids = poids;
        this.lieu = lieu;
        this.regime = regime;

    }

    public String getUsername(){
        return username;
    }
    public String getId(){
        return id;
    }
    public String getMp(){return mp;}
    public  String getAge(){
        return  age;
    }
    public String getSexe(){
        return sexe;
    }
    public String getTaille(){
        return taille;
    }
    public String getPoids(){
        return poids;
    }
    public String getLieu(){
        return lieu;
    }
    public String getRegime(){
        return regime;
    }
}
