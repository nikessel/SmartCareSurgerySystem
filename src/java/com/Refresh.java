/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import model.Doctor;
import model.Employee;
import model.Nurse;
import model.Patient;
import model.User;

/**
 *
 * @author niklas
 */
public class Refresh extends HttpServlet {

    int currentUserID, selectedConsultatantID, selectedHour, selectedMinute,
            selectedYear, selectedMonth, selectedDayOfMonth, checkID;

    boolean isEmployee, isAdmin, isDoctor, isNurse, isPatient;
    HttpSession session;
    Cookie cookie;
    Cookie[] cookies;
    Database database;
    String loggedInAs, jspContext, viewString;
    RequestDispatcher view;
    User currentUser;
    List<Consultation> consultations;
    List<Patient> patients;
    String message, note;
    Date fromDate, toDate;
    ArrayList<Employee> pendingEmployees;
    ArrayList<Integer> pendingEmployeeIDs;
    ArrayList<Consultation> pendingConsultations;
    ArrayList<Integer> pendingConsultationIDs;
    ArrayList<Object> temps;
    Object temp;
    ArrayList<Doctor> doctors;
    ArrayList<Nurse> nurses;
    Timestamp timestamp;
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
    private void setBooleans() {

        isEmployee = isAdmin = isDoctor = isPatient = false;

        if (database.isEmployee(currentUserID)) {
            isEmployee = true;

            if (database.isAdmin(currentUserID)) {
                isAdmin = true;
            } else if (database.isDoctor(currentUserID)) {
                isDoctor = true;
            } else if (database.isNurse(currentUserID)) {
                isNurse = true;
            }
        } else {
            isPatient = true;
        }
    }

    private void setPendingConsulations() {
        pendingConsultationIDs = database.getPendingConsultations();
        pendingConsultations = new ArrayList<Consultation>();
        Consultation tempConsultation;

        try {

            for (int id : pendingConsultationIDs) {
                tempConsultation = database.getConsultation(id);

                if (database.isDoctor(currentUserID)) {
                    checkID = tempConsultation.getDoctor().getDoctorID();
                } else {
                    checkID = tempConsultation.getNurse().getNurseID();
                }

                if (checkID == currentUserID) {
                    pendingConsultations.add(tempConsultation);
                }
            }

        } catch (Exception ex) {

        }
    }

    private void setPendingEmployees() {
        pendingEmployeeIDs = database.getPendingUsers();
        pendingEmployees = new ArrayList<Employee>();

        try {

            for (int i : pendingEmployeeIDs) {
                if (database.isNurse(i)) {
                    pendingEmployees.add(database.getNurse(i));
                } else if (database.isDoctor(i)) {
                    pendingEmployees.add(database.getDoctor(i));
                }
            }

        } catch (Exception ex) {

        }

    }

    private void approveEmployee(HttpServletRequest request) {
        try {
            int id = Integer.parseInt(request.getParameterValues("pendingEmployeeSelection")[0]);
            boolean approve = Boolean.parseBoolean(request.getParameter("approve"));

            if (approve) {
                database.approveEmployee(id);

            } else {
                database.deleteObjectFromDatabase(id);
            }

        } catch (Exception ex) {
            message = "";
        }
    }

    private void setDoctorsAndNurses() {
        doctors = new ArrayList<>();
        nurses = new ArrayList<>();

        try {
            temps = database.getAllFromDatabase("doctors");
            temps.remove(0);

            for (Object doctor : temps) {
                doctors.add((Doctor) doctor);
            }

            temps = database.getAllFromDatabase("nurses");
            temps.remove(0);

            for (Object nurse : temps) {
                nurses.add((Nurse) nurse);
            }
        } catch (Exception ex) {

        }
    }

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

            timestamp = new Timestamp(selectedYear, selectedMonth, selectedDayOfMonth, selectedHour, selectedMinute, 0, 0);

