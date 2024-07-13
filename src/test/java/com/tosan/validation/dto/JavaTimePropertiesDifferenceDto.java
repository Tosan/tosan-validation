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
@DateDifferences(value = {@DateDifference(fromFieldName = "fromLocalDate", toFieldName = "toLocalDate", maxDifferenceKey = "MAX_DIFFERENCE",
        minDifferenceKey = "MIN_DIFFERENCE", unit = DateDifferenceUnit.day),
        @DateDifference(fromFieldName = "fromLocalDateTime", toFieldName = "toLocalDateTime", maxDifferenceKey = "MAX_DIFFERENCE",
                minDifferenceKey = "MIN_DIFFERENCE", unit = DateDifferenceUnit.minute),
        @DateDifference(fromFieldName = "fromLocalTime", toFieldName = "toLocalTime", maxDifferenceKey = "MAX_DIFFERENCE",
                minDifferenceKey = "MIN_DIFFERENCE", unit = DateDifferenceUnit.hour),
        @DateDifference(fromFieldName = "fromZonedLocalTime", toFieldName = "toZonedLocalTime", maxDifferenceKey = "MAX_DIFFERENCE",
                minDifferenceKey = "MIN_DIFFERENCE", unit = DateDifferenceUnit.minute)})
public record JavaTimePropertiesDifferenceDto(
        LocalDate fromLocalDate,
        LocalDate toLocalDate,
        LocalDateTime fromLocalDateTime,
        LocalDateTime toLocalDateTime,
        LocalTime fromLocalTime,
        LocalTime toLocalTime,
        ZonedDateTime fromZonedLocalTime,
        ZonedDateTime toZonedLocalTime
) {
    public static final int MIN_DIFFERENCE = 3; //must be same as value in validation-key-value.properties file
    @SuppressWarnings("unused")
    public static final int MAX_DIFFERENCE = 10; //must be same as value in validation-key-value.properties file
}
