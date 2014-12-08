
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
    
    private int heuresMinimumRequis;

    ExigencesOrdre (Declaration declaration) throws IOException {
        
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
    }

    private void setNormePermis (String nom, String prenom) {
        
         switch (ordre) {
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
    
    private void setHeuresMinimum (String cycle) {
        
        switch (ordre) {
            case "architectes":
                heuresMinimumRequis = 42;
                if (cycle.equals("2012-2014"))
                    heuresMinimumRequis = 40;
                break;
            case "podiatres":
                heuresMinimumRequis = 60;
                break;
            case "géologues":
                heuresMinimumRequis = 55;
                break;
            case "psychologues":
                heuresMinimumRequis = 90;
                break;
        }
    }
    
    private void setHeuresMinimumParCategorie () {
        
        switch (ordre) {
            case "architectes":
                heuresMinParCategories.put("sous-catégories", 17);
                break;
            case "géologues":
            case "podiatres":
                heuresMinParCategories.put("cours", 22);
                heuresMinParCategories.put("projet de recherche", 3);
                heuresMinParCategories.put("groupe de discussion", 1);
                break;
            case "psychologues":
                heuresMinParCategories.put("cours", 25);
                break;
        }
    }
    
    private void setHeuresMaximumParCategorie () {
        
        switch (ordre) {
            case "architectes":
                heuresMaxParCategories.put("présentation", 23);
                heuresMaxParCategories.put("groupe de discussion", 17);
                heuresMaxParCategories.put("projet de recherche", 23);
                heuresMaxParCategories.put("rédaction professionnelle", 17);
                break;
            case "podiatres":
                break;
            case "géologues":
                break;
            case "psychologues":
                heuresMaxParCategories.put("conférence", 15);
                break;
        }
    }
    
    public JSONArray getCyclesSupportes () {
        return cyclesSupportes;
    }

    public JSONObject getCategories () {
        return categories;
    }

    public JSONArray getSousCategories () {
        return sousCategories;
    }

    public String getNormePermis () {
        return normePermis;
    }

    public String getOrdre () {
        return ordre;
    }
    
    public JSONObject getHeuresMinParCategories () {
        return heuresMinParCategories;
    }

    public JSONObject getHeuresMaxParCategories () {
        return heuresMaxParCategories;
    }

    public int getHeuresMinimum () {
        return heuresMinimumRequis;
    }

}
