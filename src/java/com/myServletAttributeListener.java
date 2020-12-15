/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import model.*;

/**
 *
 * @author Niklas Sarup-Lytzen ID: 18036644
 * *
 */
public class myServletAttributeListener implements ServletContextAttributeListener {

    @Override
    public void attributeRemoved(ServletContextAttributeEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent event) {

    }

    @Override
    public void attributeAdded(ServletContextAttributeEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
