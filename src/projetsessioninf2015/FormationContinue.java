
package projetsessioninf2015;

import java.io.FileWriter;
import java.io.IOException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 *
 * @author Achille
 * @author Pierre-Alexandre
 * @author Gires
 */
public class FormationContinue {

    
    public static void main (String[] args) throws IOException{

        String fichierEntre = args[0];
        String emplacementStatistiques = "json/statistiques.json";
        JSONObject resultat;
        Statistique statistique;
        JSONObject stats;
        stats = obtenirJsonObject(emplacementStatistiques); 
        
        switch(args[0]){
        
        case "-S":
            
            System.out.println(stats.toString(4));
            break;
            
        case "-SR":
            
            statistique = new Statistique(stats);
            ecritureDeSortie(statistique.reinitialise(), emplacementStatistiques);
            System.out.println("Réintialisation effectuée");
            break;
            
        default:
            
            Declaration declaration;
            String fichierSortie = args[1];
            String emplacementEntree = "json/" + fichierEntre;
            String emplacementSortie = "json/" + fichierSortie;
            LectureJSON lecture = new LectureJSON(emplacementEntree);
            
            try{
                
            lecture.lireFichiersJSON();
            declaration = new Declaration(lecture);
            resultat = declaration.valider();
            statistique = declaration.recupererStatistiques();
            ecritureDeSortie(statistique.toJSONObject(), emplacementStatistiques);
            ecritureDeSortie(resultat, emplacementSortie);

            } catch(Exception e){
                
                resultat = new JSONObject();
                resultat.accumulate("Erreur", "Le fichier d'entrée est invalide, le cycle est incomplet");
                ecritureDeSortie(resultat, emplacementSortie);
                System.out.println(e);
            }    
            break;
        }
        
    }
    
// sort le fichier JSON avec toutes les données
    private static void ecritureDeSortie (JSONObject resultat, String emplacement) throws IOException {
        
        try (FileWriter ecrire = new FileWriter(emplacement)){
            ecrire.write(resultat.toString(2));
        }
    }
    private static JSONObject obtenirJsonObject (String emplacement) throws IOException {

        String lecteur = FileReader.loadFileIntoString(emplacement, "UTF-8");
        return (JSONObject) JSONSerializer.toJSON(lecteur);
    }
    

    
}
