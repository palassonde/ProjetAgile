/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import net.sf.json.JSONObject;
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
public class StatistiqueTest {
    
    public StatistiqueTest() {
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
     * Test of afficher method, of class Statistique.
     */
    @Test
    public void testAfficher() throws Exception {
        System.out.println("afficher");
        Statistique.afficher();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ecrire method, of class Statistique.
     */
    @Test
    public void testEcrire() throws Exception {
        System.out.println("ecrire");
        Statistique instance = new Statistique();
        instance.ecrire();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of compiler method, of class Statistique.
     */
    @Test
    public void testCompiler() {
        System.out.println("compiler");
        Statistique.compiler();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of reinitialiser method, of class Statistique.
     */
    @Test
    public void testReinitialiser() throws Exception {
        System.out.println("reinitialiser");
        Statistique.reinitialiser();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of incrementerStat method, of class Statistique.
     */
    @Test
    public void testIncrementerStat() {
        System.out.println("incrementerStat");
        String stat = "";
        Statistique instance = new Statistique();
        instance.incrementerStat(stat);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of incrementerCategorie method, of class Statistique.
     */
    @Test
    public void testIncrementerCategorie() {
        System.out.println("incrementerCategorie");
        String categorie = "";
        Statistique instance = new Statistique();
        instance.incrementerCategorie(categorie);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of incrementerDeclarationComplete method, of class Statistique.
     */
    @Test
    public void testIncrementerDeclarationComplete() {
        System.out.println("incrementerDeclarationComplete");
        String ordre = "";
        Statistique instance = new Statistique();
        instance.incrementerDeclarationComplete(ordre);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of incrementerDeclarationIncomplete method, of class Statistique.
     */
    @Test
    public void testIncrementerDeclarationIncomplete() {
        System.out.println("incrementerDeclarationIncomplete");
        String ordre = "";
        Statistique instance = new Statistique();
        instance.incrementerDeclarationIncomplete(ordre);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of compilerJSONObject method, of class Statistique.
     */
    @Test
    public void testCompilerJSONObject() {
        System.out.println("compilerJSONObject");
        Statistique instance = new Statistique();
        JSONObject expResult = null;
        JSONObject result = instance.compilerJSONObject();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
