/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author niklas
 */
public class TestMain {

    public static void main(String[] args) {
        
        
        Admin admin  = Database.getAdmin(1001);
        
        admin.getUsername();
        
        Database.printDatabaseTable("all");
        
        System.exit(0);
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
