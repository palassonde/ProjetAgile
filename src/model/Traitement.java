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
        heuresParCategories = TraitementJSON.obtenirJsonObject("json/tableaux.json");
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

    void traiterDeclaration(Declaration declaration) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void ecrireResultat(String fichierSortie) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void traiterDeclaration() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
            else if(!exigences.getCategoriesSupportes().contains(activite.getCategorie())){
                activite.setInvalide();
                resultat.ajoutErreur("L'activité "+ activite.getDescription() +" n'est pas supporté");
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
                                     " heures effectuées dans la catégorie" + key);
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
            
            if(activite.getValidite()){
                
                heures = heuresParCategories.getInt(activite.getCategorie());
                heuresParCategories.put(activite.getCategorie(), activite.getHeures());
            }
        }  
    }

    
}
