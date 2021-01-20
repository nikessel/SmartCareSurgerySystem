package com;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Database;

/**
 *
 * @author Niklas Sarup-Lytzen ID: 18036644
 *
 */

// Requests and attempts to set a new password for a given user
public class PasswordChanger extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private String username, password, updatedPassword, repeatPassword, message;
    private Database database;
    private HttpSession session;
    private int currentUserID;
    private RequestDispatcher view;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        view = request.getRequestDispatcher("/passwordChanger.jsp");
        

        try {
            username = request.getParameter("username");
            password = request.getParameter("password");
            updatedPassword = request.getParameter("updatePassword");
            repeatPassword = request.getParameter("repeatPassword");
            currentUserID = -1;
            message = "";
            database = (Database) getServletContext().getAttribute("database");
            session = request.getSession();

            if (!updatedPassword.equals(repeatPassword)) {
                message = "The entered password update does not match";
            } else {
                try {

                    currentUserID = database.getUserID(username, password);

                    if (database.isUser(currentUserID)) {

                        database.setPassword(currentUserID, updatedPassword);
                        message = "Password changed";

                    } else if (currentUserID == -2) {
                        message = "Username not found";
                    } else if (currentUserID == -1) {
                        message = "Invalid password";
                    } else {
                        throw new Exception();
                    }

                } catch (Exception e) {
                    message = "Error setting password";
                }

            }
        } catch (Exception ex) {
            
        }

        session.setAttribute("message", message);

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
