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

/**
 *
 * @author niklas
 */
public class AddPatient extends HttpServlet {

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
            postcode, county, town, telephoneNumber, lookupPostcode;
    String firstName, surName;
    Date dateOfBirth;

    Database database;
    int currentUserID, streetNumber;
    HttpSession session;
    RequestDispatcher view;
    Address thisAddress;
    Patient thisPatient;
    boolean success, insured;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        message = "";
        success = false;
        session = request.getSession();
        view = getServletContext().getRequestDispatcher("/addPatient.jsp");
        lookupPostcode = request.getParameter("lookupPostcode");
        thisAddress = new Address("", "", "", "", "", "");

        if (Geocoding.validateGeocode(lookupPostcode)) {

            thisAddress = Geocoding.getAddress();

        } else {

            try {
                username = request.getParameter("username");
                password = request.getParameter("password");
                repeatPassword = request.getParameter("repeatPassword");
                firstName = request.getParameter("firstName");
                surName = request.getParameter("surName");
                dateOfBirth = Date.valueOf(request.getParameter("dateOfBirth"));
                insured = Boolean.parseBoolean(request.getParameter("insured"));
                addressLine1 = request.getParameter("addressLine1");
                addressLine2 = request.getParameter("addressLine2");
                county = request.getParameter("county");
                town = request.getParameter("town");
                telephoneNumber = request.getParameter("telephoneNumber");

                currentUserID = 0;
                database = (Database) getServletContext().getAttribute("database");
                currentUserID = database.getUserID(username, "");

                if (currentUserID != -2) {
                    message = "Username already exists, please login instead";
                } else if (!password.equals(repeatPassword)) {
                    message = "The entered passwords does not match";
                } else {
                    message = "User added succesfully, please login";
                    success = true;
                    thisAddress = new Address(addressLine1, addressLine2, postcode, county, town, telephoneNumber);
                    thisPatient = new Patient(username, firstName, surName, dateOfBirth, thisAddress, insured);

                    database.addObjectToDatabase(thisPatient);

                    database.addPasswordToUser(thisPatient, password);
                }
            } catch (Exception ex) {
            }
        }

        session.setAttribute("thisAddress", thisAddress);
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
