/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Niklas Sarup-Lytzen ID: 18036644
 */
import model.*;

public class EmployeeDashboard extends HttpServlet {

    int currentUserID, checkID;
    HttpSession session;
    boolean isDoctor;
    Cookie cookie;
    Cookie[] cookies;
    Database database;
    RequestDispatcher view;
    User currentUser;
    String loggedInAs = "";
    ArrayList<Consultation> consultations;
    ArrayList<Patient> patients;
    ArrayList<Consultation> pendingConsultations;
    ArrayList<Integer> pendingConsultationIDs;
    Consultation temp;
    String message;
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
    private boolean getBoolean(HttpServletRequest request, String variableName) {
        try {
            if (request.getParameter(variableName).equals("true")) {
                return true;
            }
        } catch (NullPointerException ex) {

        }
        return false;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Set view
        view = getServletContext().getRequestDispatcher("/employeeDashboard.jsp");

        //Get session
        session = request.getSession();

        database = (Database) getServletContext().getAttribute("database");

        // Get attributes
        currentUserID = (int) session.getAttribute("userID");

        // Set currentUser
        if (database.isDoctor(currentUserID)) {
            currentUser = database.getDoctor(currentUserID);
            isDoctor = true;
            loggedInAs = " doctor";
        } else {
            currentUser = database.getNurse(currentUserID);
            isDoctor = false;
            loggedInAs = " nurse";
        }

        try {
            int id = Integer.parseInt(request.getParameterValues("pendingConsultationSelection")[0]);
            boolean approve = Boolean.parseBoolean(request.getParameter("approve"));

            if (approve) {
                database.approveConsultation(id);

            } else {
                database.deleteObjectFromDatabase(id);
            }

        } catch (Exception ex) {
        }

        pendingConsultationIDs = database.getPendingConsultations();
        pendingConsultations = new ArrayList<Consultation>();

        try {

            for (int id : pendingConsultationIDs) {
                temp = database.getConsultation(id);

                if (isDoctor) {
                    checkID = temp.getDoctor().getDoctorID();
                } else {
                    checkID = temp.getNurse().getNurseID();
                }

                if (checkID == currentUserID) {
                    pendingConsultations.add(temp);
                }
            }

        } catch (Exception ex) {

        }

        message = String.valueOf(currentUserID) + "es " + String.valueOf(checkID);

        try {
            int choice = Integer.parseInt(request.getParameterValues("insuranceSelection")[0]);

            switch (choice) {
                case 0:
                    patients = database.getAllPatientsWhereIs("insured", "true");
                    break;
                case 1:
                    patients = database.getAllPatientsWhereIs("insured", "false");
                    break;
                default:
                    patients = database.getAllPatients();
            }

            session.setAttribute("insuranceSelection", null);

        } catch (Exception ex) {
            patients = database.getAllPatients();
        }

        Cookie[] cookies = request.getCookies();

        // Set cookie
        //cookie.setMaxAge(20 * 60);
        //response.addCookie(cookie);
        // Get database lists
        consultations = database.getAllConsultationsWhereIDIs(currentUserID);

        // Set / update attributes for currentSession
        synchronized (session) {
            session.setAttribute("pendingConsultations", pendingConsultations);
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
