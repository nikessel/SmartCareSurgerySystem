/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.util.ArrayList;
import javax.servlet.*;
import model.*;

/**
 *
 * @author niklas
 */
public class myServletContextListener implements ServletContextListener {
    
    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext sc = event.getServletContext();
        
        Database database = new Database();
        
        database.connect();
        
        sc.setAttribute("database", database);
    }
    

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        
    }
    
}
