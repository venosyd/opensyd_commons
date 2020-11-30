package com.venosyd.open.commons.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author sergio lisan <sels@venosyd.com>
 */
public class DateUtil {

    /**
     * DATE PATTERN
     **/
    public static final String DATE_PATTERN = "yyyy-MM-dd hh:mm:ss.SSSSSS";

    /** */
    public static final String READABLE_DATE_PATTERN = "dd/MM/yyyy";

    /**
     * gets the year-month-day of the patterned date string
     */
    public static Date extractDay(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 7));
        int day = Integer.parseInt(date.substring(8, 10));

        var c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.YEAR, year);

        return c.getTime();
    }

    /**
     * converte dia, mes e ano para o DATE PATTERN
     */
    public static String fromDayMonthYear(int day, int month, int year) {
        var c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.YEAR, year);

        var date = c.getTime();

        try {
            return new SimpleDateFormat(DATE_PATTERN).format(date);
        } catch (Exception e) {
            return null;
        }
    }

    /** */
    public static String fromDate(Date date) {
        try {
            return new SimpleDateFormat(DATE_PATTERN).format(date);
        } catch (Exception e) {
            return null;
        }
    }

    /** */
    public static Date fromString(String date) {
        try {
            return new SimpleDateFormat(DATE_PATTERN).parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    /** */
    public static Date fromReadableString(String date) {
        try {
            return new SimpleDateFormat(READABLE_DATE_PATTERN).parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    /** */
    public static String toReadableText(String source) {
        try {
            Date date = new SimpleDateFormat(DATE_PATTERN).parse(source);
            return new SimpleDateFormat(READABLE_DATE_PATTERN).format(date);
        } catch (Exception e) {
            return null;
        }

    }
}
