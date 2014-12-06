
package model;

import net.sf.json.JSONArray;

/**
 *
 * @author palass
 */
public class Resultat {
    
    private final JSONArray erreurs;
    private boolean complet;
    private boolean validite;
    
    public Resultat () {
        
        erreurs = new JSONArray();
        complet = true;
        validite = true;
    }
    
    public void ajoutErreur (String msg) {
        
        getErreurs().add(msg);
    }
    
    public void setIncomplet () {
        
        this.complet = false;
    }
    
    public void setInvalide () {
        
        this.validite = false;
    }

    /**
     * @return the erreurs
     */
    public JSONArray getErreurs () {
        return erreurs;
    }

    /**
     * @return the complet
     */
    public boolean isComplet () {
        return complet;
    }

    /**
     * @return the validite
     */
    public boolean isValidite () {
        return validite;
    }
    
    
          
}
