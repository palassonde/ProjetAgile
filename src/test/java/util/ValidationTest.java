/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Date;
import model.Activite;
import net.sf.json.JSONArray;
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
public class ValidationTest {
    
    public ValidationTest() {
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
     * Test of validerSexe method, of class Validation.
     */
    @Test
    public void testValiderSexe() throws Exception {
        System.out.println("validerSexe");
        int sexe = 0;
        boolean expResult = false;
        boolean result = Validation.validerSexe(sexe);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of validerHeuresActivite method, of class Validation.
     */
    @Test
    public void testValiderHeuresActivite() throws Exception {
        System.out.println("validerHeuresActivite");
        Activite activite = null;
        boolean expResult = false;
        boolean result = Validation.validerHeuresActivite(activite);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of validerDescriptionActivite method, of class Validation.
     */
    @Test
    public void testValiderDescriptionActivite() {
        System.out.println("validerDescriptionActivite");
        Activite activite = null;
        boolean expResult = false;
        boolean result = Validation.validerDescriptionActivite(activite);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of validerPermis method, of class Validation.
     */
    @Test
    public void testValiderPermis() throws Exception {
        System.out.println("validerPermis");
        String norme = "";
        String permis = "";
        boolean expResult = false;
        boolean result = Validation.validerPermis(norme, permis);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of validerCycle method, of class Validation.
     */
    @Test
    public void testValiderCycle() {
        System.out.println("validerCycle");
        JSONArray cyclesSupportes = null;
        String cycle = "";
        boolean expResult = false;
        boolean result = Validation.validerCycle(cyclesSupportes, cycle);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of validerFormatDate method, of class Validation.
     */
    @Test
    public void testValiderFormatDate() {
        System.out.println("validerFormatDate");
        String date = "";
        boolean expResult = false;
        boolean result = Validation.validerFormatDate(date);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of validerDateActivite method, of class Validation.
     */
    @Test
    public void testValiderDateActivite() throws Exception {
        System.out.println("validerDateActivite");
        JSONArray cyclesSupportes = null;
        Date date = null;
        boolean expResult = false;
        boolean result = Validation.validerDateActivite(cyclesSupportes, date);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
