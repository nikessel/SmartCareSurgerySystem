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

        Database database = new Database();

        database.connect();
        
        Surgery surgery = database.getSurgery(70002);
        
        System.out.println(surgery.getSurgeryTime().getWeekNumber());

        System.out.println(database.getCurrentWeekNumber());
        
        database.printDatabaseTable("all");
    }

}
