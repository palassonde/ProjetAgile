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
        String emplacement1="json/" + fichierEntre;
        String emplacement2="json/listecategories.json";
        String emplacement3="json/" + fichierSortie;
        String laNorme="UTF-8";
        String cycleSupporte = "2012-2014";
        
        JSONArray erreurs = new JSONArray();
        boolean complet = true;
   
        Date dateMax = new Date(114,4,1);
        Date dateMin = new Date(112,4,1);
        
        int nbrheuresTotal = 0;
        int heureGroupeMinimum17=0;
        int heureCatePresentation=0;
        // Lecture du fichier JSON envoyé en entrée dans un string
        
        JSONObject declaration = obtenirJsonObject(emplacement1,laNorme);

        // Lecture de la liste des catégories acceptés
        
        JSONArray listeCategories =obtenirJsonArray(emplacement2,laNorme);
        
        JSONArray activites = declaration.getJSONArray("activites");
        
        String numPermis = declaration.getString("numero_de_permis");
        String cycle = declaration.getString("cycle");
        int heuresCumulees = declaration.getInt("heures_transferees_du_cycle_precedent");
        
        // Traitement du cycle
        if (!cycle.equals(cycleSupporte)){
            complet = false;
            erreurs.add("Aucun autre cycle que " + cycleSupporte + " n'est supporté");
        }
        
        // Pré-Traitement des heures
        if (heuresCumulees > 7){
            heuresCumulees = 7;
            erreurs.add("Le nombre d'heures cumulees ne peut dépasser 7");
        }
        
        // Prend en compte les heures transférés
        nbrheuresTotal += heuresCumulees;
        // Prend en compte les heures transférés dans le critère de verification
        heureGroupeMinimum17+=heuresCumulees;

        // Traitement des activités
        int nbActivites = activites.size();
        
        JSONObject activite;
        String categorie;
        int heures;
        Date date;
        
        // Regarde chaque catégorie et traite les données correspondants
        for(int i=0; i < nbActivites; i++){
            
            activite = activites.getJSONObject(i);
            categorie = activite.getString("categorie");
            heures = activite.getInt("heures");
            date = ISO8601DateParser.parse(activite.getString("date"));
            
            // Vérifie si est dans la liste de contagégories
            if (listeCategories.contains(categorie)&&heures>0){
                
                // Vérifie si lactivité est effectué a la bonne echeance
                if(validerDate(date,dateMax,dateMin)){
                    
                    if(validationGroupeMinimum17Heures(categorie))
                        heureGroupeMinimum17+=heures;
                
                    if(validationDeCatePresentation(categorie))
                        heureCatePresentation+=heures;
                    
                    nbrheuresTotal += heures;
                }
                else{
                    erreurs.add("L'activité " + categorie + " n'a pas été complété dans l'échéance requise");
                }
            }
            else{
                erreurs.add("L'activité " + categorie + " est dans une catégorie non reconnue. Elle a été ignorée.");
            }
        }
        
        // Vérification critère de minimum aa
        if(heureGroupeMinimum17 < 17){
            complet = false;
            erreurs.add("vous avez fait moins de "+ Integer.toString(17)+ " dans les activités: cours,"
                    + "atélier,colloque,séminaire,conférence et lecture dirigée");
        }
        
        // retranche les heures comptabiliser en trop dans la categorie presentation
        if(heureCatePresentation > 23){
                    
            nbrheuresTotal -= heureCatePresentation - 23;
        }
               
        if (nbrheuresTotal < 40){
            complet = false;
            int difference = 40 - nbrheuresTotal;
            erreurs.add("Il vous manque "+Integer.toString(difference)+" de formation pour compléter la formation");
        }
        
        //Traitement du resultat et ecriture dans un fichier json
        JSONObject resultats = new JSONObject();
        
        resultats.accumulate("complet", complet);
        resultats.accumulate("erreurs", erreurs);
        
        ecritureDeSortie(resultats,emplacement3);
    }
    
    private static JSONObject obtenirJsonObject(String emplacement,String norme)throws IOException{
        
         String lecteur = FileReader.loadFileIntoString(emplacement,norme);
         return (JSONObject) JSONSerializer.toJSON(lecteur);
    }
     private static JSONArray obtenirJsonArray(String emplacement,String norme)throws IOException{
        
         String lecteur = FileReader.loadFileIntoString(emplacement,norme);
         return JSONArray.fromObject(lecteur);
    }
    
    private static void ecritureDeSortie(JSONObject obt,String emplacement) throws IOException{
        FileWriter ecrire = new FileWriter(emplacement);
        ecrire.write(obt.toString(2));
        ecrire.close();
    }
    private static boolean validationGroupeMinimum17Heures(String cat){
        boolean valide=false;
        
        switch (cat) {
            case "cours":
                valide=true;
                break; 
            case "atelier":
                valide=true;
                break;
            case "séminaire":
                valide=true;
                break;    
            case "colloque":
                valide=true;
                break; 
            case "conférence":
                valide=true;
                break;
            case "lecture dirigée":
                valide=true;
                break;    
        }
        return valide;
    }
    
    private static boolean validationDeCatePresentation(String cat){
           boolean valide=false;
           if(cat.equals("présentation")){
               valide=true;
           }
           return valide;
    }
    
    private static boolean validerDate(Date date, Date dateMax, Date dateMin){
            
        return date.after(dateMin) && date.before(dateMax);
    }
}
