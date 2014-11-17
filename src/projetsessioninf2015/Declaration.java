/**
 *
 * @author Achille
 * @author Pierre-Alexandre
 * @author Gires
 */
package projetsessioninf2015;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONObject;

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
    Statistique statistique;
    
    public Declaration (LectureJSON lecture) throws IOException, ParseException {
        
        this.lecture = lecture;
        resultat = new Resultat();
        cycle = lecture.declaration.getString("cycle");
        ordre = lecture.declaration.getString("ordre");
        numeroPermis = lecture.declaration.getString("numero_de_permis");
        nbrHeuresTotal = 0;
        statistique = new Statistique(lecture);
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
    }
    /*
    * traitement particulier de l'ordre des psychologues.
    */
    void traitementPsychologue () {
        
        if (categories.get("cours") < 25){
            
            resultat.complet = false;
            resultat.erreurs.add("Il y a moins de 25 heures effectué dans la catégorie cours");
        }
            
        if (categories.get("conférence") > 15){
            
            categories.put("conférence", 15);
        }
    }
    /* 
    * traitement particulier de l'ordre des geologues.
    */
    void traitementGeologuePodiatre () {
        
        if (categories.get("cours") < 22){
            
            resultat.complet = false;
            resultat.erreurs.add("Il y a moins de 22 heures effectué dans la catégorie cours");
        }
            
        if (categories.get("projet de recherche") < 3){
            
            resultat.complet = false;
            resultat.erreurs.add("Il y a moins de 3 heures effectué dans la catégorie projet de recherche");
        }
        
        if (categories.get("groupe de discussion") < 1){
            
            resultat.complet = false;
            resultat.erreurs.add("Il y a moins de 1 heures effectué dans la catégorie groupe de discussion");
        }
    }
    
    /* fait une vérification si l'ordre existe
    prend cet ordre calcule ces heures 
    et voit si elle a été effectuer dans le cycle 
    */
    private void traitement () throws ParseException, Exception {
        
        statistique.creerCategories();
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
                traitementGeologuePodiatre();
                break;
            case "podiatres":
                traitementGeologuePodiatre();
                break;
        }
        
        calculerHeuresTotal();
        verifierHeuresTotal();
        compilerStatistique();
        
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
                   compilerStatistiqueActivites(activite);
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

    private void verifierHeuresTotal() {
        
        if (nbrHeuresTotal < heuresTotalMinimum) {
            
            resultat.complet = false;
            resultat.erreurs.add("Il y a moins de "+heuresTotalMinimum+" heures effectués dans la formation continue");
        }
    }
    
    public Statistique recupererStatistiques(){

        return statistique;
    }

    private void compilerStatistique () {
       
        statistique.nbrTotalDeclarationTraite++;
        if (resultat.complet)
            statistique.nbrTotalDeclarationComplete++;
        else
            statistique.nbrTotalDeclarationInvalide++;
        
        switch ( lecture.declaration.getInt("sexe")) {
        
            case 0:
                statistique.nbrTotalDeclarationSexeIconnu++;
                break;
            case 1:
                statistique.nbrTotalDeclarationHomme++;
                break;
            case 2:
                statistique.nbrTotalDeclarationFemme++;
                break;
        }      
    }

    private void compilerStatistiqueActivites(Activite activite) {
        
        int nbrTotalActivites;
        statistique.nbrTotalActiviteValide++;
         
        nbrTotalActivites = statistique.activiteValideParCategorie.get(activite.getCategorie());
        nbrTotalActivites++;
        statistique.activiteValideParCategorie.put(activite.getCategorie(), nbrTotalActivites);
    }
}
