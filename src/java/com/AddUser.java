/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Address;
import model.Database;
import model.Geocoding;
import model.Patient;
import java.sql.Date;
import model.Doctor;
import model.Nurse;

/**
 *
 * @author niklas
 */
public class AddUser extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    String username, password, repeatPassword, message, addressLine1, addressLine2,
            postcode, county, town, telephoneNumber, lookupPostcode, headline;
    String firstName, surName;
    Date dateOfBirth;
    boolean isFullTime;

    Database database;
    int currentUserID, streetNumber, userType;
    HttpSession session;
    HttpServletRequest request;

    RequestDispatcher view;
    Address thisAddress;
    Patient thisPatient;

    boolean success, insured;

    private boolean attemptAddUser() {
        currentUserID = 0;
        database = (Database) getServletContext().getAttribute("database");
        currentUserID = database.getUserID(username, "");

        if (currentUserID != -2) {
            message = "Username already exists, please login instead";
        } else if (!password.equals(repeatPassword)) {
            message = "The entered passwords does not match";
        } else {
            success = true;

            if (userType == 1) {
                thisAddress = new Address(addressLine1, addressLine2, postcode, county, town, telephoneNumber);
                thisPatient = new Patient(username, firstName, surName, dateOfBirth, thisAddress, insured);
                database.addObjectToDatabase(thisPatient);
                database.addPasswordToUser(thisPatient, password);
                message = "User added succesfully, please login";
            } else if (userType == 2) {
                Doctor thisDoctor = new Doctor(username, firstName, surName, isFullTime);
                database.addObjectToDatabase(thisDoctor);
                database.addPasswordToUser(thisDoctor, password);
                message = "Request to add a doctor has been forwarded to admin for approval";
            } else if (userType == 3) {
                Nurse thisNurse = new Nurse(username, firstName, surName, isFullTime);
                database.addObjectToDatabase(thisNurse);
                database.addPasswordToUser(thisNurse, password);
                message = "Request to add a nurse has been forwarded to admin for approval";
            }
            return true;
        }

        return false;
    }

    private void getUserAttributes() {
        username = request.getParameter("username");
        password = request.getParameter("password");
        repeatPassword = request.getParameter("repeatPassword");
        firstName = request.getParameter("firstName");
        surName = request.getParameter("surName");
    }

    private void getPatientAttributes() {
        dateOfBirth = Date.valueOf(request.getParameter("dateOfBirth"));
        insured = Boolean.parseBoolean(request.getParameter("insured"));
        addressLine1 = request.getParameter("addressLine1");
        addressLine2 = request.getParameter("addressLine2");
        county = request.getParameter("county");
        town = request.getParameter("town");
        telephoneNumber = request.getParameter("telephoneNumber");

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        message = "";
        success = false;
        session = request.getSession();
        this.request = request;

        view = getServletContext().getRequestDispatcher("/addUser.jsp");
        lookupPostcode = request.getParameter("lookupPostcode");
        thisAddress = new Address("", "", "", "", "", "");

        try {
            userType = Integer.parseInt(request.getParameter("userType"));
            message = String.valueOf(userType);
        } catch (NumberFormatException ex) {
            headline = "";
        }

        if (userType == 1) {
            headline = "Patient";

            if (Geocoding.validateGeocode(lookupPostcode)) {

                thisAddress = Geocoding.getAddress();

            } else {

                try {
                    getUserAttributes();
                    getPatientAttributes();

                    attemptAddUser();

                } catch (Exception ex) {
                }
            }

            session.setAttribute("thisAddress", thisAddress);

        } else if (userType == 2 || userType == 3) {

            try {
                getUserAttributes();

                isFullTime = Boolean.parseBoolean(request.getParameter("isFullTime"));

                attemptAddUser();
            } catch (Exception ex) {

            }
        }

        session.setAttribute("headline", headline);
        session.setAttribute("message", message);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        } else {
            view.forward(request, response);
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
