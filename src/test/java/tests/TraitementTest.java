

package tests;

import model.Traitement;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author giresseho
 */
public class TraitementTest {
    
    public TraitementTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of ecrireResultat method, of class Traitement.
     */
    @Test
    public void testEcrireResultat() throws Exception {
        System.out.println("ecrireResultat");
        String fichierSortie = "";
        Traitement instance = null;
        instance.ecrireResultat(fichierSortie);
        fail("The test case is a prototype.");
    }

    /**
     * Test of produireResultat method, of class Traitement.
     */
    @Test
    public void testProduireResultat() throws Exception {
        System.out.println("produireResultat");
        Traitement instance = null;
        instance.produireResultat();
        fail("The test case is a prototype.");
    }

    /**
     * Test of ecrireStatistique method, of class Traitement.
     */
    @Test
    public void testEcrireStatistique() throws Exception {
        System.out.println("ecrireStatistique");
        Traitement instance = null;
        instance.ecrireStatistique();
        fail("The test case is a prototype.");
    }

    /**
     * Test of compilerStatistique method, of class Traitement.
     */
    @Test
    public void testCompilerStatistique() throws Exception {
        System.out.println("compilerStatistique");
        Traitement instance = null;
        instance.compilerStatistique();
        fail("The test case is a prototype.");
    }
    
}
