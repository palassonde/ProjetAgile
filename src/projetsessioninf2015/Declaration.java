/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetsessioninf2015;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONObject;

/**
 *
 * @author palass
 * la méthode ci fait une vérification de tout ce qui est entré 
 * c'est à dire qu'il vérifie la catégorie les heures dans chaque
 * activité 
 * retourne des messages des messages d'erreur si une étape n'a pas été
 * vérifié.
 */
class Declaration {
       
    String ordre;
    String cycle;
    String numeroPermis;
    
    int nbrHeuresTotal;
    int heuresCyclePrecedent;
    int heuresTotalMinimum;
    
    Map <String, Integer> categories = new HashMap<>();
   
    Resultat resultat;
    LectureJSON lecture;
    
    public Declaration (LectureJSON lecture) throws IOException, ParseException {
        
        this.lecture = lecture;
        this.resultat = new Resultat();
        this.cycle = lecture.declaration.getString("cycle");
        this.ordre = lecture.declaration.getString("ordre");
        this.numeroPermis = lecture.declaration.getString("numero_de_permis");
        this.nbrHeuresTotal = 0;
    }
    
    public JSONObject valider () throws IOException, ParseException, Exception {
           
        JSONObject resultatFinal = new JSONObject();
            
        validerCycle();
        creerCategories();
        traitement();

        resultatFinal.accumulate("complet", resultat.complet);
        resultatFinal.accumulate("erreurs", resultat.erreurs);
        return resultatFinal;
    }
    // crée la catégprie (les activtés)
    
    void creerCategories () {
        
        for (int i = 0; i < lecture.listeCategories.size(); i++){
            categories.put(lecture.listeCategories.getString(i), 0);
        }
    }
// calcul les heures accumuleés dans les cycles préceédent
    void accumulerHeures (Activite activite) {
        
        int heures;
        heures = categories.get(activite.getCategorie());
        heures += activite.getHeures();
        categories.put(activite.getCategorie(), heures);
    }
    /*
    * traitement particulier de l'ordre des architectes.
    */
    void traitementArchitecte () {
        
        heuresCyclePrecedent = lecture.declaration.getInt("heures_transferees_du_cycle_precedent");
        
        if (heuresCyclePrecedent > 7){

            heuresCyclePrecedent = 7;
            resultat.erreurs.add("Le nombre d'heures cumulees ne peut dépasser 7");
            
        } else if (heuresCyclePrecedent < 0){

            heuresCyclePrecedent = 0;
        }
       
        int heuresMinimumSousCategories = heuresCyclePrecedent;
        
        if (categories.get("présentation") > 23) {
            
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
 
        for (int i = 0; i < lecture.listeSousCategories.size(); i++){
   
            heuresMinimumSousCategories += categories.get(lecture.listeSousCategories.getString(i));
        }
        
        if (heuresMinimumSousCategories < 17){
            
            resultat.complet = false;
            resultat.erreurs.add("Il y a moins de 17 heures effectués dans les catégories demandés");
        }
        
        calculerHeuresTotal();
        
        if (nbrHeuresTotal < heuresTotalMinimum){

            resultat.complet = false;
            resultat.erreurs.add("Il y a moins de "+heuresTotalMinimum+" heures effectués dans la formation continue");
        }
            
    }
    /*
    * traitement particulier de l'ordre des psychologues.
    */
    void traitementPsychologue () {
        
        if (categories.get("cours") < 25){
            
            categories.remove(categories.get("cours"));
        }
            
        if (categories.get("conférence") > 15){
            
            categories.put("conférence", 15);
        }
            
        calculerHeuresTotal();
        
        if (nbrHeuresTotal < heuresTotalMinimum){
            
            resultat.complet = false;
            resultat.erreurs.add("Il y a moins de "+heuresTotalMinimum+" heures effectués dans la formation continue");
        }
        
    }
    /* 
    * traitement particulier de l'ordre des geologues.
    */
    void traitementGeologue () {
        
        if (categories.get("cours") < 22){
            
            categories.remove(categories.get("cours"));
        }
            
        if (categories.get("projet de recherche") < 3){
            
            categories.remove(categories.get("projet de recherche"));
        }
        
        if (categories.get("groupe de discussion") < 1){
            
            categories.remove(categories.get("groupe de discussion"));
        }
            
        calculerHeuresTotal();
        
        if (nbrHeuresTotal < heuresTotalMinimum) {
            
            resultat.complet = false;
            resultat.erreurs.add("Il y a moins de "+heuresTotalMinimum+" heures effectués dans la formation continue");
        }
        
    }
    
    /* fait une vérification si l'ordre existe
    prend cet ordre calcule ces heures 
    et voit si elle a été effectuer dans le cycle 
    */
    private void traitement () throws ParseException, Exception {
        
        
        traitementActivites();
        obtenirHeuresTotalMinimum();
        
        switch(ordre){
            
            case "architectes":
                traitementArchitecte();
                break;
            case "psychologues":
                traitementPsychologue();
                break;
            case "géologues":
                traitementGeologue();
                break;
            case "podiatres":
                traitementGeologue();
                break;
        }
    }
    
// fait une vérification du cycle 
    private void validerCycle () {
        
        boolean cycleExisteDansListe = false;
        
        for (int i = 0; i < lecture.cyclesSupportes.size(); i++) {
            
            if (cycle.equals(lecture.cyclesSupportes.getJSONObject(i).getString("cycle")))
                cycleExisteDansListe = true;
        }
        
        if (!cycleExisteDansListe) {
            
            resultat.complet = false;
            resultat.erreurs.add("Le cycle ne correspond à aucun des cycles supportés");
        }      
    }
    
    // fait la somme de tous les heures dans chaque catégorie
    private void calculerHeuresTotal() {
        
        nbrHeuresTotal += heuresCyclePrecedent;
        
        for (Map.Entry<String, Integer> categorie : categories.entrySet()){
            
           nbrHeuresTotal += categorie.getValue();
        }
    }
    
    private void obtenirHeuresTotalMinimum() {
        
        switch(ordre){
            
            case "architectes":
                heuresTotalMinimum = 40;
                if(!cycle.equals("2012-2014"))
                    heuresTotalMinimum = 42;
                break;
            case "podiatres":
                heuresTotalMinimum = 60;
                break;
            case "géologues":
                heuresTotalMinimum = 55;
                break;
            case "psychologues":
                heuresTotalMinimum = 90;
                break;
        }
    }

    private void traitementActivites() throws Exception {
        
        for (Activite activite : lecture.activites){
           
            if (activite.validerDate(lecture.cyclesSupportes)){
                
               if (lecture.listeCategories.contains(activite.getCategorie())){
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
    }
}
