
package projetsessioninf2015;

import java.util.Date;
import net.sf.json.JSONObject;

/**
 *
 * @author Achille
 * @author Pierre-Alexandre
 * @author Gires
 */
public class Activite {
    
    JSONObject activite;
    
    public Activite (JSONObject activite){
        
        this.activite = activite;
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
   
    Date getDate() throws Exception {
        
        return ISO8601DateParser.parse(activite.getString("date"));
    }
    
}
