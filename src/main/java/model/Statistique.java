/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import util.TraitementJSON;
import java.io.IOException;
import java.util.Iterator;
import net.sf.json.JSONObject;

/**
 *
 * @author palass
 */
public class Statistique {

    private final JSONObject statistique;
    private final JSONObject activitesValidesParCategories;
    private final JSONObject activitesValidesEtCompletesParOrdre;
    private final JSONObject activitesValidesEtIncompletesParOrdre;

    public Statistique() throws IOException {
        
        statistique = TraitementJSON.obtenirJSONObject("json/statistiques.json");
        activitesValidesParCategories = statistique.getJSONObject("activités_valides_par_catégories");
        activitesValidesEtCompletesParOrdre = statistique.getJSONObject("declarations_valides_et_completes_par_ordre_professionnelle");
        activitesValidesEtIncompletesParOrdre = statistique.getJSONObject("declarations_valides_et_incompletes_par_ordre_professionnelle");
    }
    
    public static void afficher() throws IOException{
        
        System.out.println(getFichierStat().toString(4));
    }
    
    private static JSONObject getFichierStat() throws IOException{
        
        JSONObject fichierStat = TraitementJSON.obtenirJSONObject("json/statistiques.json");
        return fichierStat;
    }
    
    public void ecrire() throws IOException {
        TraitementJSON.ecritureDeSortie(compilerJSONObject(), "json/statistiques.json");
    }

    public static void reinitialiser() throws IOException {
     
        JSONObject stat = new JSONObject();
        JSONObject catStat = TraitementJSON.obtenirTabStat();
        
        Iterator<String> keys = catStat.keys();
        while(keys.hasNext()){
            String key = keys.next();
            stat.put(key, 0);
        }
        stat.accumulate("activités_valides_par_catégories", TraitementJSON.obtenirTabCategories());
        stat.accumulate("declarations_valides_et_completes_par_ordre_professionnelle", TraitementJSON.obtenirTabOrdre());
        stat.accumulate("declarations_valides_et_incompletes_par_ordre_professionnelle", TraitementJSON.obtenirTabOrdre());
        
        TraitementJSON.ecritureDeSortie(stat, "json/statistiques.json");
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
    
    public void incrementerDeclarationComplete(String ordre){
        
        int valeur = activitesValidesEtCompletesParOrdre.getInt(ordre);
        valeur++;
        activitesValidesEtCompletesParOrdre.put(ordre, valeur);
    }
    
    public void incrementerDeclarationIncomplete(String ordre){
        
        int valeur = activitesValidesEtIncompletesParOrdre.getInt(ordre);
        valeur++;
        activitesValidesEtIncompletesParOrdre.put(ordre, valeur);
    }
    
    public  JSONObject compilerJSONObject(){
        
        JSONObject stat = new JSONObject();
        
        stat.put("déclarations_traitées", statistique.getInt("déclarations_traitées"));
        stat.put("déclarations_complètes", statistique.getInt("déclarations_complètes"));
        stat.put("déclarations_invalides", statistique.getInt("déclarations_invalides"));
        stat.put("déclarations_hommes", statistique.getInt("déclarations_hommes"));
        stat.put("déclarations_femmes", statistique.getInt("déclarations_femmes"));
        stat.put("déclarations_sexe_inconnu", statistique.getInt("déclarations_sexe_inconnu"));
        stat.put("activités_valides", statistique.getInt("activités_valides"));
        stat.put("declaration_permis_invalides", statistique.getInt("declaration_permis_invalides"));
        
        stat.accumulate("activités_valides_par_catégories", activitesValidesParCategories);
        stat.accumulate("declarations_valides_et_completes_par_ordre_professionnelle", activitesValidesEtCompletesParOrdre);
        stat.accumulate("declarations_valides_et_incompletes_par_ordre_professionnelle", activitesValidesEtIncompletesParOrdre);
        
        return stat;
    }
    
}
