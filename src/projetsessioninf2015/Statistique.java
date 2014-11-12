/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projetsessioninf2015;

import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONObject;

/**
 *
 * @author achille
 * 
 */
public class Statistique {
    
    public int nbrTotalDeclarationTraite;
    public int nbrTotalDeclarationComplete;
    public int nbrTotalDeclarationInvalide;
    public int nbrTotalDeclarationHomme;
    public int nbrTotalDeclarationFemme;
    public int nbrTotalDeclarationSexeIconnu;
    public int nbrTotalActiviteValide;
    
    Map <String, Integer> activiteValideParCategorie = new HashMap<>();
    LectureJSON lecture;
    JSONObject statistique;
     
     public Statistique (LectureJSON lecture) {
         
         statistique = lecture.statistiques;
         nbrTotalDeclarationTraite = lecture.statistiques.getInt("déclarations_traitées");
         nbrTotalDeclarationComplete = lecture.statistiques.getInt("déclarations_complètes");
         nbrTotalDeclarationInvalide =lecture.statistiques.getInt("déclarations_invalides");
         nbrTotalDeclarationHomme = lecture.statistiques.getInt("déclarations_hommes");
         nbrTotalDeclarationFemme = lecture.statistiques.getInt("déclarations_femmes");
         nbrTotalDeclarationSexeIconnu = lecture.statistiques.getInt("déclarations_sexe_inconnu");
         nbrTotalActiviteValide = lecture.statistiques.getInt("activités_valides");
         this.lecture = lecture;
     }
     
    public void creerCategories () {
        
        for (int i = 0; i < lecture.listeTouteCategories.size(); i++){
            activiteValideParCategorie.put(lecture.listeTouteCategories.getString(i), 0);
            
        }
    }

    void afficher () {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

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
     
     
}
