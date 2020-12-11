/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.*;

/**
 *
 * @author niklas
 */
@WebServlet("/login")
public class Login extends HttpServlet {

    private String message;
    private User currentUser;
    private Patient currentPatient;
    private Admin currentAdmin;
    private Nurse currentNurse;
    private Doctor currentDoctor;

    private int currentUserID;

    private static final long serialVersionUID = 1L;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            currentUserID = Database.getUserID(username, password);

            if (currentUserID == -2 || currentUserID == -1) {
                message = "Incorrect username or password";
                
                request.setAttribute("error", message);
                
                request.getRequestDispatcher("login.jsp").forward(request, response);
                response.sendRedirect("login.jsp");
            }

            // username  password validation 
            if (currentUserID > 0) {

                // Check user
                // admin
                if (10000 <= currentUserID && currentUserID <= 19999) {
                    currentUser = Database.getAdmin(currentUserID);

                    message = "ID: " + currentUserID + ", first name: "
                            + currentUser.getFirstName() + ", sur name: " + currentUser.getSurName();
                    System.out.println("admin");

                } //doctor
                else if (20000 <= currentUserID && currentUserID <= 29999) {
                    currentUser = new Doctor();
                    currentUser = Database.getDoctor(currentUserID);

                    message = "ID: " + currentUserID + ", first name: "
                            + currentUser.getFirstName() + ", sur name: " + currentUser.getSurName();
                    System.out.println("doctor");
                } //nurse
                //@ - bug in getNurse method needs fix
                else if (30000 <= currentUserID && currentUserID <= 39999) {
                    currentUser = new Nurse();
                    currentUser = Database.getNurse(currentUserID);

                    message = "ID: " + currentUserID + ", first name: "
                            + currentUser.getFirstName() + ", sur name: " + currentUser.getSurName();
                    System.out.println("nurse");
                } //patient
                else if (40000 <= currentUserID && currentUserID <= 49999) {
                    currentUser = new Patient();
                    currentUser = Database.getPatient(currentUserID);

                    message = "ID: " + currentUserID + ", first name: "
                            + currentUser.getFirstName() + ", sur name: " + currentUser.getSurName();
                    System.out.println("patient");
                }
                // show logged in user
                request.setAttribute("name", message);
                request.getRequestDispatcher("/loginsuccess.jsp").forward(request, response);

            }

        } catch (Exception e) {
            //HttpSession session = request.getSession();
            //session.setAttribute("user", username);
            response.sendRedirect("login.jsp");

        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
