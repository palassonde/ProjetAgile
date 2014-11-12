package projetsessioninf2015;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 *
 * @author palass
 */
public class LectureJSON {
    
    private final String emplacementDeclaration;
    private final String emplacementListeCategories = "json/exigences/listecategories.json";
    private final String emplacementListeCycles = "json/exigences/listeCycles.json";
    
    private final String permisValide = "[ARSZ]\\d\\d\\d\\d";
    
    private final JSONArray erreur;
    public JSONArray cyclesSupportes;
    public JSONArray listeCategories;
    public JSONArray listeSousCategories;
    public JSONObject declaration;
    public ArrayList<Activite> activites;
    
    LectureJSON (String emplacementDeclaration) throws IOException{
        
        this.emplacementDeclaration = emplacementDeclaration;
        cyclesSupportes = new JSONArray();
        listeCategories = new JSONArray();
        listeSousCategories = new JSONArray();
        activites = new ArrayList<>();
        declaration = new JSONObject();
        erreur = new JSONArray();
    }
    
    public void lireFichiersJSON () throws IOException, ParseException, Exception{
        
        obtenirDeclaration();
        obtenirListeCategories();
        obtenirCyclesSupportes();
        obtenirActivites();
        validerFichier();
    }
    
    private void obtenirDeclaration () throws IOException {

        declaration = obtenirJsonObject(emplacementDeclaration);
    }
    
    private void obtenirListeCategories () throws IOException {
        
        String lecteur = FileReader.loadFileIntoString(emplacementListeCategories, "UTF-8");
        listeCategories = JSONObject.fromObject(lecteur).getJSONArray(declaration.getString("ordre"));
        listeSousCategories = JSONObject.fromObject(lecteur).getJSONArray("sous-categories");
    }
    
    private void obtenirCyclesSupportes () throws IOException {
        
        JSONObject liste = obtenirJsonObject(emplacementListeCycles);
        JSONArray cycles = liste.getJSONArray(declaration.getString("ordre"));
        cyclesSupportes = cycles;
    }
    
    private JSONObject obtenirJsonObject (String emplacement) throws IOException {

        String lecteur = FileReader.loadFileIntoString(emplacement, "UTF-8");
        return (JSONObject) JSONSerializer.toJSON(lecteur);
    }

    private void obtenirActivites () throws ParseException, Exception {
        
        JSONArray listeActivites = declaration.getJSONArray("activites");
        
        for (int i = 0; i < listeActivites.size(); i++) {
            
            Activite activite = new Activite(listeActivites.getJSONObject(i));
            activites.add(activite);
        }
    }
    
    private void validerFichier () throws Exception{
        
        validerPermis();
        validerActivites();
    }
    
    private void validerPermis () throws Exception{
        
        if(!declaration.getString("numero_de_permis").matches(permisValide)){
            throw new Exception("Le permis n'est pas valide");
        }
    }

    private void validerActivites () throws Exception {
        
        for (Activite activite : activites) {
            if (activite.getHeures() < 1){
                throw new Exception("Les heures des activités doivent être supérieur à 0");
            }
            else if (activite.getDescription().length() < 20){
                throw new Exception("La description d'une ou plusieurs activités est trop courte");
            }
        }
    }
    
}
