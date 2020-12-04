/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author niklas
 */
public class TestMain {

    public static void main(String[] args) {
        User currentUser;
        int currentUserID = -2;
        
        
        try {
            currentUserID = Database.getUserID("testadmin", "password");
        } catch (SQLException ex) {
            System.out.println(ex);   
        }
        System.out.println(currentUserID);
            
        if (10000 <= currentUserID && currentUserID <= 19999){
                currentUser = Database.getAdmin(currentUserID);
                System.out.println("Is an admin");
        }
        
        
                

        System.exit(0);
        Admin admin = Database.getAdmin(1001);

        admin.getUsername();

        Database.printDatabaseTable("all");

        // Admin admin = new Admin("AAAAA", "dsd", "dsd", "dsds", true);
        Database.writeObjectToDatabase(admin);

        Doctor doctor = new Doctor("dds", "dsd", "dsd", "dsds", true);
        Database.writeObjectToDatabase(doctor);

        Nurse nurse = new Nurse("dds", "dsd", "dsd", "dsds", true);
        Database.writeObjectToDatabase(nurse);

        Patient patient = new Patient("fsdfs", "dsadsa", "dasdas", "sad", java.sql.Date.valueOf("2000-12-12"), new Address("sdaa", "sad", "sd", "dsaf", "sda", "asd"));
        Database.writeObjectToDatabase(patient);

        Database.deleteObjectFromDatabase(Database.getNurse(3002));

        System.out.println(Database.getConsultation(5005));

        Database.printDatabaseTable("all");

    }

}
