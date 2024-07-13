package com.tosan.validation.util.date;

import com.tosan.tools.jalali.JalaliDate;
import com.tosan.validation.constraints.DateDifferenceUnit;

import java.time.temporal.Temporal;
import java.util.Date;

/**
 * Provides appropriate {@code DateTimeComparatorBasedOnUnit} based on
 * the type of arguments to be compared.
 *
 * @author Mohammadreza Noshadravan
 * @since 07/10/2023
 */
public class DateTimeComparatorContext {
    private final Class strategy;
    private final static DateComparator dateComparator = new DateComparator();
    private final static JalaliDateComparator jalaliDateComparator = new JalaliDateComparator();
    private final static TemporalComparator temporalComparator = new TemporalComparator();

    public DateTimeComparatorContext(Class strategy) {
        this.strategy = strategy;
    }

    /**
     * @return appropriate {@code DateTimeComparatorBasedOnUnit} based on the {@code clazz}
     */
    private DateTimeComparatorBasedOnUnit getComparator() {
        if (Date.class.isAssignableFrom(strategy)) {
            return dateComparator;
        } else if (JalaliDate.class.isAssignableFrom(strategy)) {
            return jalaliDateComparator;
        } else if (Temporal.class.isAssignableFrom(strategy)) {
            return temporalComparator;
        } else {
            throw new IllegalArgumentException(String.format("%s does not support type %s",
                    DateTimeComparatorContext.class.getName(), strategy.getName()));
        }
    }

    @SuppressWarnings("unchecked")
    public long doCompare(Object fromDateTime, Object toDateTime, DateDifferenceUnit unit) {
        return getComparator().compare(fromDateTime, toDateTime, unit);
    }
}
