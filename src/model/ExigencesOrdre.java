/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.IOException;
import java.util.HashMap;
import net.sf.json.JSONArray;
import util.TraitementJSON;

/**
 *
 * @author palass
 */
public class ExigencesOrdre {
    
    private final String emplacementCategories = "json/listeCategories.json";
    private final String emplacementCycles = "json/listeCategories.json";
    
    private final JSONArray cyclesSupportes;
    private final JSONArray categoriesSupportes;
    private final JSONArray sousCategories;
    private final JSONArray listeTouteCategories;
    private String normePermis;
    private final String ordre;
    
    private final HashMap heuresMinParCategories;
    private final HashMap heuresMaxParCategories;
    
    private int heuresCyclePrecedent;
    private int heuresMinimum;

    ExigencesOrdre(Declaration declaration) throws IOException {
        
        this.ordre = declaration.getOrdre();
        listeTouteCategories = TraitementJSON.obtenirJsonObject(emplacementCategories).getJSONArray("categories");
        sousCategories = TraitementJSON.obtenirJsonObject(emplacementCategories).getJSONArray("sous-categories");
        categoriesSupportes = TraitementJSON.obtenirJsonObject(emplacementCategories).getJSONArray(ordre);
        cyclesSupportes = TraitementJSON.obtenirJsonObject(emplacementCycles).getJSONArray(ordre);
        heuresMinParCategories = TraitementJSON.getMapCategories();
        heuresMaxParCategories = TraitementJSON.getMapCategories();
        setNormePermis(declaration.getNom(), declaration.getPrenom());
        setHeuresMinimumParCategorie();
        setHeuresMaximumParCategorie();
        setHeuresMinimum(declaration.getCycle());
        setHeuresCyclePrecedent(declaration.getHeuresCyclePrecedent());
        heuresCyclePrecedent = 0;
    }

    private void setNormePermis(String nom, String prenom) {
        
         switch(ordre){
            case "architectes":
                normePermis = "[AT]\\d{4}";
                break;
            case "géologues":
                normePermis = nom.substring(0,1).toUpperCase() + prenom.substring(0,1).toUpperCase() + "\\d{4}";
                break;
            case "psychologues":
                normePermis = "\\d{5}-\\d{2}";
                break;
            case "podiatres":
                normePermis = "\\d{5}";
                break;  
        }
    }
    
    private void setHeuresMinimum(String cycle) {
        
        switch(ordre){
            case "architectes":
                heuresMinimum = 42;
                if(cycle.equals("2012-2014"))
                    heuresMinimum = 40;
                break;
            case "podiatres":
                heuresMinimum = 60;
                break;
            case "géologues":
                heuresMinimum = 55;
                break;
            case "psychologues":
                heuresMinimum = 90;
                break;
        }
    }
    
    private void setHeuresMinimumParCategorie() {
        
        switch(ordre){
            case "architectes":
                break;
            case "géologues":
            case "podiatres":
                getHeuresMinParCategories().put("cours", 22);
                getHeuresMinParCategories().put("projet de recherche", 3);
                getHeuresMinParCategories().put("groupe de discussion", 1);
                break;
            case "psychologues":
                getHeuresMinParCategories().put("cours", 25);
                break;
        }
    }
    
    private void setHeuresMaximumParCategorie() {
        
        switch(ordre){
            case "architectes":
                getHeuresMaxParCategories().put("présentation", 23);
                getHeuresMaxParCategories().put("groupe de discussion", 17);
                getHeuresMaxParCategories().put("projet de recherche", 23);
                getHeuresMaxParCategories().put("rédaction professionnelle", 17);
                break;
            case "podiatres":
                break;
            case "géologues":
                break;
            case "psychologues":
                getHeuresMaxParCategories().put("conférence", 15);
                break;
        }
    }
    
    private void setHeuresCyclePrecedent(int heures) {
        
        heuresCyclePrecedent = heures;
    }

    public JSONArray getCyclesSupportes() {
        return cyclesSupportes;
    }

    public JSONArray getCategoriesSupportes() {
        return categoriesSupportes;
    }

    public JSONArray getSousCategories() {
        return sousCategories;
    }

    public JSONArray getListeTouteCategories() {
        return listeTouteCategories;
    }

    public String getNormePermis() {
        return normePermis;
    }

    public String getOrdre() {
        return ordre;
    }
    
    public HashMap getHeuresMinParCategories() {
        return heuresMinParCategories;
    }

    public HashMap getHeuresMaxParCategories() {
        return heuresMaxParCategories;
    }

    public int getHeuresCyclePrecedent() {
        return heuresCyclePrecedent;
    }

    public int getHeuresMinimum() {
        return heuresMinimum;
    }

}
