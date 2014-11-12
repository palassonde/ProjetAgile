package projetsessioninf2015;

import java.io.FileWriter;
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
    
    String emplacementDeclaration;
    String emplacementResultat;
    String emplacementListeCategories = "json/exigences/listecategories.json";
    String emplacementListeCycles = "json/exigences/listeCycles.json";
    
    private final JSONArray erreur;
    private final FileWriter ecrire;
    public JSONArray cyclesSupportes;
    public JSONArray listeCategories;
    public JSONArray listeSousCategories;
    public JSONObject declaration;
    public ArrayList<Activite> activites;
    
    
    LectureJSON(String emplacementDeclaration, String emplacementResultat) throws IOException{
        
        this.emplacementDeclaration = emplacementDeclaration;
        cyclesSupportes = new JSONArray();
        listeCategories = new JSONArray();
        listeSousCategories = new JSONArray();
        activites = new ArrayList<>();
        declaration = new JSONObject();
        erreur = new JSONArray();
        ecrire = new FileWriter(emplacementResultat);
    }
    
    public void lireFichiersJSON() throws IOException, ParseException {
        
        obtenirDeclaration();
        obtenirListeCategories();
        obtenirCyclesSupportes();
        obtenirActivites();
        try{
            validerFichiers();
        } catch(ErreurFonctionnel e){
            ecrire.write(erreur.toString(2));
        }
        
    }
    
    private void obtenirDeclaration () throws IOException {

        declaration = obtenirJsonObject(emplacementDeclaration);
    }
    
    private void obtenirListeCategories () throws IOException {
        
        String lecteur = FileReader.loadFileIntoString(emplacementListeCategories, "UTF-8");
        JSONArray liste = new JSONArray();
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

    private void obtenirActivites () throws ParseException {
        
        JSONArray listeActivites = declaration.getJSONArray("activites");
        
        for (int i = 0; i < listeActivites.size(); i++) {
            
            Activite activite = new Activite(listeActivites.getJSONObject(i));
            activites.add(activite);
        }
    }
    
    private void validerFichiers() throws IOException, ErreurFonctionnel{
        
        erreur.add("Fichier Invalide, Cycle Incomplet");
        
        if(!validerPermis()){
            throw new ErreurFonctionnel("Le permis n'est pas valide");
        }
        
        for (Activite activite : activites) {
            
            if (activite.getHeures() < 1){
             
                    throw new ErreurFonctionnel("L'heure d'une activité doit être supérieur à 0");
                }
            
        }        
    }
    
     /* vérifie le numéro de permis 
    return true si elle est correcte 
    et false dans le cas contraire
    */
    boolean validerPermis () {
        
        boolean laSelection1 = false;
        boolean laSelection2 = false;
        boolean ok = true;
        String numeroPermis = declaration.getString("numero_de_permis");
        
        if (numeroPermis.length() == 5) {
            
            char leChar = numeroPermis.charAt(0);
        
            switch (leChar) {
               case 'A':
                   laSelection1 = true; 
                   break;
                case 'R':
                   laSelection1 = true; 
                   break;
                case 'S':
                    laSelection1 = true; 
                    break;
                case 'Z':
                    laSelection1 = true; 
                    break;
            }
            
            for (int i = 1; i < numeroPermis.length(); i++) {
                
                if (numeroPermis.charAt(i) >= '0'&& numeroPermis.charAt(i) <= '9') {
                   laSelection2 = true; 
                }else{
                    ok = false;
                }
                    
            }
            laSelection2 = laSelection2 && ok;
        }
        
        return laSelection1 && laSelection2;
    }
}
