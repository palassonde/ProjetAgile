/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetsessioninf2015;

import net.sf.json.JSONArray;

/**
 *retournr true si tout les chants souhaités ont passé tous les test
 * et false dans le cas contraire
 * @author palass
 */
class Resultat {
    
    JSONArray erreurs;
    boolean complet;
    
    public Resultat () {
        
        erreurs = new JSONArray();
        complet = true;
    }
}
