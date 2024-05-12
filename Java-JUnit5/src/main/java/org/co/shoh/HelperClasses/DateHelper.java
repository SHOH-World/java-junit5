package org.co.shoh.HelperClasses;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateHelper {

    // Gives you the current date in whatever format you like
    // e.g. Wednesday 14th December 2016 12.07pm
    // ddHHmmss = 14120734
    // dd/MM/YYYY = 14/12/2016
    // YYYYMMdd = 20161214
    // d MMM YYYY = 14 Dec 2016
    // EEEE d MMM YYYY = Wednesday 14 Dec 2016
    // HH:ss EEE d MMM YYYY = 12:07 Tue 12 Dec 2016
    public static String getDateInFormat(String format) {
        return calculateDate(format);
    }

    //Gives you the current date plus or minus whatever number of days specify, using the format you define (see above for
    //examples of format). Accepts negatives to calculate days in the past.
    // eg Wednesday 14th December 2016 12.07pm
    // format: dd/MM/YY, amount: 0 = 14/12/16
    // format: dd/MM/YY, amount: 1 = 15/12/16
    // format: dd/MM/YY, amount: -1 = 13/12/16
    public static String getAlteredDate(String format, int amount) {
        return calculateDate(format, amount);
    }


    //Pass in a date format as a string, will return today's date in your specified format
    public static String calculateDate(String df) {
        DateFormat dateFormat = new SimpleDateFormat(df);
        Date date = new Date();
        return dateFormat.format(date);
    }

    //Pass in a date format and  days you want to add or subtract from today's date
    // E.g. calculateDate(dateformat, -1) will return yesterday's date
    // E.g. calculateDate(dateformat, 2) will return the day after tomorrow
    public static String calculateDate(String df, int diff) {
        DateFormat dateFormat = new SimpleDateFormat(df);
        Date date = new Date();
        date = addDays(date, diff);
        return dateFormat.format(date);
    }


    private static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //negative number would decrement the days
        return cal.getTime();
    }

    // Reads in a date and writes it out in a new format. You must supply the original format as well as the new.
    public static String reformatDate(String dateToParse, String inputDF, String outputDF) {
        DateFormat inputDateFormat = new SimpleDateFormat(inputDF);
        Date date = null;
        try {
            date = inputDateFormat.parse(dateToParse);
        } catch (Exception e) {
            System.out.println("Could not parse date");
            return null;
        }
        DateFormat dateFormat = new SimpleDateFormat(outputDF);
        return dateFormat.format(date);
    }

    // Parses a string and returns a Date object for manipulation
    public static Date parseDate(String dateToParse, String dateFormat) {
        DateFormat inputDateFormat = new SimpleDateFormat(dateFormat);
        Date date = null;
        try {
            date = inputDateFormat.parse(dateToParse);
        } catch (Exception e) {
            System.out.println("Could not parse date");
            return null;
        }
        return date;
    }

    public static Date getCurrentDate() {
        return new Date();
    }

    public static long getCurrentEpoch() {
        return System.currentTimeMillis() / 1000;
    }

    public static String convertEpochToDateTime(long time) {
        Date date = new Date(time * 1000);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return format.format(date);
    }

    public static String calculateDateTimeWithTimezone(String df, TimeZone timezone) {
        DateFormat dateFormat = new SimpleDateFormat(df);
        dateFormat.setTimeZone(timezone);
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        cal.setTimeZone(timezone);
        date = cal.getTime();
        return dateFormat.format(date);
    }

}
