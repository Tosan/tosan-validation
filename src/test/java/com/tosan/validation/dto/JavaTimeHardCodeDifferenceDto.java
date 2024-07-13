package com.tosan.validation.dto;

import com.tosan.validation.constraints.DateDifference;
import com.tosan.validation.constraints.DateDifferenceUnit;
import com.tosan.validation.constraints.DateDifferences;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

/**
 * @author Mohammadreza Noshadravan
 * @since 07/10/2023
 */
@DateDifferences(value = {@DateDifference(fromFieldName = "fromLocalDate", toFieldName = "toLocalDate", maxDifference = 10, minDifference = 5, unit =
        DateDifferenceUnit.day),
        @DateDifference(fromFieldName = "fromLocalDateTime", toFieldName = "toLocalDateTime", maxDifference = 10, minDifference = 5,
                unit = DateDifferenceUnit.minute),
        @DateDifference(fromFieldName = "fromLocalTime", toFieldName = "toLocalTime", maxDifference = 10, minDifference = 5,
                unit = DateDifferenceUnit.hour),
        @DateDifference(fromFieldName = "fromZonedLocalTime", toFieldName = "toZonedLocalTime", maxDifference = 10, minDifference = 5,
                unit = DateDifferenceUnit.minute)})
public record JavaTimeHardCodeDifferenceDto(
        LocalDate fromLocalDate,
        LocalDate toLocalDate,
        LocalDateTime fromLocalDateTime,
        LocalDateTime toLocalDateTime,
        LocalTime fromLocalTime,
        LocalTime toLocalTime,
        ZonedDateTime fromZonedLocalTime,
        ZonedDateTime toZonedLocalTime
) {
    public static final int MIN_DIFFERENCE = 5;
    @SuppressWarnings("unused")
    public static final int MAX_DIFFERENCE = 10;
}
