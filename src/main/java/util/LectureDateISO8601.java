/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author palass
 */
public class LectureDateISO8601 {
    
    public static Date lire(String input) throws ParseException{
        
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        
        return date.parse(input);
    }
    
}
