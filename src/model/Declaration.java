 /**
 *
 * @author Achille
 * @author Pierre-Alexandre
 * @author Gires
 */
package model;

import util.TraitementJSON;
import java.io.IOException;
import java.util.ArrayList;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Declaration {
    
    private final JSONObject declaration;
    private final JSONArray activites;
    private ArrayList<Activite> listeActivites;
    
    public Declaration (String emplacement) throws IOException, Exception{

        declaration = TraitementJSON.obtenirJsonObject(emplacement);
        activites = declaration.getJSONArray("activités");
        creerListeActivites();
    }
    
    public int getSexe(){       
        return declaration.getInt("sexe");
    }
    
    public String getNom(){
        return declaration.getString("nom");
    }
    
    public String getPrenom(){
        return declaration.getString("prenom");
    }
    
    public String getOrdre(){       
        return declaration.getString("ordre");
    }
    
    public String getCycle(){        
        return declaration.getString("cycle");
    }
    
    public String getNumeroPermis(){        
        return declaration.getString("numero_de_permis");
    }
    
    public int getHeuresCyclePrecedent() {
        return declaration.getInt("heures_transfere_cycle_precedent");
    }
    
    public ArrayList<Activite> getActivites(){       
        return listeActivites;
    }
    
    private void creerListeActivites() throws Exception {
        
        for (int i = 0; i < activites.size(); i++){
            
            Activite activite = new Activite(activites.getJSONObject(i));
            listeActivites.add(activite);
        }
    }
     
}
