/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.ArrayList;
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
public class DeclarationTest {
    
    public DeclarationTest() {
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
     * Test of getSexe method, of class Declaration.
     */
    @Test
    public void testGetSexe() {
        System.out.println("getSexe");
        Declaration instance = null;
        int expResult = 0;
        int result = instance.getSexe();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNom method, of class Declaration.
     */
    @Test
    public void testGetNom() {
        System.out.println("getNom");
        Declaration instance = null;
        String expResult = "";
        String result = instance.getNom();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPrenom method, of class Declaration.
     */
    @Test
    public void testGetPrenom() {
        System.out.println("getPrenom");
        Declaration instance = null;
        String expResult = "";
        String result = instance.getPrenom();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getOrdre method, of class Declaration.
     */
    @Test
    public void testGetOrdre() {
        System.out.println("getOrdre");
        Declaration instance = null;
        String expResult = "";
        String result = instance.getOrdre();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCycle method, of class Declaration.
     */
    @Test
    public void testGetCycle() {
        System.out.println("getCycle");
        Declaration instance = null;
        String expResult = "";
        String result = instance.getCycle();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNumeroPermis method, of class Declaration.
     */
    @Test
    public void testGetNumeroPermis() {
        System.out.println("getNumeroPermis");
        Declaration instance = null;
        String expResult = "";
        String result = instance.getNumeroPermis();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHeuresCyclePrecedent method, of class Declaration.
     */
    @Test
    public void testGetHeuresCyclePrecedent() {
        System.out.println("getHeuresCyclePrecedent");
        Declaration instance = null;
        int expResult = 0;
        int result = instance.getHeuresCyclePrecedent();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getActivites method, of class Declaration.
     */
    @Test
    public void testGetActivites() {
        System.out.println("getActivites");
        Declaration instance = null;
        ArrayList<Activite> expResult = null;
        ArrayList<Activite> result = instance.getActivites();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isValide method, of class Declaration.
     */
    @Test
    public void testIsValide() {
        System.out.println("isValide");
        Declaration instance = null;
        boolean expResult = false;
        boolean result = instance.isValide();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setInvalide method, of class Declaration.
     */
    @Test
    public void testSetInvalide() {
        System.out.println("setInvalide");
        Declaration instance = null;
        instance.setInvalide();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
