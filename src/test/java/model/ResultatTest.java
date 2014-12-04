

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
    
    private Resultat leresultat;
    
    @Before
    public void setUp () {
        
       leresultat = new Resultat(); 
    }
    
    @After
    public void tearDown () {
        
        leresultat = null;
    }

    /**
     * Test of ajoutErreur method, of class Resultat.
     */
    @Test
    public void testAjoutErreur () {
       
        
    }

  

    /**
     * Test of getErreurs method, of class Resultat.
     */
    @Test
    public void testGetErreurs () {
       
        
    }

    /**
     * Test of isComplet method, of class Resultat.
     */
    @Test
    public void testIsComplet () {
        
        assertEquals(leresultat.isComplet(),true);
    }

    /**
     * Test of isValidite method, of class Resultat.
     */
    @Test
    public void testIsValidite () {
       
        assertEquals(leresultat.isValidite(),true);
    }
    
}
