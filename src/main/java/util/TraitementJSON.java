/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.FileWriter;
import java.io.IOException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import model.Resultat;

/**
 *
 * @author palass
 */
public class TraitementJSON {
    
    public static  JSONObject obtenirJSONObject (String emplacement) throws IOException {

        String lecteur = FileReader.loadFileIntoString(emplacement, "UTF-8");
        return (JSONObject) JSONSerializer.toJSON(lecteur);
    }
    
    public static JSONArray obtenirJSONArray (String emplacement) throws IOException {

        String lecteur = FileReader.loadFileIntoString(emplacement, "UTF-8");
        return (JSONArray) JSONSerializer.toJSON(lecteur);
    }
    
    
    public static void ecritureDeSortie (JSONObject sortie, String emplacement) throws IOException {
        
        try (FileWriter ecrire = new FileWriter(emplacement)){
            ecrire.write(sortie.toString(4));
        }
    }
    
    public static JSONObject resultatToJSONObject(Resultat resultat){
        
        JSONObject obj = new JSONObject();
        
        obj.accumulate("complet", resultat.isComplet());
        obj.accumulate("erreurs", resultat.getErreurs());
        
        return obj;
    }

    public static JSONObject obtenirTabCategories() throws IOException {
        return TraitementJSON.obtenirJSONObject("json/categories.json").getJSONObject("catégories");
    }
    
    public static JSONObject obtenirTabOrdre() throws IOException {
        return TraitementJSON.obtenirJSONObject("json/categories.json").getJSONObject("ordres");
    }
    
    public static JSONObject obtenirTabStat() throws IOException {
        return TraitementJSON.obtenirJSONObject("json/categories.json").getJSONObject("statistiques");
    }
    
}
