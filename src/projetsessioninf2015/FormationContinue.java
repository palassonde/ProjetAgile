
package projetsessioninf2015;

import java.io.FileWriter;
import java.io.IOException;
import net.sf.json.JSONObject;

/**
 *
 * @author Achille
 * @author Pierre-Alexandre
 * @author Gires
 */
public class FormationContinue {

    
    public static void main (String[] args) throws IOException{

        // Declaration des variables locales
        String fichierEntre = args[0];
        String fichierSortie = args[1];
        String emplacementEntree = "json/" + fichierEntre;
        String emplacementSortie = "json/" + fichierSortie;
        String emplacementStatistiques = "json/statistiques.json";
        JSONObject resultat;
        
        Statistique statistique;
        Statistique valeurRemisAZero;
        Declaration declaration;
        LectureJSON lecture = new LectureJSON(emplacementEntree);
        
        try{
        lecture.lireFichiersJSON();
        declaration = new Declaration(lecture);
        resultat = declaration.valider();
        statistique = declaration.recupererStatistiques();
        statistique.afficher();
        ecritureDeSortie(statistique.toJSONObject(), emplacementStatistiques);
        ecritureDeSortie(resultat, emplacementSortie);
        
        } catch(Exception e){
            resultat = new JSONObject();
            resultat.accumulate("Erreur", "Le fichier d'entrée est invalide, le cycle est incomplet");
            ecritureDeSortie(resultat, emplacementSortie);
            System.out.println(e);
        }
        
    }
// sort le fichier JSON avec toutes les données
    private static void ecritureDeSortie (JSONObject resultat, String emplacement) throws IOException {
        
        try (FileWriter ecrire = new FileWriter(emplacement)){
            ecrire.write(resultat.toString(2));
        }
    }
    
}
