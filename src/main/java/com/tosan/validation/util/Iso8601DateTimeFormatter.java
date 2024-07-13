package com.tosan.validation.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Zahra Hajihosseinkhani
 * @since 07/01/2016
 */
public class Iso8601DateTimeFormatter {
    private static final String ISO8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    public static String format(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(ISO8601_DATE_FORMAT);
        return formatter.format(date);
    }
}

