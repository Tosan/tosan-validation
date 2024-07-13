package com.tosan.validation.util.date;

import com.tosan.validation.constraints.DateDifferenceUnit;

import java.util.Date;

/**
 * a {@code DateTimeComparatorBasedOnUnit} that compares two {@code Date} arguments
 *
 * @author Mohammadreza Noshadravan
 * @since 07/10/2023
 */
class DateComparator implements DateTimeComparatorBasedOnUnit<Date> {

    @Override
    public long compare(Date date1, Date date2, DateDifferenceUnit unit) {
        return convert(date1.getTime() - date2.getTime(), unit);
    }
}