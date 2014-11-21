/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import net.sf.json.JSONArray;

/**
 *
 * @author palass
 */
class Resultat {
    
    private final JSONArray erreurs;
    private boolean complet;
    private boolean validite;
    
    public Resultat () {
        
        erreurs = new JSONArray();
        complet = true;
        validite = true;
    }
    
    public void ajoutErreur(String msg){
        
        erreurs.add(msg);
    }
    
    public void setIncomplet(){
        
        this.complet = false;
    }
    
    public void setInvalide(){
        
        this.validite = false;
    }
          
}
