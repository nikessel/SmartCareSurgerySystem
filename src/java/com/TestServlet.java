/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.*;

/**
 *
 * @author niklas
 */
public class TestServlet extends HttpServlet {
   private String message;
   private User currentUser;
   private Patient currentPatient;
   private Nurse currentNurse;
   private int currentUserID;

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
        
        currentPatient = new Patient();
        
        response.setContentType("text/html;charset=UTF-8");
        
        currentUserID = Database.getUserID("testnurse", "password");
        
        System.out.println(currentUserID);
        
        // Check if user is a patient
        if (30000 <= currentUserID && currentUserID <= 39999) {
            currentNurse = Database.getNurse(currentUserID);
            
            message = "ID: " + currentUserID + ", first name: " + currentNurse.getFirstName() + ", sur name: " + currentNurse.getSurName();
        }
        
        else if (40000 <= currentUserID && currentUserID <= 49999) {
            currentPatient = Database.getPatient(currentUserID);
            
            message = "ID: " + currentUserID + ", first name: " + currentPatient.getFirstName() + ", sur name: " + currentPatient.getSurName();
        }
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet TestServlet </title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>" + message + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
