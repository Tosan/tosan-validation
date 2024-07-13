package com.tosan.validation.util.date;

import com.tosan.tools.jalali.JalaliDate;
import com.tosan.validation.constraints.DateDifferenceUnit;
import com.tosan.validation.util.CalendarUtils;

import java.util.Objects;

/**
 * a {@code DateTimeComparatorBasedOnUnit} that compares two {@code JalaliDate} arguments
 *
 * @author Mohammadreza Noshadravan
 * @since 07/10/2023
 */
class JalaliDateComparator implements DateTimeComparatorBasedOnUnit<JalaliDate> {

    @Override
    public long compare(JalaliDate fromDate, JalaliDate toDate, DateDifferenceUnit unit) {
        long diffInMillis = 0;
        try {
            diffInMillis = Objects.requireNonNull(CalendarUtils.shamsiToMiladiDate(fromDate)).getTime() - Objects.requireNonNull(
                    CalendarUtils.shamsiToMiladiDate(toDate)).getTime();
        } catch (NullPointerException ignored) {
            //
        }
        return convert(diffInMillis, unit);
    }
}
