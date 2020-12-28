/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import model.Database;

/**
 *
 * @author niklas
 */
public class AuthenticationFilter implements Filter {

    private FilterConfig fc;
    private String message;
    private int currentUserID;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.fc = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        response.setContentType("text/html;charset=UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Database database = (Database) request.getServletContext().getAttribute("database");

        try {
            currentUserID = database.getUserID(username, password);

            if (currentUserID >= 0) {
                request.setAttribute("userID", currentUserID);
                chain.doFilter(request, response);
            } else {
                throw new NullPointerException();
            }

        } catch (NullPointerException e) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");
            requestDispatcher.include(request, response);
        }

    }

    @Override
    public void destroy() {

    }

}
