/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetsessioninf2015;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 *
 * @author Achille
 * @author Pierre-Alexandre
 * @author Gires
 */
public class ProjetSessionINF2015 {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     * @throws java.text.ParseException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {

        // Declaration des variables locales
        String fichierEntre = args[0];
        String fichierSortie = args[1];
        String emplacementEntree = "json/" + fichierEntre;
        String emplacementSortie = "json/" + fichierSortie;
       
        JSONObject fichierDeclaration = obtenirJsonObject(emplacementEntree);
        Declaration declaration = new Declaration(fichierDeclaration);
        JSONObject resultat = declaration.valider();
        
        ecritureDeSortie(resultat, emplacementSortie);
        if(!declaration.validerNumeroPermis()){
            System.out.println("erreur");
        }
    }

    private static JSONObject obtenirJsonObject(String emplacement) throws IOException {

        String lecteur = FileReader.loadFileIntoString(emplacement, "UTF-8");
        return (JSONObject) JSONSerializer.toJSON(lecteur);
    }

    private static void ecritureDeSortie(JSONObject resultat, String emplacement) throws IOException {
        
        FileWriter ecrire = new FileWriter(emplacement);
        ecrire.write(resultat.toString(2));
        ecrire.close();
    }
}
