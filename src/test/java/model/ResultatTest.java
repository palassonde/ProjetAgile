

package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author giresseho
 */
public class ResultatTest {
    
    private Resultat leResultat;
    
    @Before
    public void setUp () {
        
       leResultat = new Resultat(); 
    }
    
    @After
    public void tearDown () {
        
        leResultat = null;
    }

    /**
     * Test of ajoutErreur method, of class Resultat.
     */
    @Test
    public void testAjoutErreur () {
        
        leResultat.ajoutErreur("invalide");
        assertFalse(leResultat.getErreurs().isEmpty());
    }

  

    /**
     * Test of getErreurs method, of class Resultat.
     */
    @Test
    public void testGetErreurs () {
        
         leResultat.ajoutErreur("invalide");
         assertEquals(leResultat.getErreurs().getString(0),"invalide");
    }

    /**
     * Test of isComplet method, of class Resultat.
     */
    @Test
    public void testIsComplet () {
        
        assertEquals(leResultat.isComplet(),true);
    }

    /**
     * Test of isValidite method, of class Resultat.
     */
    @Test
    public void testIsValidite () {
       
        assertEquals(leResultat.isValide(),true);
    }
    
}
