
package projetsessioninf2015;

import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONObject;

/**
 *
 * @author Achille
 * @author Pierre-Alexandre
 * @author Gires
 */
public class Statistique {
    
    public int nbrTotalDeclarationTraite;
    public int nbrTotalDeclarationComplete;
    public int nbrTotalDeclarationInvalide;
    public int nbrTotalDeclarationHomme;
    public int nbrTotalDeclarationFemme;
    public int nbrTotalDeclarationSexeIconnu;
    public int nbrTotalActiviteValide;
    public String argument;
    
    Map <String, Integer> activiteValideParCategorie = new HashMap<>();
    LectureJSON lecture;
    JSONObject statistique;
    JSONObject statistiqueValeurNul;
    JSONObject activitesValides;
    
     // lecture des informations pour l'affichage 
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

    Statistique(JSONObject fichierStatistique) {
        
        statistique = fichierStatistique;
        activitesValides = statistique.getJSONObject("activités_valides_par_catégories");
        
    }
    
   
     // création de la catégorie
    public void creerCategories () {
        
        for (int i = 0; i < lecture.listeTouteCategories.size(); i++){
            activiteValideParCategorie.put(lecture.listeTouteCategories.getString(i), 0);
            
        }
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
    
}
