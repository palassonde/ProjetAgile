/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 *
 * @author palass
 */
public class TraitementJSON {
    
    public static  JSONObject obtenirJsonObject (String emplacement) throws IOException {

        String lecteur = FileReader.loadFileIntoString(emplacement, "UTF-8");
        return (JSONObject) JSONSerializer.toJSON(lecteur);
    }
    
    public static JSONArray obtenirJsonArray (String emplacement) throws IOException {

        String lecteur = FileReader.loadFileIntoString(emplacement, "UTF-8");
        return (JSONArray) JSONSerializer.toJSON(lecteur);
    }
    
    
    public static void ecritureDeSortie (JSONObject sortie, String emplacement) throws IOException {
        
        try (FileWriter ecrire = new FileWriter(emplacement)){
            ecrire.write(sortie.toString(4));
        }
    }
    
    public static JSONObject toJSONObject(Object bean){
        
        JSONObject obj = JSONObject.fromObject(bean);
        return obj;
    }
    
    public static HashMap getMapCategories () throws IOException {
        
        String emplacement = "json/listeCategories.json";
        HashMap categories = new HashMap();
        JSONArray listeCategories = obtenirJsonObject(emplacement).getJSONArray("catégories");
        
        for (int i = 0; i < listeCategories.size(); i++)
            categories.put(listeCategories.getString(i), 0);
        
        return categories;
    }
    
}
