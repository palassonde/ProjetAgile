
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

    public Statistique () throws IOException {
        
        statistique = TraitementJSON.obtenirJSONObject("json/statistiques.json");
        activitesValidesParCategories = statistique.getJSONObject("activités_valides_par_catégories");
        activitesValidesEtCompletesParOrdre = statistique.getJSONObject("déclarations_valides_et_complètes_par_ordre_professionnelle");
        activitesValidesEtIncompletesParOrdre = statistique.getJSONObject("déclarations_valides_et_incomplètes_par_ordre_professionnelle");
    }
    
    public static void afficher () throws IOException{
        
        System.out.println(getFichierStat().toString(4));
    }
    
    private static JSONObject getFichierStat () throws IOException{
        
        JSONObject fichierStat = TraitementJSON.obtenirJSONObject("json/statistiques.json");
        return fichierStat;
    }
    
    public void ecrire () throws IOException {
        
        TraitementJSON.ecritureDeSortie(compilerJSONObject(), "json/statistiques.json");
    }

    public static void reinitialiser () throws IOException {
     
        JSONObject stat = new JSONObject();
        JSONObject catStat = TraitementJSON.obtenirTabStat();
        
        Iterator<String> keys = catStat.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            stat.put(key, 0);
        }
        
        JSONObject cat = TraitementJSON.obtenirTabCategories();
        cat.remove("sous-catégories");
        stat.accumulate("activités_valides_par_catégories", cat);
        stat.accumulate("déclarations_valides_et_complètes_par_ordre_professionnelle", TraitementJSON.obtenirTabOrdre());
        stat.accumulate("déclarations_valides_et_incomplètes_par_ordre_professionnelle", TraitementJSON.obtenirTabOrdre());
        
        TraitementJSON.ecritureDeSortie(stat, "json/statistiques.json");
    }
    
    public void incrementerStat (String stat) {
        
        int valeur = statistique.getInt(stat);
        valeur++;
        statistique.put(stat, valeur);
    }
    
    public void incrementerCategorie (String categorie) {
        
        int valeur = activitesValidesParCategories.getInt(categorie);
        valeur++;
        activitesValidesParCategories.put(categorie, valeur);
    }
    
    public void incrementerDeclarationComplete (String ordre) {
        
        int valeur = activitesValidesEtCompletesParOrdre.getInt(ordre);
        valeur++;
        activitesValidesEtCompletesParOrdre.put(ordre, valeur);
    }
    
    public void incrementerDeclarationIncomplete (String ordre) {
        
        int valeur = activitesValidesEtIncompletesParOrdre.getInt(ordre);
        valeur++;
        activitesValidesEtIncompletesParOrdre.put(ordre, valeur);
    }
    
    public  JSONObject compilerJSONObject () {
        
        JSONObject stat = new JSONObject();
        
        stat.put("déclarations_traitées", statistique.getInt("déclarations_traitées"));
        stat.put("déclarations_complètes", statistique.getInt("déclarations_complètes"));
        stat.put("déclarations_invalides", statistique.getInt("déclarations_invalides"));
        stat.put("déclarations_hommes", statistique.getInt("déclarations_hommes"));
        stat.put("déclarations_femmes", statistique.getInt("déclarations_femmes"));
        stat.put("déclarations_sexe_inconnu", statistique.getInt("déclarations_sexe_inconnu"));
        stat.put("activités_valides", statistique.getInt("activités_valides"));
        stat.put("déclaration_permis_invalides", statistique.getInt("déclaration_permis_invalides"));
        activitesValidesParCategories.remove("sous-catégories");
        stat.accumulate("activités_valides_par_catégories", activitesValidesParCategories);
        stat.accumulate("déclarations_valides_et_complètes_par_ordre_professionnelle", activitesValidesEtCompletesParOrdre);
        stat.accumulate("déclarations_valides_et_incomplètes_par_ordre_professionnelle", activitesValidesEtIncompletesParOrdre);
        
        return stat;
    }
    
}
