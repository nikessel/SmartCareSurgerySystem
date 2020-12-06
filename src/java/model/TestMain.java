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

        currentUserID = Database.getUserID("testdoctor", "password");
        
        System.out.println(currentUserID);
        
        Database.setPassword(currentUserID, "password2");
        
        currentUserID = Database.getUserID("testdoctor", "password");
        
        System.out.println(currentUserID);
        
        currentUserID = Database.getUserID("testdoctor", "password2");
        
        System.out.println(currentUserID);

        ArrayList<Integer> int_arr = new ArrayList();

        try {
            int_arr = Database.getIDs();
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        System.out.println(int_arr);

//        System.exit(0);

        currentUserID = Database.getUserID("testdoctor", "password");

        System.out.println(currentUserID);

        if (10000 <= currentUserID && currentUserID <= 19999) {
            currentUser = Database.getAdmin(currentUserID);
            System.out.println("Is an admin");
        } else if (20000 <= currentUserID && currentUserID <= 29999) {
            currentUser = Database.getDoctor(currentUserID);
            System.out.println("Is a doctor");
        } else {
            currentUser = new Admin("", "", "", "", true);
            System.out.println("Error: couldn't get user!");
        }

        System.out.println(currentUser);

        Database.printDatabaseTable("all");

        Admin admin = new Admin("AAAAA", "dsd", "dsd", "dsds", true);
        Database.writeObjectToDatabase(admin);

        Doctor doctor = new Doctor("dds", "dsd", "dsd", "dsds", true);
        Database.writeObjectToDatabase(doctor);

        Nurse nurse = new Nurse("dds", "dsd", "dsd", "dsds", true);
        Database.writeObjectToDatabase(nurse);

        Patient patient = new Patient("fsdfs", "dsadsa", "dasdas", "sad", java.sql.Date.valueOf("2000-12-12"), new Address("sdaa", "sad", "sd", "dsaf", "sda", "asd"));
        Database.writeObjectToDatabase(patient);

        currentUserID = Database.getUserID("testnurse", "password");

        System.out.println(currentUserID);

        Database.deleteObjectFromDatabase(Database.getNurse(currentUserID));

        currentUserID = Database.getUserID("testnurse", "password");

        System.out.println(Database.getConsultation(50002));

        Database.printDatabaseTable("all");

    }

}
