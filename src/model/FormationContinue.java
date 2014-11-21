
package model;

import java.io.IOException;

/**
 *
 * @author Achille
 * @author Pierre-Alexandre
 * @author Gires
 */
public class FormationContinue {

    public static void main (String[] args) throws IOException, Exception{
        
        switch(args[0]){
        
        case "-S":
            
            Statistique.afficher();
            break;
            
        case "-SR":
            
            Statistique.reinitialiser();
            System.out.println("Réintialisation effectuée");
            break;
            
        default:

            String emplacementEntree = "json/" + args[0];
            String emplacementSortie = "json/" + args[1];
            Declaration declaration = new Declaration(emplacementEntree);
            Traitement traitement = new Traitement(declaration);
            
            try{
                traitement.produireResultat(); 
            } catch(Exception e){
                traitement.ecrireResultat(emplacementSortie);
                System.out.println(e);
            }
            finally{
                Statistique.compiler();
                Statistique.ecrire();
            }
            break;
        }   
    }
}
