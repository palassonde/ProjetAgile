/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projetsessioninf2015;

import java.io.FileWriter;
import java.io.IOException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 *
 * @author achille
 */
public class ProjetSessionINF2015 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
    private static JSONObject obtenirJsoObject()throws IOException{
        
         String lecteur = FileReader.loadFileIntoString("json/membre.json", "UTF-8");
         return (JSONObject) JSONSerializer.toJSON(lecteur);
    }
    
    private static void ecriture(JSONObject obt) throws IOException{
        FileWriter ecrire=new FileWriter("json/resultat.json");
        ecrire.write(obt.toString(2));
    }
    
}
