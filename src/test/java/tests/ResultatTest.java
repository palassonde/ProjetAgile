/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tests;

import model.Resultat;
import net.sf.json.JSONArray;
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
public class ResultatTest {
    
    public ResultatTest() {
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
     * Test of ajoutErreur method, of class Resultat.
     */
    @Test
    public void testAjoutErreur() {
        System.out.println("ajoutErreur");
        String msg = "";
        Resultat instance = new Resultat();
        instance.ajoutErreur(msg);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setIncomplet method, of class Resultat.
     */
    @Test
    public void testSetIncomplet() {
        System.out.println("setIncomplet");
        Resultat instance = new Resultat();
        instance.setIncomplet();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setInvalide method, of class Resultat.
     */
    @Test
    public void testSetInvalide() {
        System.out.println("setInvalide");
        Resultat instance = new Resultat();
        instance.setInvalide();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getErreurs method, of class Resultat.
     */
    @Test
    public void testGetErreurs() {
        System.out.println("getErreurs");
        Resultat instance = new Resultat();
        JSONArray expResult = null;
        JSONArray result = instance.getErreurs();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isComplet method, of class Resultat.
     */
    @Test
    public void testIsComplet() {
        System.out.println("isComplet");
        Resultat instance = new Resultat();
        boolean expResult = false;
        boolean result = instance.isComplet();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isValidite method, of class Resultat.
     */
    @Test
    public void testIsValidite() {
        System.out.println("isValidite");
        Resultat instance = new Resultat();
        boolean expResult = false;
        boolean result = instance.isValidite();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
