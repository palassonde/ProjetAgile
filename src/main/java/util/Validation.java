/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Date;
import net.sf.json.JSONArray;
import model.Activite;

/**
 *
 * @author palass
 */
public class Validation {
    
    public static boolean validerSexe (int sexe) throws Exception {
        
        return !(sexe < 0 || sexe > 2);
    }
    
    public static boolean validerHeuresActivite (Activite activite) throws Exception {

        return activite.getHeures() >= 0;
    }
    
    public static boolean validerDescriptionActivite (Activite activite){
        
        return activite.getDescription().length() >= 20;
    }
    
    public static boolean validerPermis (String norme, String permis) throws Exception{

        return permis.matches(norme);
    }
    
    public static boolean validerCycle (JSONArray cyclesSupportes, String cycle) {
        
        boolean cycleExisteDansListe = false;
        
        for (int i = 0; i < cyclesSupportes.size(); i++) {
            
            if (cycle.equals(cyclesSupportes.getJSONObject(i).getString("cycle")))
                cycleExisteDansListe = true;
        }
        
        return cycleExisteDansListe; 
    }
    
    public static boolean validerFormatDate (String date){
    
        return date.matches("\\d{4}-\\d{2}-\\d{2}");
    }
    
    public static boolean validerDateActivite(JSONArray cyclesSupportes, Date date) throws Exception{
        
        Date dateMin;
        Date dateMax;
        
        for (int i = 0; i < cyclesSupportes.size(); i++){
            
            dateMin = ISO8601DateParser.parse(cyclesSupportes.getJSONObject(i).getString("dateMin"));
            dateMax = ISO8601DateParser.parse(cyclesSupportes.getJSONObject(i).getString("dateMax"));
          
            if(date.after(dateMin) && date.before(dateMax))
                return true;
        }
 
        return false;
    }
    
    
}
