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

        ArrayList<String> arr = new ArrayList();
        ArrayList<String> arr2 = new ArrayList();
        ArrayList<Integer> arr3 = new ArrayList();
        ArrayList<String> arr4 = new ArrayList();

        try {
            arr = Database.getPasswords();
        } catch (SQLException ex) {
            Logger.getLogger(TestMain.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            arr2 = Database.getUsernames();
        } catch (SQLException ex) {
            Logger.getLogger(TestMain.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            arr3 = Database.getIDs();
        } catch (SQLException ex) {
            Logger.getLogger(TestMain.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 0; i < arr.size(); i++) {
            
            arr4.clear();
            
            arr4 = Database.getHashedPasswordString(arr.get(i));
            
            System.out.println("ID: " + arr3.get(i));
            System.out.println("Username: " + arr2.get(i));
            System.out.println("Password: " + arr.get(i));
            System.out.println("Salt (hex): " + arr4.get(0));
            System.out.println("Hash: " + arr4.get(1));
            System.out.println("\n");

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
