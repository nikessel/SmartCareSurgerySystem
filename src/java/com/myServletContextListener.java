package com;

import javax.servlet.*;
import model.*;

/**
 *
 * @author Niklas Sarup-Lytzen ID: 18036644
 *
 */
public class myServletContextListener implements ServletContextListener {
    
    /*  Whenever the webapp is started, this method will run
        The method will connect the database and set the database as a global
        attribute accessible by all servlets */
    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext sc = event.getServletContext();
        
        Database database = new Database();
        
        database.connect();
        
        sc.setAttribute("database", database);
        
        sc.setAttribute("message", "");
        
    }
    

    // When the webapp is closed so too will the database connection
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        Database database = (Database) event.getServletContext().getAttribute("database");
        database.closeConnection();
    }  
}