            Doctor tempDoctor;
            Nurse tempNurse;

            if (database.isDoctor(selectedConsultatantID)) {
                tempDoctor = database.getDoctor(selectedConsultatantID);
                tempNurse = database.getNurse(30000);
            } else {
                tempDoctor = database.getDoctor(20000);
                tempNurse = database.getNurse(selectedConsultatantID);
            }

            Consultation consultation = new Consultation((Patient) currentUser, tempDoctor, tempNurse, timestamp, note, 10);

            message = consultation.toString();
            database.addObjectToDatabase(consultation);

        } catch (Exception ex) {
        }
        return false;
    }

    protected void initialise(int id, HttpSession thisSession, HttpServletRequest request) {
        database = (Database) request.getServletContext().getAttribute("database");
        currentUserID = id;
        setBooleans();

        if (!isAdmin) {
            consultations = database.getAllConsultationsWhereIDIs(currentUserID);
            thisSession.setAttribute("consultations", consultations);
        }

        if (isEmployee) {
            patients = database.getAllPatients();
            thisSession.setAttribute("patients", patients);
        }

        if (isAdmin || isPatient) {
            setDoctorsAndNurses();
            thisSession.setAttribute("doctors", doctors);
            thisSession.setAttribute("nurses", nurses);

            if (isAdmin) {
                currentUser = database.getAdmin(currentUserID);
                loggedInAs = "n admin";
                setPendingEmployees();
                thisSession.setAttribute("pendingEmployees", pendingEmployees);
            } else {
                currentUser = database.getPatient(currentUserID);
                loggedInAs = " patient";
            }
        }

        if (isDoctor || isNurse) {
            setPendingConsulations();
            thisSession.setAttribute("pendingConsultations", pendingConsultations);

            if (isDoctor) {
                currentUser = database.getDoctor(currentUserID);
                loggedInAs = " doctor";
            } else if (isNurse) {
                currentUser = database.getNurse(currentUserID);
                loggedInAs = " nurse";
            }
        }
        
        thisSession.setAttribute("currentUser", currentUser);
        thisSession.setAttribute("loggedInAs", loggedInAs);

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        session = request.getSession(false);
        database = (Database) request.getServletContext().getAttribute("database");
        currentUserID = (Integer) session.getAttribute("userID");
        currentUser = (User) session.getAttribute("currentUser");
        jspContext = String.valueOf(request.getParameter("jspName"));

        if (jspContext.contains("timetable")) {
            try {

                fromDate = Date.valueOf(request.getParameter("fromDate"));
                toDate = Date.valueOf(request.getParameter("toDate"));

                consultations = database.getAllConsultationsWhereIDIsFromTo(currentUserID, fromDate, toDate);

            } catch (Exception e) {
                consultations = database.getAllConsultationsWhereIDIs(currentUserID);
            }
            session.setAttribute("consultations", consultations);

        } else if (jspContext.contains("pendingConsultations")) {

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

            setPendingConsulations();
            session.setAttribute("pendingConsultations", pendingConsultations);

        } else if (jspContext.contains("patientTable")) {
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

            session.setAttribute("patients", patients);
        } else if (jspContext.contains("bookConsultation")) {
            requestConsultation(request);
        } else if (jspContext.contains("pendingEmployees")) {
            approveEmployee(request);
            setPendingEmployees();
            session.setAttribute("pendingEmployees", pendingEmployees);
        }

        session.setAttribute("filterMessage", message);

        if (database.isEmployee(currentUserID)) {
            viewString = "/protected/employeeDashboard.do";
        } else if (database.isAdmin(currentUserID)) {
            viewString = "/protected/adminDashboard.do";
        } else if (database.isPatient(currentUserID)) {
            viewString = "/protected/patientDashboard.do";
        }

        response.sendRedirect(request.getContextPath() + viewString);

        //view = getServletContext().getRequestDispatcher(viewString);
        //view.forward(request, response);
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
