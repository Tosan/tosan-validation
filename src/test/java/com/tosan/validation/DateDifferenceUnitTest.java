package com.tosan.validation;

import com.tosan.validation.dto.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Habib Motallebpour
 * @since 30/10/2016
 */
public class DateDifferenceUnitTest {
    private final LocalDate fromDate = LocalDate.of(2000, 5, 15);
    private final LocalDateTime fromDateTime = LocalDateTime.of(2000, 5, 15, 10, 30);
    private final LocalTime fromTime = LocalTime.of(10, 30, 30);
    private final ZonedDateTime fromZone = ZonedDateTime.of(fromDateTime, ZoneId.of("UTC+4"));

    @Test
    public void dateDifferenceTest() {
        DateDifferenceDto dateDifferenceDto = new DateDifferenceDto();
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        dateDifferenceDto.setFromDate(today);
        calendar.add(Calendar.DAY_OF_MONTH, 5);
        Date tomorrow = calendar.getTime();
        dateDifferenceDto.setToDate(tomorrow);
        ValidatorTest.validate(new Object[]{dateDifferenceDto});
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void dateDifferenceMaxExceptionTest() {
        DateDifferenceDto dateDifferenceDto = new DateDifferenceDto();
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        dateDifferenceDto.setFromDate(today);
        calendar.add(Calendar.DAY_OF_MONTH, 20);
        Date tomorrow = calendar.getTime();
        dateDifferenceDto.setToDate(tomorrow);
        ValidatorTest.validate(new Object[]{dateDifferenceDto});
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void dateDifferenceMinExceptionTest() {
        DateDifferenceDto dateDifferenceDto = new DateDifferenceDto();
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        dateDifferenceDto.setFromDate(today);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date tomorrow = calendar.getTime();
        dateDifferenceDto.setToDate(tomorrow);
        ValidatorTest.validate(new Object[]{dateDifferenceDto});
    }

    @Test
    public void subDateDifferenceTest() {
        SubDateDifferenceDto dateDifferenceDto = new SubDateDifferenceDto();
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        dateDifferenceDto.setFromDate(today);
        calendar.add(Calendar.DAY_OF_MONTH, 5);
        Date tomorrow = calendar.getTime();
        dateDifferenceDto.setToDate(tomorrow);
        ValidatorTest.validate(new Object[]{dateDifferenceDto});
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void dateDifferenceListTest() {
        DateDifferenceDto dateDifferenceDto1 = new DateDifferenceDto();
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        dateDifferenceDto1.setFromDate(today);
        calendar.add(Calendar.DAY_OF_MONTH, 15);
        Date tomorrow = calendar.getTime();
        dateDifferenceDto1.setToDate(tomorrow);

        DateDifferenceDto dateDifferenceDto2 = new DateDifferenceDto();
        Calendar calendar1 = Calendar.getInstance();
        Date today1 = calendar1.getTime();
        dateDifferenceDto2.setFromDate(today1);
        calendar1.add(Calendar.DAY_OF_MONTH, 5);
        Date tomorrow1 = calendar1.getTime();
        dateDifferenceDto2.setToDate(tomorrow1);

        DateDifferenceListDto dateDifferenceListDto = new DateDifferenceListDto();
        List<DateDifferenceDto> list = dateDifferenceListDto.getDateList();
        list.add(dateDifferenceDto1);
        list.add(dateDifferenceDto2);
        ValidatorTest.validate(new Object[]{dateDifferenceListDto});
    }

    @Test
    void testValidTemporalHardCodedTest() {
        LocalDate toDate = LocalDate.of(2000, 5, 15 + JavaTimeHardCodeDifferenceDto.MIN_DIFFERENCE + 1);
        LocalDateTime toDateTime = LocalDateTime.of(2000, 5, 15, 10, 30 + JavaTimeHardCodeDifferenceDto.MIN_DIFFERENCE + 1);
        LocalTime toTime = LocalTime.of(10 + JavaTimeHardCodeDifferenceDto.MIN_DIFFERENCE + 1, 30, 30);
        ZonedDateTime toZone = ZonedDateTime.of(toDateTime, ZoneId.of("UTC+4"));
        JavaTimeHardCodeDifferenceDto hardCodeDifferenceDto = new JavaTimeHardCodeDifferenceDto(fromDate, toDate, fromDateTime, toDateTime, fromTime, toTime,
                fromZone, toZone);
        try {
            ValidatorTest.validate(new Object[]{hardCodeDifferenceDto});
        } catch (RuntimeException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    void testInvalidTemporalHardCodedTest() {
        LocalDate toDate = LocalDate.of(2000, 5, 15 + JavaTimeHardCodeDifferenceDto.MIN_DIFFERENCE - 1);
        LocalDateTime toDateTime = LocalDateTime.of(2000, 5, 15, 10, 30 + JavaTimeHardCodeDifferenceDto.MIN_DIFFERENCE - 1);
        LocalTime toTime = LocalTime.of(10 + JavaTimeHardCodeDifferenceDto.MIN_DIFFERENCE - 1, 30, 30);
        ZonedDateTime toZone = ZonedDateTime.of(toDateTime, ZoneId.of("UTC+4"));
        JavaTimeHardCodeDifferenceDto hardCodeDifferenceDto =
                new JavaTimeHardCodeDifferenceDto(fromDate, toDate, fromDateTime, toDateTime, fromTime, toTime, fromZone, toZone);
        try {
            ValidatorTest.validate(new Object[]{hardCodeDifferenceDto});
        } catch (RuntimeException e) {
            String message = e.getMessage();
            Assert.assertTrue(message.contains("fromLocalTime and toLocalTime") && message.contains("fromLocalDateTime and toLocalDateTime") &&
                    message.contains("fromLocalDate and toLocalDate"));
            return;
        }
        Assert.fail();
    }

    @Test
    void testValidTemporalPropertiesTest() {
        LocalDate toDate = LocalDate.of(2000, 5, 15 + JavaTimePropertiesDifferenceDto.MIN_DIFFERENCE + 1);
        LocalDateTime toDateTime = LocalDateTime.of(2000, 5, 15, 10, 30 + JavaTimePropertiesDifferenceDto.MIN_DIFFERENCE + 1);
        LocalTime toTime = LocalTime.of(10 + JavaTimePropertiesDifferenceDto.MIN_DIFFERENCE + 1, 30, 30);
        ZonedDateTime toZone = ZonedDateTime.of(toDateTime, ZoneId.of("UTC+4"));
        JavaTimePropertiesDifferenceDto propertiesDifferenceDto =
                new JavaTimePropertiesDifferenceDto(fromDate, toDate, fromDateTime, toDateTime, fromTime, toTime, fromZone, toZone);
        try {
            ValidatorTest.validate(new Object[]{propertiesDifferenceDto});
        } catch (RuntimeException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    void testInvalidTemporalPropertiesTest() {
        LocalDate toDate = LocalDate.of(2000, 5, 15 + JavaTimePropertiesDifferenceDto.MIN_DIFFERENCE - 1);
        LocalDateTime toDateTime = LocalDateTime.of(2000, 5, 15, 10, 30 + JavaTimePropertiesDifferenceDto.MIN_DIFFERENCE - 1);
        LocalTime toTime = LocalTime.of(10 + JavaTimePropertiesDifferenceDto.MIN_DIFFERENCE - 1, 30, 30);
        ZonedDateTime toZone = ZonedDateTime.of(toDateTime, ZoneId.of("UTC+4"));
        JavaTimePropertiesDifferenceDto propertiesDifferenceDto =
                new JavaTimePropertiesDifferenceDto(fromDate, toDate, fromDateTime, toDateTime, fromTime, toTime, fromZone, toZone);
        try {
            ValidatorTest.validate(new Object[]{propertiesDifferenceDto});
        } catch (RuntimeException e) {
            String message = e.getMessage();
            Assert.assertTrue(message.contains("fromLocalTime and toLocalTime") && message.contains("fromLocalDateTime and toLocalDateTime") &&
                    message.contains("fromLocalDate and toLocalDate"));
            return;
        }
        Assert.fail();
    }

    @Test
    public void shouldSkipValidationWhenPassNullValueForFromAndTo() {
        DateDifferenceDto dateDifferenceDto = new DateDifferenceDto();
        ValidatorTest.validate(new Object[]{dateDifferenceDto});
    }
}
