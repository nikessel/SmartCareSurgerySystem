/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Consultation;
import model.Database;
import model.Patient;
import model.User;

/**
 *
 * @author patdizon
 */
public class PatientDashboard extends HttpServlet {

    int currentUserID;
    HttpSession session;
    Cookie cookie;
    Cookie[] cookies;
    Database database;
    RequestDispatcher view;
    User currentUser;
    List<Consultation> consultations;
    List<Patient> patients;
    String message = "";
    Date fromDate, toDate;
    
    
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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            // Set view
            RequestDispatcher view = getServletContext().getRequestDispatcher("/patientDashboard.jsp");

            //Get session
            session = request.getSession();
            
            currentUserID = (int) request.getAttribute("userID");
            
            currentUser = database.getPatient(currentUserID);

            String username = request.getParameter("username");
            String firstName = request.getParameter("firstName");
            String surname = request.getParameter("Surname");
            String patientID = request.getParameter("patientID");
            String dateOfBirth = request.getParameter("dateOfBirth");
            String address = request.getParameter("address");

            // Set / update attributes for currentSession
            synchronized (session) {
                session.setAttribute("currentUser", currentUser);
                session.setAttribute("userID", currentUserID);

            }
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
