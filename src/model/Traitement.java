/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONObject;
import util.TraitementJSON;
import util.Validation;

/**
 *
 * @author palass
 */
public class Traitement {
    
    Statistique statistique;
    Resultat resultat;
    Declaration declaration;
    ExigencesOrdre exigences;
    HashMap heuresParCategories;
    
    public Traitement(Declaration declaration) throws IOException{
        
        this.declaration = declaration;
        exigences = new ExigencesOrdre(declaration);
        statistique = new Statistique();
        resultat = new Resultat();
        heuresParCategories = TraitementJSON.getMapCategories();
    }
    
    private void compilerStatistiqueActivites(Activite activite) {
        
        declaration.
        
        int nbrTotalActivites;
        statistique.nbrTotalActiviteValide++;
         
        nbrTotalActivites = statistique.activiteValideParCategorie.get(activite.getCategorie());
        nbrTotalActivites++;
        statistique.activiteValideParCategorie.put(activite.getCategorie(), nbrTotalActivites);
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
    
    private void verifierHeuresTotal() {
        
        if (nbrHeuresTotal < heuresTotalMinimum) {
            
            resultat.complet = false;
            resultat.erreurs.add("Il y a moins de "+heuresTotalMinimum+" heures effectués dans la formation continue");
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
               resultat.erreurs.add();
            }
        }
    }
    

    
    private void validerFichier () throws Exception{
        
        validerPermis();
        validerActivites();
        validerSexe();
    }

 


    private void obtenirStatistiques() throws IOException {
        
        
        
        statistiques = obtenirJsonObject(emplacementStatistiques);
    }
    
    private void obtenirHeuresTotalMinimum() {
        
        
    }
    
    private void calculerHeuresTotal() {
        
        nbrHeuresTotal += heuresCyclePrecedent;
        
        for (Map.Entry<String, Integer> categorie : categories.entrySet()){
            
           nbrHeuresTotal += categorie.getValue();
        }
    }
    
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
    
    public JSONObject valider () throws IOException, ParseException, Exception {
           
        JSONObject resultatFinal = new JSONObject();
            
        validerCycle();
        creerCategories();
        traitement();

        resultatFinal.accumulate("complet", resultat.complet);
        resultatFinal.accumulate("erreurs", resultat.erreurs);
        return resultatFinal;
    }

    void traiterDeclaration(Declaration declaration) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void ecrireResultat(String fichierSortie) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void traiterDeclaration() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    
    
    
    
       void ValeurNull() {
        
         nbrTotalDeclarationTraite = 0;
         nbrTotalDeclarationComplete = 0;
         nbrTotalDeclarationInvalide =0;
         nbrTotalDeclarationHomme = 0;
         nbrTotalDeclarationFemme = 0;
         nbrTotalDeclarationSexeIconnu = 0;
         nbrTotalActiviteValide = 0;
         this.lecture = null;
        
    }
     
// incrémente le nombre de fois l'activité a été faite
    JSONObject toJSONObject() {
        
        int valeur;
        
        JSONObject activitesValides = new JSONObject();
        
        for (Map.Entry<String, Integer> activiteValide : activiteValideParCategorie.entrySet()) {
            
           valeur = statistique.getJSONObject("activités_valides_par_catégories").getInt(activiteValide.getKey());
           activitesValides.put(activiteValide.getKey(), activiteValide.getValue() + valeur);
        }
        
        statistique.put("déclarations_traitées", nbrTotalDeclarationTraite);
        statistique.put("déclarations_complètes",nbrTotalDeclarationComplete); 
        statistique.put("déclarations_invalides", nbrTotalDeclarationInvalide);
        statistique.put("déclarations_hommes",nbrTotalDeclarationHomme); 
        statistique.put("déclarations_femmes",nbrTotalDeclarationFemme); 
        statistique.put("déclarations_sexe_inconnu",nbrTotalDeclarationSexeIconnu );
        statistique.put("activités_valides", nbrTotalActiviteValide);
        statistique.put("activités_valides_par_catégories", activitesValides);
        
        return statistique;
    }

    public JSONObject reinitialise() {
         
        activitesValides.put( "rédaction professionnelle", 0);
        activitesValides.put( "conférence", 0);
        activitesValides.put( "colloque", 0);
        activitesValides.put("formation continue", 0);
        activitesValides.put( "projet de recherche", 0);
        activitesValides.put(  "groupe de discussion", 0);
        activitesValides.put(  "cours", 0);
        activitesValides.put(  "présentation", 0);
        activitesValides.put(  "séminaire", 0);
        activitesValides.put(  "lecture dirigée", 0);
        activitesValides.put(  "atelier", 0);
     
        statistique.put("déclarations_traitées", 0);
        statistique.put("déclarations_complètes",0); 
        statistique.put("déclarations_invalides", 0);
        statistique.put("déclarations_hommes",0); 
        statistique.put("déclarations_femmes",0); 
        statistique.put("déclarations_sexe_inconnu",0 );
        statistique.put("activités_valides", 0);
        statistique.put("activités_valides_par_catégories", activitesValides);
        
        
         return statistique;
    }

    void produireResultat() {
        
        caclulerHeures();
        
    }
    
    private void validerActivites() throws Exception{
        
        for(Activite activite : declaration.getActivites()){
            
            if(!Validation.validerDateActivite(exigences.getCyclesSupportes(), activite.getParsedDate())){
                activite.setInvalide();
                resultat.ajoutErreur("L'activité "+ activite.getDescription() +" a été effectué à l'extérieur de l'intervalle demandé");
            }
            else if(!Validation.validerDescriptionActivite(activite) | !Validation.validerFormatDate(activite.getDate())){
                resultat.setInvalide();
            }
        }   
    }

    private void caclulerHeures() {
        
        for(Activite activite : declaration.getActivites()){
            
            if(activite.getValidite())
                int heures
                heures = heuresParCategories.get(activite.getCategorie());
                heuresParCategories.put(activite.getCategorie(), activite.getHeures());
        }  
    }
    
}
