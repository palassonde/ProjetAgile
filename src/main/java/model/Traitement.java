/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.IOException;
import java.util.Iterator;
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
    JSONObject heuresParCategories;
    
    int nbrHeuresTotal;
    
    public Traitement(Declaration declaration) throws IOException{
        
        this.declaration = declaration;
        exigences = new ExigencesOrdre(declaration);
        statistique = new Statistique();
        resultat = new Resultat();
        heuresParCategories = TraitementJSON.obtenirTabCategories();
    }
    
    private void verifierHeuresTotal() {
        
        if (nbrHeuresTotal < exigences.getHeuresMinimum()) {
            
            resultat.setIncomplet();
            resultat.ajoutErreur("Il y a moins de "+exigences.getHeuresMinimum()+" heures effectués dans la formation continue");
        }
    }
    
    private void calculerHeuresTotal() {
        
        Iterator<String> keys = heuresParCategories.keys();
        
        while(keys.hasNext()){
            String key = keys.next();
            nbrHeuresTotal += heuresParCategories.getInt(key);
        }
    }

    
    private void validerCycle () {
        
        if (!Validation.validerCycle(exigences.getCyclesSupportes(), declaration.getCycle())) {
            
            resultat.setIncomplet();
            resultat.ajoutErreur("Le cycle ne correspond à aucun des cycles supportés");
        }      
    }

    void ecrireResultat(String fichierSortie) throws IOException {
        TraitementJSON.ecritureDeSortie(TraitementJSON.resultatToJSONObject(resultat), fichierSortie);
    }

    void produireResultat() throws Exception {
        
        verifierActivites();
        caclulerHeures();
        verifierHeuresMinimales();
        verifierHeuresMaximales();
        verifierHeuresSousCategories();
    }
    
    private void verifierActivites() throws Exception{
         
        for(Activite activite : declaration.getActivites()){
            
            if(!Validation.validerDateActivite(exigences.getCyclesSupportes(), activite.getParsedDate())){
                activite.setInvalide();
                resultat.ajoutErreur("L'activité "+ activite.getDescription() +" a été effectué à l'extérieur de l'intervalle demandé");
            }
            else if(!Validation.validerFormatDate(activite.getDate())){
                activite.setInvalide();
                resultat.setInvalide();
            }
            else if(!exigences.getCategories().has(activite.getCategorie())){
                activite.setInvalide();
                resultat.ajoutErreur("La catégorie "+ activite.getDescription() +" n'est pas supporté");
            }
            else if(!Validation.validerDescriptionActivite(activite)){
                activite.setInvalide();
            }
        }   
    }
    
    private void verifierHeuresMinimales(){
        
        Iterator<String> keys = heuresParCategories.keys();
        
        while(keys.hasNext()){
            String key = keys.next();
            if(heuresParCategories.getInt(key) < exigences.getHeuresMinParCategories().getInt(key)){
                resultat.setIncomplet();
                resultat.ajoutErreur("Il y a moins de "+ exigences.getHeuresMinParCategories().getInt(key) +
                                     " heures effectuées dans la catégorie " + key);
            }
        }
    }
    
    private void verifierHeuresMaximales(){
        
        Iterator<String> keys = heuresParCategories.keys();
        
        while(keys.hasNext()){
            String key = keys.next();
            if(heuresParCategories.getInt(key) > exigences.getHeuresMaxParCategories().getInt(key))
                heuresParCategories.put(key, exigences.getHeuresMaxParCategories().getInt(key));
        }
        
    }
    
    private void verifierHeuresSousCategories(){
        
        int heures = 0;
        
        Iterator<String> keys = heuresParCategories.keys();
        while(keys.hasNext()){
            String key = keys.next();
            if(exigences.getSousCategories().contains(key))
                heures += heuresParCategories.getInt(key);
        }
        if(heures < 17){
            resultat.setIncomplet();
            resultat.ajoutErreur("Il y a moins de 17 heures effectuées dans les sous-catégories");
        }
    }

    private void caclulerHeures() {
        
        int heures;
        
        for(Activite activite : declaration.getActivites()){
            
            if(activite.isValidite()){
                
                heures = heuresParCategories.getInt(activite.getCategorie());
                heuresParCategories.put(activite.getCategorie(), activite.getHeures());
            }
        }  
    }

    void ecrireStatistique() throws IOException {
        statistique.ecrire();
    }

    void compilerStatistique() throws Exception {
        
        statistique.incrementerStat("déclarations_traitées");
        
        if ( resultat.isComplet() && declaration.isValide()) {
            
            statistique.incrementerStat("déclarations_complètes");
        
        }else{
             statistique.incrementerStat("déclarations_invalides");
        }
        
        if (declaration.getSexe()==0) {
            
           statistique.incrementerStat("déclarations_homme"); 
        }else if (declaration.getSexe()==1){
            statistique.incrementerStat("déclarations_femmes");  
        }else if (declaration.getSexe()==2){
            
            statistique.incrementerStat("déclarations_sexe_inconnu");  
        }
        
        for (Activite activite:declaration.getActivites()){
            
            if (activite.isValidite()){
                statistique.incrementerStat("activités_valides"); 
                statistique.incrementerCategorie(activite.getCategorie());
             }
        }
        
        if (!Validation.validerPermis(exigences.getNormePermis(), declaration.getNumeroPermis()))
            statistique.incrementerStat("declaration_permis_invalides"); 
        
        System.out.println(resultat.isComplet());
        
        if(declaration.isValide() && resultat.isComplet())
            statistique.incrementerDeclarationComplete(declaration.getOrdre());
        
        if(declaration.isValide() && !resultat.isComplet())
            statistique.incrementerDeclarationIncomplete(declaration.getOrdre());
    }

}
