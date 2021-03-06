
package model;

import net.sf.json.JSONArray;

/**
 *
 * @author palass
 */
public class Resultat {
    
    private final JSONArray erreurs;
    private boolean complet;
    private boolean valide;
    
    public Resultat () {
        
        erreurs = new JSONArray();
        complet = true;
        valide = true;
    }
    
    public void ajoutErreur (String msg) {
        
        getErreurs().add(msg);
    }
    
    public void setIncomplet () {
        
        this.complet = false;
    }
    
    public void setInvalide () {
        
        this.valide = false;
        setIncomplet();
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
    public boolean isValide () {
        return valide;
    }
    
    
          
}
