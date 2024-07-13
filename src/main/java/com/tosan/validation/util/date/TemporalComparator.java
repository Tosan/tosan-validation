package com.tosan.validation.util.date;


import com.tosan.validation.constraints.DateDifferenceUnit;
import jakarta.validation.UnexpectedTypeException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * a {@code DateTimeComparatorBasedOnUnit} that
 * compares two {@code Temporal} arguments.
 * supported {@code Temporal}s are {@code LocalDate},
 * {@code LocalDateTime} and {@code LocalTime} and {@code ZonedDateTime}.
 *
 * @author Mohammadreza Noshadravan
 * @since 07/10/2023
 */
class TemporalComparator implements DateTimeComparatorBasedOnUnit<Temporal> {
    @Override
    public long compare(Temporal fromDate, Temporal toDate, DateDifferenceUnit unit) {
        if (!fromDate.getClass().equals(toDate.getClass())) {
            throw new IllegalArgumentException("both arguments fromDate and toDate must be of the same types.");
        }
        var supportedType = Set.of(LocalDate.class, LocalDateTime.class, LocalTime.class, ZonedDateTime.class);
        boolean supportFromDate = supportedType.stream().anyMatch(a -> a.isAssignableFrom(fromDate.getClass()));
        if (!supportFromDate) {
            throw new IllegalArgumentException("Unsupported type to compare.");
        }
        if (fromDate.isSupported(ChronoUnit.MILLIS)) {
            return convert(ChronoUnit.MILLIS.between(toDate, fromDate), unit);
        } else if (fromDate.isSupported(ChronoUnit.DAYS)) {
            return convert(ChronoUnit.DAYS.between(toDate, fromDate) * TimeUnit.DAYS.toMillis(1), unit);
        } else {
            throw new UnexpectedTypeException("Unexpected exception: LocalTime and LocalDatetime support MILLIS and LocalDate supports DAYS");
        }
    }
}