package com.tosan.validation.util;

import com.tosan.tools.jalali.JalaliCalendar;
import com.tosan.tools.jalali.JalaliDate;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author Boshra Taheri
 * @since 11/25/13
 */
public class CalendarUtils {

    public static Date shamsiToMiladiDate(JalaliDate jDate) {
        JalaliDate jalaliDate = new JalaliDate(jDate.getYear(), jDate.getMonth(), jDate.getDay(), jDate.getHour(), jDate
                .getMinute(), jDate.getSecond());
        JalaliCalendar jalaliCalendar = new JalaliCalendar(jalaliDate);
        if (jalaliDate.isValid()) {
            return jalaliCalendar.getTime();
        } else {
            return null;
        }
    }

    public static Date toDate(Object value) {
        if (value instanceof Date) {
            return (Date) value;
        } else if (value instanceof ZonedDateTime) {
            return Date.from(((ZonedDateTime) value).toInstant());
        } else {
            throw new IllegalArgumentException("Unsupported type .");
        }
    }
}
