package com;

import java.io.*;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import model.Database;

/**
 *
 * @author Niklas Sarup-Lytzen ID: 18036644
 *
 */

// The authentication filter will run a single time when the user attempts to login
public class AuthenticationFilter implements Filter {

    private FilterConfig fc;
    private String errorMessage, username, password;
    private int currentUserID;
    private boolean isPending;
    private HttpSession session;
    private RequestDispatcher view;
    private Database database;

    private boolean wrongUsername() {
        return currentUserID == -2;
    }

    private boolean wrongPassword() {
        return currentUserID == -1;
    }

    // This method will set User subclass specific session attributes, used 
    // authorisation later on
    private void setBooleans() {

        if (database.isUser(currentUserID)) {
            session.setAttribute("isUser", "1");

            if (database.isEmployee(currentUserID)) {
                session.setAttribute("isEmployee", "1");

                if (database.isAdmin(currentUserID)) {
                    session.setAttribute("isAdmin", "1");
                } else if (database.isDoctor(currentUserID)) {
                    session.setAttribute("isDoctor", "1");
                } else if (database.isNurse(currentUserID)) {
                    session.setAttribute("isNurse", "1");
                }
            } else {
                session.setAttribute("isPatient", "1");
            }
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.fc = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        // Filters handle all request types, not just http. Therefore we need to
        // Parse the request as a httpRequest
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        response.setContentType("text/html;charset=UTF-8");

        session = httpRequest.getSession();
        database = (Database) httpRequest.getServletContext().getAttribute("database");
        view = httpRequest.getRequestDispatcher("/login.jsp");
        errorMessage = "";

        if (httpRequest.getParameter("username") != null) {
            try {
                username = httpRequest.getParameter("username");
                password = httpRequest.getParameter("password");
                currentUserID = database.getUserID(username, password);
                isPending = database.isUserPending(currentUserID);

                if (isPending) {
                    throw new NullPointerException();
                } else {

                    if (currentUserID >= 0) {
                        httpRequest.setAttribute("userID", currentUserID);
                        setBooleans();
                        chain.doFilter(httpRequest, response);
                        return;
                    } else {
                        throw new NullPointerException();
                    }
                }

            } catch (NullPointerException e) {
                if (isPending) {
                    errorMessage = "Admin approval required before this user can login";
                } else if (wrongUsername()) {
                    errorMessage = "Invalid username, please try again";
                } else if (wrongPassword()) {
                    errorMessage = "Invalid password, please try again";
                }

                httpRequest.getServletContext().setAttribute("message", errorMessage);
                session.setAttribute("message", errorMessage);

            }
        }
        view.forward(httpRequest, response);
    }

    @Override
    public void destroy() {

    }

}
