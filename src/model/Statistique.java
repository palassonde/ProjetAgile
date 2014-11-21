/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import util.TraitementJSON;
import java.io.IOException;
import net.sf.json.JSONObject;

/**
 *
 * @author palass
 */
public class Statistique {

    private JSONObject statistique;
    private final JSONObject activitesValidesParCategories;
    private final JSONObject activitesValidesEtCompletesParOrdre;
    private final JSONObject activitesValidesEtIncompletesParOrdre;

    public Statistique() throws IOException {
        
        statistique = getFichierStat();
        activitesValidesParCategories = statistique.getJSONObject("activités_valides_par_catégories");
        activitesValidesEtCompletesParOrdre = statistique.getJSONObject("declarations_valides_et_completes_par_ordre_professionnelle");
        activitesValidesEtIncompletesParOrdre = statistique.getJSONObject("declarations_valides_et_incompletes_par_ordre_professionnelle");
    }
    
    public static void afficher() throws IOException{
        
        System.out.println(getFichierStat().toString(4));
    }
    
    private static JSONObject getFichierStat() throws IOException{
        
        JSONObject fichierStat = TraitementJSON.obtenirJsonObject("JSON/statistiques.json");
        return fichierStat;
    }
    
    static void ecrire() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    static void compiler() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static void reinitialiser() {
        
        /*statistique.putAll(statistique);
        
        activitesValidesParCategories.put( "rédaction professionnelle", 0);
        activitesValidesParCategories.put( "conférence", 0);
        activitesValidesParCategories.put( "colloque", 0);
        activitesValidesParCategories.put("formation continue", 0);
        activitesValidesParCategories.put( "projet de recherche", 0);
        activitesValidesParCategories.put(  "groupe de discussion", 0);
        activitesValidesParCategories.put(  "cours", 0);
        activitesValidesParCategories.put(  "présentation", 0);
        activitesValidesParCategories.put(  "séminaire", 0);
        activitesValidesParCategories.put(  "lecture dirigée", 0);
        activitesValidesParCategories.put(  "atelier", 0);
     
        statistique.put("déclarations_traitées", 0);
        statistique.put("déclarations_complètes",0); 
        statistique.put("déclarations_invalides", 0);
        statistique.put("déclarations_hommes",0); 
        statistique.put("déclarations_femmes",0); 
        statistique.put("déclarations_sexe_inconnu",0 );
        statistique.put("activités_valides", 0);
        statistique.put("activités_valides_par_catégories", activitesValidesParCategories);*/
    }
    
    public void incrementerStat(String stat){
        
        int valeur = statistique.getInt(stat);
        valeur++;
        statistique.put(stat, valeur);
    }
    
    public void incrementerCategorie(String categorie){
        
        int valeur = activitesValidesParCategories.getInt(categorie);
        valeur++;
        activitesValidesParCategories.put(categorie, valeur);
    }
    
    public void incrementerDeclarationCompleteInvalide(String ordre){
        
        int valeur = activitesValidesEtCompletesParOrdre.getInt(ordre);
        valeur++;
        activitesValidesEtCompletesParOrdre.put(ordre, valeur);
    }
    
    public void incrementerDeclarationCompleteValide(String ordre){
        
        int valeur = activitesValidesEtIncompletesParOrdre.getInt(ordre);
        valeur++;
        activitesValidesEtIncompletesParOrdre.put(ordre, valeur);
    }
    
    public JSONObject compilerJSONObject(){
        
        JSONObject stat = new JSONObject();
        
        stat.accumulate("activités_valides_par_catégories", activitesValidesParCategories);
        stat.accumulate("declarations_valides_et_completes_par_ordre_professionnelle", activitesValidesEtCompletesParOrdre);
        stat.accumulate("declarations_valides_et_incompletes_par_ordre_professionnelle", activitesValidesEtIncompletesParOrdre);

        return stat;
    }
    
}
