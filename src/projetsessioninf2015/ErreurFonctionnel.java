/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetsessioninf2015;

/**
 *
 * @author palass
 */
public class ErreurFonctionnel extends Exception {
    
    public ErreurFonctionnel(String message){
        super("Erreur dans la validation du fichier JSON :" + message);
    }
    
    public ErreurFonctionnel(){
    
        super("Erreur dans la validation du fichier JSON");
    }
    
}
