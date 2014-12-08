
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
    
    private final Statistique statistique;
    private final Resultat resultat;
    private final Declaration declaration;
    private final ExigencesOrdre exigences;
    private final JSONObject heuresParCategories;
    
    private int nbrHeuresTotal;
    
    public Traitement (Declaration declaration) throws IOException{
        
        this.declaration = declaration;
        exigences = new ExigencesOrdre(declaration);
        statistique = new Statistique();
        resultat = new Resultat();
        heuresParCategories = TraitementJSON.obtenirTabCategories();
    }
    
    private void verifierHeuresTotal () {
        
        if (nbrHeuresTotal < exigences.getHeuresMinimum()) {
            
            resultat.setIncomplet();
            resultat.ajoutErreur("Il y a moins de "+exigences.getHeuresMinimum()+" heures effectuées dans la formation continue");
        }
        
    }
    
    private void calculerHeuresTotal () {
        
        Iterator<String> keys = heuresParCategories.keys();
        nbrHeuresTotal += declaration.getHeuresCyclePrecedent();
        
        while (keys.hasNext()) {
            
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

   public void ecrireResultat (String fichierSortie) throws IOException {
        TraitementJSON.ecritureDeSortie(TraitementJSON.resultatToJSONObject(resultat), fichierSortie);
        ecrireStatistique();
    }

  public void produireResultat () throws Exception {
        
        verifierActivites();
        validerCycle();
        caclulerHeures();
        verifierHeuresSousCategories();
        heuresParCategories.remove("sous-catégories");
        verifierHeuresMinimales();
        verifierHeuresMaximales();
        calculerHeuresTotal();
        verifierHeuresTotal();
        compilerStatistique();
        verifierValidite();
    }
    
    private void verifierActivites () throws Exception{
         
        for (Activite activite : declaration.getActivites()) {
            
            if (!Validation.validerDateActivite(exigences.getCyclesSupportes(), activite.getParsedDate(), declaration.getCycle())) {
                activite.setInvalide();
                resultat.ajoutErreur("L'activité "+ activite.getDescription() +" a été effectuée à l'extérieur de l'intervalle demandé");
            }
            else if (!Validation.validerFormatDate(activite.getDate())) {
                activite.setInvalide();
                resultat.setInvalide();
            }
            else if (!exigences.getCategories().has(activite.getCategorie())) {
                activite.setInvalide();
                resultat.ajoutErreur("La catégorie "+ activite.getCategorie() +" n'est pas supportée");
            }
            else if (!Validation.validerDescriptionActivite(activite)) {
                activite.setInvalide();
                resultat.setInvalide();
            }
            else if (!Validation.validerHeuresActivite(activite)){
                activite.setInvalide();
                resultat.setInvalide();
            }
        }   
    }
    
    private void verifierHeuresMinimales () {
        
        Iterator<String> keys = heuresParCategories.keys();
        
        while (keys.hasNext()) {
            String key = keys.next();
            if (heuresParCategories.getInt(key) < exigences.getHeuresMinParCategories().getInt(key)) {
                resultat.setIncomplet();
                resultat.ajoutErreur("Il y a moins de "+ exigences.getHeuresMinParCategories().getInt(key) +
                                     " heures effectuées dans la catégorie " + key);
            }
        }
    }
    
    private void verifierHeuresMaximales () {
        
        Iterator<String> keys = heuresParCategories.keys();
        
        while (keys.hasNext()) {
            String key = keys.next();
            if (exigences.getHeuresMaxParCategories().getInt(key) > 0){
                if (heuresParCategories.getInt(key) > exigences.getHeuresMaxParCategories().getInt(key))
                    heuresParCategories.put(key, exigences.getHeuresMaxParCategories().getInt(key));
            }
        }
    }
    
    private void verifierHeuresSousCategories () {
        
        int heures = 0;
        heures += declaration.getHeuresCyclePrecedent();
        
        
        heures += heuresParCategories.getInt("sous-catégories");
        
        if (heures < exigences.getHeuresMinParCategories().getInt("sous-catégories")) {
            resultat.setIncomplet();
            resultat.ajoutErreur("Il y a moins de 17 heures effectuées dans les sous-catégories");
        }
    }

    private void caclulerHeures () {
        
        int heures;
        int heuresSousCategories;
        
        for (Activite activite : declaration.getActivites()) {
            
            if (activite.isValide()) {
                
                heures = heuresParCategories.getInt(activite.getCategorie());
                heures += activite.getHeures();
                heuresParCategories.put(activite.getCategorie(), heures);
                if (exigences.getSousCategories().contains(activite.getCategorie())) {
                    heures = heuresParCategories.getInt("sous-catégories");
                    heures += activite.getHeures();
                    heuresParCategories.put("sous-catégories", heures);
                }
            }
        }  
    }

   public void ecrireStatistique () throws IOException {
        statistique.ecrire();
    }

   public void compilerStatistique () throws Exception {
        
        statistique.incrementerStat("déclarations_traitées");
        
        if ( resultat.isComplet() && resultat.isValide()) {
            statistique.incrementerStat("déclarations_complètes");
        }else{
             statistique.incrementerStat("déclarations_invalides");
        }
        
        if (declaration.getSexe()==0) {
           statistique.incrementerStat("déclarations_hommes"); 
        }else if (declaration.getSexe()==1){
            statistique.incrementerStat("déclarations_femmes");  
        }else if (declaration.getSexe()==2){
            
            statistique.incrementerStat("déclarations_sexe_inconnu");  
        }
        
        for (Activite activite: declaration.getActivites()){  
            if (activite.isValide()){
                statistique.incrementerStat("activités_valides"); 
                statistique.incrementerCategorie(activite.getCategorie());
             }
        }

        if (!Validation.validerPermis(exigences.getNormePermis(), declaration.getNumeroPermis())){
            resultat.setInvalide();
            statistique.incrementerStat("déclaration_permis_invalides"); 
        }

        if (resultat.isValide() && resultat.isComplet())
            statistique.incrementerDeclarationComplete(declaration.getOrdre());
        
        if (resultat.isValide() && !(resultat.isComplet()))
            statistique.incrementerDeclarationIncomplete(declaration.getOrdre());
      
    }

    public void verifierValidite() throws Exception {
        
        if(!resultat.isValide())
            throw new Exception("La déclaration est invalide");   
    }
    
    public void gestionErreur(){
        
        resultat.setIncomplet();
        resultat.getErreurs().clear();
        resultat.ajoutErreur("Le fichier entré est invalide, le cycle est incomplet"); 
    }

}
