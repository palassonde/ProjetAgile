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
    
  private Validation valide;
    @Before
    public void setUp() {
        
        valide = new Validation();
    }
    
    @After
    public void tearDown() {
        
        valide = null;
    }

    /**
     * Test of validerSexe method, of class Validation.
     */
    @Test
    public void testValiderSexe() throws Exception {
        
        int sexe = 0;
        boolean expResult = true;
        boolean result = Validation.validerSexe(sexe);
        assertEquals(expResult, result);
        
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
        
    }

    /**
     * Test of validerPermis method, of class Validation.
     */
    @Test
    public void testValiderPermis() throws Exception {
        
        String norme = "uft-8";
        String permis = "45676";
        boolean expResult = false;
        boolean result = Validation.validerPermis(norme, permis);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of validerCycle method, of class Validation.
     */
    @Test
    public void testValiderCycle() {
        
        JSONArray cyclesSupportes = null;
        String cycle = "";
        boolean expResult = false;
        boolean result = Validation.validerCycle(cyclesSupportes, cycle);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of validerFormatDate method, of class Validation.
     */
    @Test
    public void testValiderFormatDate() {
        
        String date = "";
        boolean expResult = false;
        boolean result = Validation.validerFormatDate(date);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of validerDateActivite method, of class Validation.
     */
    @Test
    public void testValiderDateActivite() throws Exception {
       
        JSONArray cyclesSupportes = null;
        Date date = null;
        String cycle = null;
        boolean expResult = false;
        boolean result = Validation.validerDateActivite(cyclesSupportes, date, cycle);
        assertEquals(expResult, result);
        
    }
    
}
