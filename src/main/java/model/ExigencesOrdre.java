
package model;

import java.io.IOException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.TraitementJSON;

/**
 *
 * @author palass
 */
public class ExigencesOrdre {
    
    private final String emplacementCycles = "json/cycles.json";
    
    private final JSONArray cyclesSupportes;
    private final JSONObject categories;
    private final JSONArray sousCategories;
    private String normePermis;
    private final String ordre;
    
    private final JSONObject heuresMinParCategories;
    private final JSONObject heuresMaxParCategories;
    
    private int heuresCyclePrecedent;
    private int heuresMinimum;

    ExigencesOrdre(Declaration declaration) throws IOException {
        
        this.ordre = declaration.getOrdre();
        sousCategories = TraitementJSON.obtenirJSONObject("json/categories.json").getJSONArray("sous-catégories");
        categories = TraitementJSON.obtenirTabCategories();
        cyclesSupportes = TraitementJSON.obtenirJSONObject(emplacementCycles).getJSONArray(ordre);
        heuresMinParCategories = TraitementJSON.obtenirTabCategories();
        heuresMaxParCategories = TraitementJSON.obtenirTabCategories();
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

    public JSONObject getCategories() {
        return categories;
    }

    public JSONArray getSousCategories() {
        return sousCategories;
    }

    public String getNormePermis() {
        return normePermis;
    }

    public String getOrdre() {
        return ordre;
    }
    
    public JSONObject getHeuresMinParCategories() {
        return heuresMinParCategories;
    }

    public JSONObject getHeuresMaxParCategories() {
        return heuresMaxParCategories;
    }

    public int getHeuresCyclePrecedent() {
        return heuresCyclePrecedent;
    }

    public int getHeuresMinimum() {
        return heuresMinimum;
    }

}
