/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetsessioninf2015;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 *
 * @author palass
 */
class Declaration {
   
    int nbrHeuresTotal;
    int heuresCyclePrecedent;
    String cycle;
    Map <String, Integer> categories = new HashMap<>();
    String ordre;
    private final String numeroPermis;
    Resultat resultat;
    private final ArrayList<Activite> activites;
    private final JSONArray cyclesSupportes;
    private final JSONArray listeCategories;

    public Declaration (JSONObject declaration) throws IOException, ParseException {
        
        this.resultat = new Resultat();
        this.cycle = declaration.getString("cycle");
        this.ordre = declaration.getString("ordre");
        this.heuresCyclePrecedent = declaration.getInt("heures_transferees_du_cycle_precedent");
        this.numeroPermis = declaration.getString("numero_de_permis");
        this.activites = obtenirActivites(declaration);
        this.cyclesSupportes = obtenirCyclesSupportes(declaration);
        this.listeCategories = obtenirListeCategories("json/exigences/listecategories.json");
    }
    
    public JSONObject valider () throws IOException, ParseException {
           
        JSONObject resultatFinal = new JSONObject();
        
        if (validerNumeroPermis()) {
            
            validerCycle();
            creerCategories();
            traitement();

            
            resultatFinal.accumulate("complet", resultat.complet);
            resultatFinal.accumulate("erreurs", resultat.erreurs);
            return resultatFinal;
            
        }
        
        resultatFinal.accumulate("Fichier invalide", "cycle incomplet");
        return resultatFinal;
    }
    
    
    
    void creerCategories () {
        
        for (int i = 0; i < listeCategories.size(); i++) {
            categories.put(listeCategories.getString(i), 0);
        }
    }

    void accumulerHeures (Activite activite) {
        
        int heures;
        heures = categories.get(activite.getCategorie());
        heures += activite.getHeures();
        categories.put(activite.getCategorie(), heures);
    }
   
    boolean validerNumeroPermis () {
        
        boolean laSelection1=false;
        boolean laSelection2=false;
        boolean ok=true;
        if (numeroPermis.length()==5) {
            char leChar=numeroPermis.charAt(0);
        
            switch (leChar) {
               case 'A':
                   laSelection1=true; 
                   break;
                case 'R':
                   laSelection1=true; 
                   break;
                case 'S':
                    laSelection1=true; 
                    break;
                case 'Z':
                    laSelection1=true; 
                    break;
            }
            for (int i=1;i<numeroPermis.length();i++) {
                
                if (numeroPermis.charAt(i) >= '0'&& numeroPermis.charAt(i) <='9' ) {
                   laSelection2=true; 
                }else{
                    ok=false;
                }
                    
            }
            laSelection2=laSelection2&&ok;
        }
        
        return laSelection1&&laSelection2;
    }
    
    void traitementArchitecte () {
       
        int heureGroupeMinimum17 = 0;
        int heurePresentation = 0;
        int heureGroupeDiscussion=0;
        int heureProjetRecherche=0;
        int heureRedaction=0;
        
        heureGroupeMinimum17 += heuresCyclePrecedent;
        
        
        
        
        
        
        
    }
    
    void traitementPsychologue () {
        
    }
    void traitementGeologue () {
        
    }
    
    
    private void traitement () throws ParseException{
        
        if (heuresCyclePrecedent > 7) {

            heuresCyclePrecedent = 7;
            resultat.erreurs.add("Le nombre d'heures cumulees ne peut dépasser 7");
            
        } else if (heuresCyclePrecedent < 0) {

            heuresCyclePrecedent = 0;
        }
        
        nbrHeuresTotal += heuresCyclePrecedent;
        
        for (Activite activite : activites) {
           
            if (activite.validerDate(cyclesSupportes)) {
               
               accumulerHeures(activite);
            }
        }

        switch (this.ordre) {
            case "architecte":
                traitementArchitecte();
                break;
            case "psychologue":
                traitementPsychologue();
                break;
            case "Geologue":
                traitementGeologue();
                break;
        }
    }
    
    private ArrayList<Activite> obtenirActivites (JSONObject declaration) throws ParseException {
        
        JSONArray listeActivites = declaration.getJSONArray("activites");
        ArrayList<Activite> activites = new ArrayList<>();
        
        for (int i = 0; i < listeActivites.size(); i++) {
            
            Activite activite = new Activite(listeActivites.getJSONObject(i));
        
            activites.add(activite);
        }
        
        return activites;
    }

    private JSONArray obtenirCyclesSupportes (JSONObject declaration) throws IOException {
        
        JSONObject listeCyclesSupportes = obtenirJsonObject("json/exigences/listeCycles.json");
        JSONArray cyclesSupportes = listeCyclesSupportes.getJSONArray(declaration.getString("ordre"));
        
        return cyclesSupportes;
    }
    
     private static JSONArray obtenirJsonArray (String emplacement) throws IOException {

        String lecteur = FileReader.loadFileIntoString(emplacement, "UTF-8");
        return JSONArray.fromObject(lecteur);
    }
     
     private static JSONObject obtenirJsonObject(String emplacement) throws IOException {

        String lecteur = FileReader.loadFileIntoString(emplacement, "UTF-8");
        return (JSONObject) JSONSerializer.toJSON(lecteur);
    }

    private void validerCycle () {
        
        boolean cycleExisteDansListe = false;
        
        for (int i = 0; i < cyclesSupportes.size(); i++) {
            
            if (cycle.equals(cyclesSupportes.getJSONObject(i).getString("cycle"))) {
                cycleExisteDansListe = true;
            }
        }
        
        if (!cycleExisteDansListe) {
            resultat.complet = false;
            resultat.erreurs.add("Le cycle ne correspond à aucun des cycles supportés");
        }
                
    }

    private JSONArray obtenirListeCategories (String emplacement) throws IOException {
        
        String lecteur = FileReader.loadFileIntoString(emplacement, "UTF-8");
        JSONArray listeCategories = new JSONArray();
        listeCategories = JSONObject.fromObject(lecteur).getJSONArray(ordre);
        return listeCategories;
    }
}
