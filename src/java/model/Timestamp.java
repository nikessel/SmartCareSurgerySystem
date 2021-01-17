/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 *
 * @author niklas
 */
public class Timestamp extends java.sql.Timestamp {

    private final Calendar calendar = Calendar.getInstance(Locale.UK);
    int weekNumber;

    public Timestamp(int year, int month, int date, int hour, int minute) {
        super(year, month, date, hour, minute, 0, 0);
    }

    public Timestamp(int date) {
        super(date);
    }

    public static Timestamp convertToMyTimestamp(java.sql.Timestamp thisSQLTimestamp) {
        int year = thisSQLTimestamp.getYear();
        int month = thisSQLTimestamp.getMonth();
        int date = thisSQLTimestamp.getDate();
        int hour = thisSQLTimestamp.getHours();
        int minute = thisSQLTimestamp.getMinutes();

        return new Timestamp(year, month, date, hour, minute);
    }

    public static java.sql.Timestamp convertToSQLTimestamp(Timestamp thisTimestamp) {
        int year = thisTimestamp.getYear();
        int month = thisTimestamp.getMonth();
        int date = thisTimestamp.getDate();
        int hour = thisTimestamp.getHours();
        int minute = thisTimestamp.getMinutes();

        return new java.sql.Timestamp(year, month, date, hour, minute, 0, 0);
    }

    public int getWeekNumber() {
        calendar.set(this.getYear(), this.getMonth(), this.getDate());
        int weekNumber = calendar.get(Calendar.WEEK_OF_YEAR);

        return weekNumber;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String string = dateFormat.format(this);

        return string;
    }

}
