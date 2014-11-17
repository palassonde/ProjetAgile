
package projetsessioninf2015;

import java.text.ParseException;
import java.util.Date;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 *
 * @author Achille
 * @author Pierre-Alexandre
 * @author Gires
 */
class Activite {
    
    private final String description;
    private final String categorie;
    private final int heures;
    private final Date date;
                     
    public Activite (JSONObject activite) throws ParseException, Exception {
        
        this.date = ISO8601DateParser.parse(activite.getString("date"));
        this.categorie = activite.getString("categorie");
        this.heures = activite.getInt("heures");
        this.description = activite.getString("description");
    }
    // vérifie l'heure return true si elle respecte les normes false dans le cas contraire
    public boolean validerDate (JSONArray cyclesSupportes) throws ParseException, Exception {
        
        Date dateMin;
        Date dateMax;
        
        for (int i = 0; i < cyclesSupportes.size(); i++){
            
            dateMin = ISO8601DateParser.parse(cyclesSupportes.getJSONObject(i).getString("dateMin"));
            dateMax = ISO8601DateParser.parse(cyclesSupportes.getJSONObject(i).getString("dateMax"));
          
            if (date.after(dateMin) && date.before(dateMax)){
                return true;
            }
        }
 
        return false;
    }
    
    public int getHeures () {
        return heures;
    }
    public String getCategorie () {
        return categorie;
    }
    public String getDescription () {
        return description;
    }
    
}
