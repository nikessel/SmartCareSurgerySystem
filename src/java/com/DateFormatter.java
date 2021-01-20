package com;

/**
 *
 * @author Niklas Sarup-Lytzen ID: 18036644
 *
 */
import java.text.SimpleDateFormat;
import java.util.Date;

// Used for some date handling directly in the .jsps, using a custom tag mapping
// In /WEB-INF/custom.tld
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
