

package model;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author giresseho
 */
public class DeclarationTest {
    private  Declaration declaration;
   
    @Before
    public void setUp () throws Exception {
        
        declaration = new Declaration("json/declaration.json");
    }
    
    @After
    public void tearDown () {
        
        declaration = null;
    }

    /**
     * Test of getSexe method, of class Declaration.
     */
    @Test
    public void testGetSexe () {
        
        int resultat = 2;
        assertEquals("resultat incorrect",declaration.getSexe(),resultat);
    }

    /**
     * Test of getNom method, of class Declaration.
     */
    @Test
    public void testGetNom () {
      
      
      assertEquals(declaration.getNom(),"Kant");
    }

    /**
     * Test of getPrenom method, of class Declaration.
     */
    @Test
    public void testGetPrenom () {
        
      assertEquals(declaration.getPrenom(),"Emmanuel");  
    }

    /**
     * Test of getOrdre method, of class Declaration.
     */
    @Test
    public void testGetOrdre () {
       
        assertEquals(declaration.getOrdre(),"podiatres"); 
    }

    /**
     * Test of getCycle method, of class Declaration.
     */
    @Test
    public void testGetCycle () {
       
        assertEquals(declaration.getCycle(),"2013-2016");
    }

    /**
     * Test of getNumeroPermis method, of class Declaration.
     */
    @Test
    public void testGetNumeroPermis () {
        
        assertEquals(declaration.getNumeroPermis(),"56789");
    }

    /**
     * Test of getHeuresCyclePrecedent method, of class Declaration.
     */
    @Test
    public void testGetHeuresCyclePrecedent () {
        
       assertEquals(declaration.getHeuresCyclePrecedent(),0);
    }

    /**
     * Test of getActivites method, of class Declaration.
     */
    @Test
    public void testGetActivites () {
       
      assertEquals(declaration.getActivites().get(0).getCategorie(),"cours");
    }

    /**
     * Test of isValide method, of class Declaration.
     */
    @Test
    public void testIsValide () {
        
       assertEquals(declaration.isValide(),false);
    }

  
    
}
