/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main (String[] args) throws IOException{

        // Declaration des variables locales
        String fichierEntre = args[0];
        String fichierSortie = args[1];
        String emplacementEntree = "json/" + fichierEntre;
        String emplacementSortie = "json/" + fichierSortie;
        JSONObject resultat;
        
        Declaration declaration;
        LectureJSON lecture = new LectureJSON(emplacementEntree);
        
        try{
        lecture.lireFichiersJSON();
        declaration = new Declaration(lecture);
        resultat = declaration.valider();
        ecritureDeSortie(resultat, emplacementSortie);
        } catch(Exception e){
            resultat = new JSONObject();
            resultat.accumulate("Erreur", "Le fichier d'entrée est invalide, le cycle est incomplet");
            ecritureDeSortie(resultat, emplacementSortie);
            System.out.println(e);
        }
    }

    private static void ecritureDeSortie (JSONObject resultat, String emplacement) throws IOException {
        
        try (FileWriter ecrire = new FileWriter(emplacement)){
            ecrire.write(resultat.toString(2));
        }
    }
    
}
