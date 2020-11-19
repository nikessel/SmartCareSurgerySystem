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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Database.Connect();
        Database.initialisePatients();
        
        int length = Database.getPATIENTLIST().size();
        
        for (int i = 0; i < length; i++) {
            System.out.println(Database.getPATIENTLIST().get(i));
        }
    }
    
}
