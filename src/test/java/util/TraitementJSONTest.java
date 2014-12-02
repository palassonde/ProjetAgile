/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import model.Resultat;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author User
 */
public class TraitementJSONTest {
    
    public TraitementJSONTest() {
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
     * Test of obtenirJSONObject method, of class TraitementJSON.
     */
    @Test
    public void testObtenirJSONObject() throws Exception {
        System.out.println("obtenirJSONObject");
        String emplacement = "";
        JSONObject expResult = null;
        JSONObject result = TraitementJSON.obtenirJSONObject(emplacement);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of obtenirJSONArray method, of class TraitementJSON.
     */
    @Test
    public void testObtenirJSONArray() throws Exception {
        System.out.println("obtenirJSONArray");
        String emplacement = "";
        JSONArray expResult = null;
        JSONArray result = TraitementJSON.obtenirJSONArray(emplacement);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ecritureDeSortie method, of class TraitementJSON.
     */
    @Test
    public void testEcritureDeSortie() throws Exception {
        System.out.println("ecritureDeSortie");
        JSONObject sortie = null;
        String emplacement = "";
        TraitementJSON.ecritureDeSortie(sortie, emplacement);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of resultatToJSONObject method, of class TraitementJSON.
     */
    @Test
    public void testResultatToJSONObject() {
        System.out.println("resultatToJSONObject");
        Resultat resultat = null;
        JSONObject expResult = null;
        JSONObject result = TraitementJSON.resultatToJSONObject(resultat);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of obtenirTabCategories method, of class TraitementJSON.
     */
    @Test
    public void testObtenirTabCategories() throws Exception {
        System.out.println("obtenirTabCategories");
        JSONObject expResult = null;
        JSONObject result = TraitementJSON.obtenirTabCategories();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of obtenirTabOrdre method, of class TraitementJSON.
     */
    @Test
    public void testObtenirTabOrdre() throws Exception {
        System.out.println("obtenirTabOrdre");
        JSONObject expResult = null;
        JSONObject result = TraitementJSON.obtenirTabOrdre();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of obtenirTabStat method, of class TraitementJSON.
     */
    @Test
    public void testObtenirTabStat() throws Exception {
        System.out.println("obtenirTabStat");
        JSONObject expResult = null;
        JSONObject result = TraitementJSON.obtenirTabStat();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
