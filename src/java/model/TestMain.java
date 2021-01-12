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
        
        Doctor doctor = database.getDoctor(20001);
        
        database.addObjectToDatabase(doctor);

        System.out.println(database.getPrice("consultation"));
        
        database.setPrice("consultation", 555);
        
        System.out.println(database.getPrice("consultation"));
        
        


    }
}

  