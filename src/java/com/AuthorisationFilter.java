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

/**
 *
 * @author Niklas Sarup-Lytzen ID: 18036644
 *
 */
public class AuthorisationFilter implements Filter {

    private FilterConfig fc;
    private HttpSession session;
    private boolean isUser, isAdmin, isDoctor, isNurse, isPatient;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.fc = filterConfig;
    }

    // Set local booleans based on session attributes from the authentication filter
    private void setBooleans() {
        isUser = isAdmin = isDoctor = isNurse = isPatient = false;

        if (session.getAttribute("isUser") != null) {
            isUser = true;

            if (session.getAttribute("isAdmin") != null) {
                isAdmin = true;

            } else if (session.getAttribute("isDoctor") != null) {
                isDoctor = true;

            } else if (session.getAttribute("isNurse") != null) {
                isNurse = true;

            } else {
                isPatient = true;
            }
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        session = httpRequest.getSession(false);
        String requestURI = httpRequest.getRequestURI();
        setBooleans();

        response.setContentType("text/html;charset=UTF-8");

        String[] publicUrlPatterns = {"/addUser.do",
            "/login.do", "/passwordChanger.do", "/errorPage.jsp", "/addUser.do",
            "/logout.do"};

        // If the requestURI is public, the filter is done
        for (int i = 0; i < publicUrlPatterns.length; i++) {
            if (requestURI.endsWith(publicUrlPatterns[i])) {
                chain.doFilter(httpRequest, response);
                return;
            }
        }

        String[] protectedUrlPatterns = {"/protected/refresh.do", "/protected/adminDashboard.do",
            "/protected/employeeDashboard.do", "/protected/patientDashboard.do"};

        try {

            // Any valid user can make a request to the refresh servlet
            if (isUser && requestURI.endsWith(protectedUrlPatterns[0])) {

            // Otherwise users are only allowed to access their own respective dashboards
            } else if (isAdmin && requestURI.endsWith(protectedUrlPatterns[1])) {

            } else if ((isNurse || isDoctor) && requestURI.endsWith(protectedUrlPatterns[2])) {

            } else if (isPatient && requestURI.endsWith(protectedUrlPatterns[3])) {

            // If none of these cases are true forward the user to the error page
            } else {
                throw new Exception();
            }

            chain.doFilter(httpRequest, response);

        } catch (Exception e) {

            session.setAttribute("message", "You are not allowed to access this resource: " + requestURI + isPatient + e);

            RequestDispatcher requestDispatcher = httpRequest.getRequestDispatcher("/errorPage.jsp");
            requestDispatcher.forward(httpRequest, response);
        }

    }

    @Override
    public void destroy() {

    }

}
