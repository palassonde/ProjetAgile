/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tests;

import model.ExigencesOrdre;
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
 * @author giresseho
 */
public class ExigencesOrdreTest {
    
    public ExigencesOrdreTest() {
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
     * Test of getCyclesSupportes method, of class ExigencesOrdre.
     */
    @Test
    public void testGetCyclesSupportes() {
        System.out.println("getCyclesSupportes");
        ExigencesOrdre instance = null;
        JSONArray expResult = null;
        JSONArray result = instance.getCyclesSupportes();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCategories method, of class ExigencesOrdre.
     */
    @Test
    public void testGetCategories() {
        System.out.println("getCategories");
        ExigencesOrdre instance = null;
        JSONObject expResult = null;
        JSONObject result = instance.getCategories();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSousCategories method, of class ExigencesOrdre.
     */
    @Test
    public void testGetSousCategories() {
        System.out.println("getSousCategories");
        ExigencesOrdre instance = null;
        JSONArray expResult = null;
        JSONArray result = instance.getSousCategories();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNormePermis method, of class ExigencesOrdre.
     */
    @Test
    public void testGetNormePermis() {
        System.out.println("getNormePermis");
        ExigencesOrdre instance = null;
        String expResult = "";
        String result = instance.getNormePermis();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getOrdre method, of class ExigencesOrdre.
     */
    @Test
    public void testGetOrdre() {
        System.out.println("getOrdre");
        ExigencesOrdre instance = null;
        String expResult = "";
        String result = instance.getOrdre();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHeuresMinParCategories method, of class ExigencesOrdre.
     */
    @Test
    public void testGetHeuresMinParCategories() {
        System.out.println("getHeuresMinParCategories");
        ExigencesOrdre instance = null;
        JSONObject expResult = null;
        JSONObject result = instance.getHeuresMinParCategories();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHeuresMaxParCategories method, of class ExigencesOrdre.
     */
    @Test
    public void testGetHeuresMaxParCategories() {
        System.out.println("getHeuresMaxParCategories");
        ExigencesOrdre instance = null;
        JSONObject expResult = null;
        JSONObject result = instance.getHeuresMaxParCategories();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHeuresCyclePrecedent method, of class ExigencesOrdre.
     */
    @Test
    public void testGetHeuresCyclePrecedent() {
        System.out.println("getHeuresCyclePrecedent");
        ExigencesOrdre instance = null;
        int expResult = 0;
        int result = instance.getHeuresCyclePrecedent();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHeuresMinimum method, of class ExigencesOrdre.
     */
    @Test
    public void testGetHeuresMinimum() {
        System.out.println("getHeuresMinimum");
        ExigencesOrdre instance = null;
        int expResult = 0;
        int result = instance.getHeuresMinimum();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
