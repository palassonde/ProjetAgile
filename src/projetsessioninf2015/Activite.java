/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetsessioninf2015;

import java.text.ParseException;
import java.util.Date;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author palass
 */
class Activite {
    
    private String description;
    private String categorie;
    private int heures;
    private final Date date;
                     
    public Activite(JSONObject activite) throws ParseException {
        
        date = ISO8601DateParser.parse(activite.getString("date"));
        categorie = activite.getString("categorie");
        heures = activite.getInt("heures");
    }
    
    public boolean validerDate(JSONArray cyclesSupportes) throws ParseException {
        
        boolean valide = true;
        Date dateMin;
        Date dateMax;
        
        for(int i = 0; i < cyclesSupportes.size(); i++){
            
            dateMin = ISO8601DateParser.parse(cyclesSupportes.getJSONObject(i).getString("datemin"));
            dateMax = ISO8601DateParser.parse(cyclesSupportes.getJSONObject(i).getString("datemax"));
          
            if (!(date.after(dateMin) && date.before(dateMax))){
                valide = false;
            }
        }
 
        return valide;
    }
    
    public int getHeures(){
        return heures;
    }
    public String getCategorie(){
        return categorie;
    }
    
}
