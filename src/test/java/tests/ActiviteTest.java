/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tests;

import java.util.Date;
import model.Activite;
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
public class ActiviteTest {
    private JSONObject cours ;
    private JSONObject heures;
    private JSONObject getDate;
   
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
     * Test of getHeures method, of class Activite.
     */
    @Test
    public void testGetHeures() {
        System.out.println("getHeures");
        Activite instance;
        instance = null;
        int expResult = 0;
        int result = instance.getHeures();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDescription method, of class Activite.
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        Activite instance;
        instance = new Activite(cours);
        String expResult = "";
        String result = instance.getDescription();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCategorie method, of class Activite.
     */
    @Test
    public void testGetCategorie() {
        System.out.println("getCategorie");
        Activite instance = new Activite(cours);
        String expResult = "";
        String result = instance.getCategorie();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPars
     * @throws java.lang.Exception
     */
    @Test
    public void testGetParsedDate() throws Exception {
        System.out.println("getParsedDate");
        Activite instance;
        JSONObject date;
        date = null;
        instance = new Activite (date);
        Date expResult;
        expResult = new Date ();
        Date result = instance.getParsedDate();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDate method, of class Activite.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetDate() throws Exception {
        System.out.println("getDate");
        Activite instance = new Activite (getDate);
        String expResult = "";
        String result = instance.getDate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isValidite method, of class Activite.
     */
    @Test
    public void testIsValidite() {
        System.out.println("isValidite");
        Activite instance = null;
        boolean expResult = false;
        boolean result = instance.isValidite();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of setInvalide method, of class Activite.
     */
    @Test
    public void testSetInvalide() {
        System.out.println("setInvalide");
        Activite instance = null;
        instance.setInvalide();
        fail("The test case is a prototype.");
    }
    
}
