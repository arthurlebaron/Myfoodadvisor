package com.myfoodadvisor.myfoodadvisor.Entities;

/**
 * Created by arthu on 03/12/2017.
 */

public class Proposition {
    public String nom_recette;
    public String description;

    public Proposition() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public  Proposition(String nom_recette, String description){
        this.nom_recette = nom_recette;
        this.description = description;
    }
    public String getNom_recette(){
        return nom_recette;
    }
    public String getDescription(){
        return description;
    }
}
