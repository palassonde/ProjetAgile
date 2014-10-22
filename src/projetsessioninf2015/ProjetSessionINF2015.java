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
import java.util.Date;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 *
 * @author achille
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
        String emplacement1 = "json/" + fichierEntre;
        String emplacement2 = "json/listecategories.json";
        String emplacement3 = "json/" + fichierSortie;
        String laNorme = "UTF-8";
        String cycleSupporte = "2012-2014";

        JSONArray erreurs = new JSONArray();
        boolean complet = true;

        Date dateMax = new Date(114, 3, 1);
        Date dateMin = new Date(112, 3, 1);

        int nbrheuresTotal = 0;
        int heureGroupeMinimum17 = 0;
        int heurePresentation = 0;
        int heureGroupeDiscussion=0;
        int heureProjetRecherche=0;
        int heureRedaction=0;
        
        // Lecture du fichier JSON envoyé en entrée dans un string

        JSONObject declaration = obtenirJsonObject(emplacement1, laNorme);

        // Lecture de la liste des catégories acceptés
        JSONArray listeCategories = obtenirJsonArray(emplacement2, laNorme);
        
        ArrayList<String> listeSousCategories = new ArrayList<>();
        
        for (int i = 0; i < 6; i++){
            listeSousCategories.add(listeCategories.getString(i));
        }
 
        

        JSONArray activites = declaration.getJSONArray("activites");

        String numPermis = declaration.getString("numero_de_permis");
        String cycle = declaration.getString("cycle");
        int heuresCumulees = declaration.getInt("heures_transferees_du_cycle_precedent");

        // Traitement du cycle
        if (!cycle.equals(cycleSupporte)) {
            complet = false;
            erreurs.add("Aucun autre cycle que " + cycleSupporte + " n'est supporté");
        }

        // Pré-Traitement des heures
        if (heuresCumulees > 7) {
            heuresCumulees = 7;
            erreurs.add("Le nombre d'heures cumulees ne peut dépasser 7");
        } else if (heuresCumulees < 0) {
        
            heuresCumulees=0;
        }

        // Prend en compte les heures transférés
        nbrheuresTotal += heuresCumulees;
        // Prend en compte les heures transférés dans le critère de verification
        heureGroupeMinimum17 += heuresCumulees;

        // Traitement des activités
        int nbActivites = activites.size();

        JSONObject activite;
        String categorie;
        int heures;
        Date date;

        // Regarde chaque catégorie et traite les données correspondants
        for (int i = 0; i < nbActivites; i++) {

            activite = activites.getJSONObject(i);
            categorie = activite.getString("categorie");
            heures = activite.getInt("heures");
            date = ISO8601DateParser.parse(activite.getString("date"));

            // Vérifie si est dans la liste de contagégories
            if (listeCategories.contains(categorie)) {

                if (heures > 0) {
                    // Vérifie si lactivité est effectué a la bonne echeance
                    if (validerDate(date, dateMax, dateMin)) {

                        if (listeSousCategories.contains(categorie))
                            heureGroupeMinimum17 += heures;
                        
                            switch (categorie) {
                                
                                case "présentation":
                                    heurePresentation += heures;
                                    break;
                                case "groupe de discussion":
                                    heureGroupeDiscussion+=heures;
                                    break;
                                case "projet de recherche":
                                    heureProjetRecherche+=heures;
                                    break;
                                case "rédaction professionnelle":
                                    heureRedaction+=heures;
                                    break;                                
                            }
                            nbrheuresTotal += heures;
                    }
                    else
                        erreurs.add("L'activité " + categorie + " n'a pas été complété dans l'échéance requise");
                } 
            } 
            else
                erreurs.add("L'activité " + categorie + " est dans une catégorie non reconnue. Elle a été ignorée.");
        }

        // Vérification critère de minimum aa
        if (heureGroupeMinimum17 < 17) {
            complet = false;
            erreurs.add("vous avez fait moins de " + 17 + " heures dans les activités: cours,"
                    + "atélier,colloque,séminaire,conférence et lecture dirigée");
        }

       
        // retranche les heures comptabiliser en trop dans la categorie presentation
        if (heurePresentation > 23)
            nbrheuresTotal -= (heurePresentation - 23);
        
        if (heureGroupeDiscussion > 17)
            nbrheuresTotal -= (heureGroupeDiscussion - 17);
        
        if (heureProjetRecherche > 23)
            nbrheuresTotal -= (heureProjetRecherche - 23);
        
        if (heureRedaction > 17)
            nbrheuresTotal -= (heureRedaction - 17);

        if (nbrheuresTotal < 40) {
            complet = false;
            int difference = 40 - nbrheuresTotal;
            erreurs.add("Il manque " + difference + " heures de formation pour completer le cycle");
        }

        //Traitement du resultat et ecriture dans un fichier json
        JSONObject resultats = new JSONObject();

        resultats.accumulate("complet", complet);
        resultats.accumulate("erreurs", erreurs);

        ecritureDeSortie(resultats, emplacement3);
    }

    private static JSONObject obtenirJsonObject(String emplacement, String norme) throws IOException {

        String lecteur = FileReader.loadFileIntoString(emplacement, norme);
        return (JSONObject) JSONSerializer.toJSON(lecteur);
    }

    private static JSONArray obtenirJsonArray(String emplacement, String norme) throws IOException {

        String lecteur = FileReader.loadFileIntoString(emplacement, norme);
        return JSONArray.fromObject(lecteur);
    }

    private static void ecritureDeSortie(JSONObject obt, String emplacement) throws IOException {
        FileWriter ecrire = new FileWriter(emplacement);
        ecrire.write(obt.toString(2));
        ecrire.close();
    }
    
    private static boolean validerDate(Date date, Date dateMax, Date dateMin) {
        
        return date.after(dateMin) && date.before(dateMax);
    }
    
    private static boolean validationNumeroPermis (String chaine) {
        boolean laSelection1=false;
        boolean laSelection2=false;
        boolean ok=true;
        if (chaine.length()==5) {
            char leChar=chaine.charAt(0);
        
            switch(leChar){
               case 'A':
                   laSelection1=true; 
                   break;
                case 'R':
                   laSelection1=true; 
                   break;
                case 'S':
                    laSelection1=true; 
                    break;
                case 'Z':
                    laSelection1=true; 
                    break;
            }
            for (int i=1;i<chaine.length();i++) {
                
                if (i>='0'&&i<='9') {
                   laSelection2=true; 
                }else{
                    ok=false;
                }
                    
            }
            laSelection2=laSelection2&&ok;
        }
        
        return laSelection1&&laSelection2;
        
    }
    
}
