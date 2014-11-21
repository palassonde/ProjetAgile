
package model;

import util.ISO8601DateParser;
import java.util.Date;
import net.sf.json.JSONObject;

/**
 *
 * @author Achille
 * @author Pierre-Alexandre
 * @author Gires
 */
public class Activite {
    
    private final JSONObject activite;
    private boolean validite;
    
    public Activite (JSONObject activite){
        
        this.activite = activite;
        validite = true;
    }
    
    public int getHeures(){
        
        return activite.getInt("heures");
    }
    
    public String getDescription(){
        
        return activite.getString("description");
    }
    
    public String getCategorie(){
        
        return activite.getString("catégorie");
    }
   
    public Date getParsedDate() throws Exception {
        
        return ISO8601DateParser.parse(getDate());
    }
    
    public String getDate() throws Exception {
        
        return activite.getString("date");
    }

    public boolean getValidite() {
        return validite;
    }
    
    public void setInvalide(){
        validite = false;
    }
    
    
}
