/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
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
 * @author Niklas Sarup-Lytzen ID: 18036644 *
 */
import model.*;

public class PatientDashboard extends HttpServlet {

    int currentUserID, selectedConsultatantID, selectedHour, selectedMinute,
            selectedYear, selectedMonth, selectedDayOfMonth;
    HttpSession session;
    Cookie cookie;
    Cookie[] cookies;
    Database database;
    String loggedInAs = "";
    RequestDispatcher view;
    User currentUser;
    ArrayList<Consultation> consultations;
    ArrayList<Patient> patients;
    ArrayList<Doctor> doctors;
    ArrayList<Nurse> nurses;
    ArrayList<Object> temp;
    Timestamp timestamp;
    String message, note;
    String[] selectedDate;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private Boolean requestConsultation(HttpServletRequest request) {

        try {
            String[] selectedTime = request.getParameter("selectedTime").split(":");
            selectedDate = request.getParameter("selectedDate").split("-");
            selectedConsultatantID = Integer.parseInt(request.getParameterValues("selectedConsultatantID")[0]);
            note = request.getParameter("note");

            selectedYear = Integer.parseInt(selectedDate[0]);
            selectedMonth = Integer.parseInt(selectedDate[1]);
            selectedDayOfMonth = Integer.parseInt(selectedDate[2]);
            selectedHour = Integer.parseInt(selectedTime[0]);
            selectedMinute = Integer.parseInt(selectedTime[1]);

            timestamp = new Timestamp(selectedYear, selectedMonth, selectedDayOfMonth, selectedHour, selectedMinute);

            Patient patient = (Patient) currentUser;
            Doctor doctor;
            Nurse nurse;

            if (database.isDoctor(selectedConsultatantID)) {
                doctor = database.getDoctor(selectedConsultatantID);
                nurse = database.getNurse(30000);
            } else {
                doctor = database.getDoctor(20000);
                nurse = database.getNurse(selectedConsultatantID);
            }

            Consultation consultation = new Consultation(patient, doctor, nurse, timestamp, note, 10);

            database.addObjectToDatabase(consultation);

        } catch (Exception ex) {
        }
        return false;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        // Set view
        view = getServletContext().getRequestDispatcher("/patientDashboard.jsp");

        doctors = new ArrayList<Doctor>();
        nurses = new ArrayList<Nurse>();

        //Get session
        session = request.getSession();

        // Get attributes
        currentUserID = (int) session.getAttribute("userID");
        database = (Database) getServletContext().getAttribute("database");

        // Set currentUser
        currentUser = database.getPatient(currentUserID);
        loggedInAs = " patient";
        Cookie[] cookies = request.getCookies();

        requestConsultation(request);

        consultations = database.getAllConsultationsWhereIDIs(currentUserID);

        try {
            temp = database.getAllFromDatabase("doctors");
            temp.remove(0);

            for (Object doctor : temp) {
                doctors.add((Doctor) doctor);
            }

            temp = database.getAllFromDatabase("nurses");
            temp.remove(0);

            for (Object nurse : temp) {
                nurses.add((Nurse) nurse);
            }
        } catch (Exception ex) {

        }

        // Set cookie
        //cookie.setMaxAge(20 * 60);
        //response.addCookie(cookie);
        // Set / update attributes for currentSession
        synchronized (session) {
            session.setAttribute("doctors", doctors);
            session.setAttribute("nurses", nurses);
            session.setAttribute("consultations", consultations);
            session.setAttribute("currentUser", currentUser);
            session.setAttribute("message", message);
            session.setAttribute("loggedInAs", loggedInAs);

        }

        //response.sendRedirect(request.getContextPath() + "/patientDashboard.jsp");
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
