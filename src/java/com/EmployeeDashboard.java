/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Niklas Sarup-Lytzen ID: 18036644
 * *
 */
import model.*;

@WebServlet("/employeeDashboard")

public class EmployeeDashboard extends HttpServlet {

    int currentUserID;
    HttpSession session;
    Cookie cookie;
    Cookie[] cookies;
    Database database;
    RequestDispatcher view;
    User currentUser;
    String loggedInAs = "";
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
        // Set view
        view = getServletContext().getRequestDispatcher("/WEB-INF/employeeDashboard.jsp");

        //Get session
        session = request.getSession();
        
        

        try {
            if (Boolean.parseBoolean(String.valueOf(request.getAttribute("resetDates")))) {
                consultations = database.getAllConsultationsWhereIDIs(currentUserID);
                fromDate = null;
                toDate = null;
            } else {

                fromDate = Date.valueOf(request.getParameter("fromDate"));
                toDate = Date.valueOf(request.getParameter("toDate"));

                consultations = database.getAllConsultationsWhereIDIsFromTo(currentUserID, fromDate, toDate);

            }

            session.setAttribute("consultations", consultations);
            view.forward(request, response);

        } catch (Exception e) {

        }

        try {
            int choice = Integer.parseInt(request.getParameterValues("insuranceSelection")[0]);
            boolean insured = true;

            switch (choice) {
                case 2:
                    session.setAttribute("patients", database.getAllPatients());
                    break;
                case 1:
                    insured = false;
                default:
                    session.setAttribute("patients", database.getAllPatientsWhereIs("insured", String.valueOf(insured)));
            }

            session.setAttribute("insuranceSelection", null);
            view.forward(request, response);

        } catch (Exception ex) {
            message = "";
        }

        // Get attributes
        currentUserID = (int) session.getAttribute("userID");
        database = (Database) getServletContext().getAttribute("database");

        // Set currentUser
        if (20000 <= currentUserID && currentUserID <= 29999) {
            currentUser = database.getDoctor(currentUserID);
            loggedInAs = "doctor";
        } else {
            currentUser = database.getNurse(currentUserID);
            loggedInAs = "nurse";
        }

        Cookie[] cookies = request.getCookies();

        // Set cookie
        //cookie.setMaxAge(20 * 60);
        //response.addCookie(cookie);
        // Get database lists
        consultations = database.getAllConsultationsWhereIDIs(currentUserID);
        patients = database.getAllPatients();

        // Set / update attributes for currentSession
        synchronized (session) {
            session.setAttribute("consultations", consultations);
            session.setAttribute("patients", patients);
            session.setAttribute("currentUser", currentUser);
            session.setAttribute("message", message);
            session.setAttribute("loggedInAs", loggedInAs);

        }

        //response.sendRedirect(request.getContextPath() + "/employeeDashboard.jsp");
        view.forward(request, response);

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
