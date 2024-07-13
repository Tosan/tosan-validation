package com.tosan.validation.util.date;

import com.tosan.validation.constraints.DateDifferenceUnit;

import java.util.concurrent.TimeUnit;

/**
 * Compare the difference of two date/time objects based on a unit of type {@code DateDifferenceUnit}
 *
 * @author Mohammadreza Noshadravan
 * @since 07/10/2023
 */
public interface DateTimeComparatorBasedOnUnit<T> {

    /**
     * This function performs a comparison between two arguments and
     * returns the duration between them
     *
     * @param fromDate the first date/time to be compared
     * @param toDate the second date/time to be compared
     * @param unit the unit that comparison results is based on that
     * @return a negative integer, zero, or a positive integer as the
     *         first argument is less than, equal to, or greater than
     *         the second. It also indicates the duration between two
     *         arguments based on the {@code unit}
     */
    long compare(T fromDate, T toDate, DateDifferenceUnit unit);

    /**
     * Converts from milliseconds to another unit. The passed argument
     * named {@code dateDifferenceUnit} is used to convert a duration
     * from milliseconds to another unitConvert
     *
     * @param diffInMilliseconds the difference that is calculated in milliseconds
     * @param dateDifferenceUnit the unit that will be converted from milliseconds
     * @return difference in selected unit {@code dateDifferenceUnit}
     */
    default long convert(Long diffInMilliseconds, DateDifferenceUnit dateDifferenceUnit) {
        return switch (dateDifferenceUnit) {
            case day -> diffInMilliseconds / TimeUnit.DAYS.toMillis(1);
            case hour -> diffInMilliseconds / TimeUnit.HOURS.toMillis(1);
            case minute -> diffInMilliseconds / TimeUnit.MINUTES.toMillis(1);
            case second -> diffInMilliseconds / TimeUnit.SECONDS.toMillis(1);
            case millisecond -> diffInMilliseconds;
        };
    }
}