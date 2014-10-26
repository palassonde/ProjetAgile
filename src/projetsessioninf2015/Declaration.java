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
       
    String ordre;
    String cycle;
    int nbrHeuresTotal;
    int heuresCyclePrecedent;
    Map <String, Integer> categories = new HashMap<>();
    private final String numeroPermis;
    Resultat resultat;
    private final ArrayList<Activite> activites;
    private final JSONArray cyclesSupportes;
    private JSONArray listeCategories;
    private JSONArray listeSousCategories;
    private final JSONObject fichierDeclaration;
    
    public Declaration (JSONObject fichierDeclaration) throws IOException, ParseException {
        
        this.fichierDeclaration = fichierDeclaration;
        this.resultat = new Resultat();
        this.cycle = fichierDeclaration.getString("cycle");
        this.ordre = fichierDeclaration.getString("ordre");
        this.numeroPermis = fichierDeclaration.getString("numero_de_permis");
        this.activites = new ArrayList();
        obtenirActivites();
        this.cyclesSupportes = obtenirCyclesSupportes();
        listeCategories = new JSONArray();
        listeSousCategories = new JSONArray();
        this.nbrHeuresTotal = 0;
        obtenirListeCategories("json/exigences/listecategories.json");
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
        
        boolean laSelection1 = false;
        boolean laSelection2 = false;
        boolean ok = true;
        
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
    
    void traitementArchitecte () {
        
        heuresCyclePrecedent = fichierDeclaration.getInt("heures_transferees_du_cycle_precedent");
        
        if (heuresCyclePrecedent > 7) {

            heuresCyclePrecedent = 7;
            resultat.erreurs.add("Le nombre d'heures cumulees ne peut dépasser 7");
            
        } else if (heuresCyclePrecedent < 0) {

            heuresCyclePrecedent = 0;
        }
       
        int heuresMinimumSousCategories = heuresCyclePrecedent;
        
        if (categories.get("présentation") > 23){
            categories.put("présentation", 23);
        }
        if (categories.get("groupe de discussion") > 17){
            categories.put("groupe de discussion", 17);
        }
        if (categories.get("projet de recherche") > 23){
            categories.put("projet de recherche", 23);
        }
        if (categories.get("rédaction professionnelle") > 17){
            categories.put("rédaction professionnelle", 17);
        }
 
        for (int i = 0; i < listeSousCategories.size(); i++) {
   
            heuresMinimumSousCategories += categories.get(listeSousCategories.getString(i));
        }
        
        if (heuresMinimumSousCategories < 17){
            
            resultat.complet = false;
            resultat.erreurs.add("Il y a moins de 17 heures effectués dans les catégories demandés");
        }
        
        calculerHeuresTotal();
        
        if (nbrHeuresTotal < 42){
            
            resultat.complet = false;
            resultat.erreurs.add("Il y a moins de 40 heures effectués dans la formation continue");
        }
    }
    
    void traitementPsychologue () {
        
    }
    void traitementGeologue () {
        
    }
    
    private void traitement () throws ParseException{
        
        for (Activite activite : activites) {
           
            if (activite.validerDate(cyclesSupportes)) {
                
               if (listeCategories.contains(activite.getCategorie())){
                   
                   accumulerHeures(activite);
               }
               else{
                   
                    resultat.erreurs.add("L'activité " + activite.getDescription() + " n'a pas été comptabilisé");
               }    
            }
            else{ 
                
               resultat.erreurs.add("L'activité " + activite.getDescription() + " a été effectué à l'extérieur de l'intervalle demandé");
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
    
    private void obtenirActivites () throws ParseException {
        
        JSONArray listeActivites = fichierDeclaration.getJSONArray("activites");
        
        for (int i = 0; i < listeActivites.size(); i++) {
            
            Activite activite = new Activite(listeActivites.getJSONObject(i));
            activites.add(activite);
        }
    }

    private JSONArray obtenirCyclesSupportes () throws IOException {
        
        JSONObject liste = obtenirJsonObject("json/exigences/listeCycles.json");
        JSONArray cycles = liste.getJSONArray(fichierDeclaration.getString("ordre"));
        
        return cycles;
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

    private void obtenirListeCategories (String emplacement) throws IOException {
        
        String lecteur = FileReader.loadFileIntoString(emplacement, "UTF-8");
        JSONArray liste = new JSONArray();
        listeCategories = JSONObject.fromObject(lecteur).getJSONArray(ordre);
        listeSousCategories = JSONObject.fromObject(lecteur).getJSONArray("sous-categories");
    }
    
    private void calculerHeuresTotal() {
        
        nbrHeuresTotal += heuresCyclePrecedent;
        
        for (Map.Entry<String, Integer> categorie : categories.entrySet()){
            
           nbrHeuresTotal += categorie.getValue();
        }
    }
}
