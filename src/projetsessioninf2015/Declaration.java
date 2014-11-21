/**
 *
 * @author Achille
 * @author Pierre-Alexandre
 * @author Gires
 */
package projetsessioninf2015;

import java.io.IOException;
import java.util.ArrayList;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Declaration {
    
    JSONObject declaration;
    JSONArray activites;
    ArrayList<Activite> listeActivites;
    boolean erreurFonctionnel;
    
    public Declaration (String emplacement) throws IOException, Exception{
        
        erreurFonctionnel = false;
        declaration = TraitementJSON.obtenirJsonObject(emplacement);
        activites = declaration.getJSONArray("activités");
        creerListeActivites();
    }
    
    public String getPrenom(){
        
        return declaration.getString("prénom");
    }
    
    public String getNom(){
        
        return declaration.getString("nom");
    }
    
    public int getSexe(){
        
        return declaration.getInt("sexe");
    }
    
    public String getCycle(){
        
        return declaration.getString("cycle");
    }
    
    public String getOrdre(){
        
        return declaration.getString("ordre");
    }
    
    public String getNumeroPermis(){
        
        return declaration.getString("numero_de_permis");
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
