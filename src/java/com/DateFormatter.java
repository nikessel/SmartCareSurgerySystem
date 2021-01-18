/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

/**
 *
 * @author Niklas Sarup-Lytzen ID: 18036644 *
 */
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateFormatter {

    private DateFormatter() {
    }

    public static String formatDate(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static boolean isInThePast(Date date) {
        Date currentDate = new Date();  
        
        return date.before(currentDate);
    }


}
