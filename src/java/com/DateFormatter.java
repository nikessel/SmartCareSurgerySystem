/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

/**
 *
 * @author niklas
 */
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateFormatter {

    Date date;

    private DateFormatter() {
    }

    public static String formatDate(Date date, String pattern) {
        pattern = "dd-MM-yyyy";
        return new SimpleDateFormat(pattern).format(date);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
