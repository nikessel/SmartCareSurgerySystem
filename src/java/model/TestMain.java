/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Niklas Sarup-Lytzen ID: 18036644
 *
 */
public class TestMain {

    public static void main(String[] args) {

        
        
            
        System.out.println(Geocoding.validateGeocode("wl5531ll"));
        
        System.out.println(Geocoding.getAddress());
        System.exit(0);
        
        Database database = new Database();
        
        database.connect();
        
        Patient patient = database.getPatient(40017);
        
        System.out.println(patient.getSurName());
        
  
        database.addPasswordToUser(patient, "hello");
        
        

    }

}
