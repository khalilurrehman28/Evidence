package com.dupleit.kotlin.primaryschoolassessment.otherHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by khalil on 11/7/17.
 */

public class DateFormatConverter {

    public String convertDate(String myDate){
        String dateTime = myDate;
        SimpleDateFormat dateParser = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date = null;
        try {
            date = dateParser.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Then convert the Date to a String, formatted as you dd/MM/yyyy
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMddyyyy");
        System.out.println(dateFormatter.format(date));
        return dateFormatter.format(date);
    }

}
