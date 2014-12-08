
package model;

import java.util.Date;
import net.sf.json.JSONObject;
import util.LectureDateISO8601;

/**
 *
 * @author Achille
 * @author Pierre-Alexandre
 * @author Gires
 */
public class Activite {
    
    private final JSONObject activite;
    private boolean valide;
    
    public Activite (JSONObject activite) {
        
        this.activite = activite;
        valide = true;
    }
    
    public int getHeures () {
        
        return activite.getInt("heures");
    }
    
    public String getDescription () {
        
        return activite.getString("description");
    }
    
    public String getCategorie () {
        
        return activite.getString("categorie");
    }
   
    public Date getParsedDate () throws Exception {
        
        return LectureDateISO8601.lire(getDate());
    }
    
    public String getDate () throws Exception {
        
        return activite.getString("date");
    }

    public boolean isValide () {
        return valide;
    }
    
    public void setInvalide (){
        valide = false;
    }
    
    
}
