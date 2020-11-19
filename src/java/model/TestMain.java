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

    public static void print_all_users() {
        int length = Database.getPATIENTLIST().size();

        for (int i = 0; i < length; i++) {
            System.out.println(Database.getPATIENTLIST().get(i));
        }

        length = Database.getDOCTORLIST().size();

        for (int i = 0; i < length; i++) {
            System.out.println(Database.getDOCTORLIST().get(i));
        }

        length = Database.getADMINLIST().size();

        for (int i = 0; i < length; i++) {
            System.out.println(Database.getADMINLIST().get(i));
        }

        length = Database.getNURSELIST().size();

        for (int i = 0; i < length; i++) {
            System.out.println(Database.getNURSELIST().get(i));
        }
    }

    public static void main(String[] args) {
        Database.Connect();
        Database.initialisePatients();
        Database.initialiseDoctors();
        Database.initialiseAdmins();
        Database.initialiseNurses();

        print_all_users();

    }

}
