/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.mysql.cj.x.protobuf.MysqlxExpect;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Niklas Sarup-Lytzen ID: 18036644
 *
 */
public class TestMain {

    public static void main(String[] args) {

        Database database = new Database();

        database.connect();
        
        System.exit(0);
        database.printDatabaseTable("doctors");

        Nurse nurse = new Nurse("dasd", "dasd", "dasd", true);
        Doctor doctor = new Doctor("sdffsd", "fdsf", "fsdfs", false);
        
        database.addObjectToDatabase(nurse);
        
        database.addObjectToDatabase(doctor);


        ArrayList<Integer> arr = database.getPendingUsers();
        ArrayList<Employee> emp = new ArrayList<>();

        for (int i : arr) {
            System.out.println(i);

            if (database.isNurse(i)) {
                emp.add(database.getNurse(i));
            }
            else if (database.isDoctor(i)) {
                emp.add(database.getDoctor(i));
            }
        }

        for (Employee em : emp) {
            System.out.println(em);
        }
    }

}
