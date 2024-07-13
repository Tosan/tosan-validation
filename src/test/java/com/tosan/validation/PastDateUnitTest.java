package com.tosan.validation;

import com.tosan.validation.dto.PastDateDto;
import org.testng.annotations.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Habib Motallebpour
 * @since 30/10/2016
 */
public class PastDateUnitTest {

    @Test
    public void validPastDateTest() {
        PastDateDto pastDateDto = new PastDateDto();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -2);
        Date date = calendar.getTime();
        pastDateDto.setDate(date);
        ValidatorTest.validate(new Object[]{pastDateDto});
    }

    @Test
    public void validPastZonedDateTimeTest() {
        PastDateDto pastDateDto = new PastDateDto();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -2);
        ZonedDateTime date = calendar.getTime().toInstant().atZone(ZoneId.of("Asia/Tehran"));
        pastDateDto.setZonedDate(date);
        ValidatorTest.validate(new Object[]{pastDateDto});
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void futureDateTest() {
        PastDateDto pastDateDto = new PastDateDto();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, +2);
        Date tomorrow = calendar.getTime();
        pastDateDto.setDate(tomorrow);
        ValidatorTest.validate(new Object[]{pastDateDto});
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void futureZoneDateTimeTest() {
        PastDateDto pastDateDto = new PastDateDto();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, +2);
        ZonedDateTime tomorrow = calendar.getTime().toInstant().atZone(ZoneId.of("Asia/Tehran"));
        pastDateDto.setZonedDate(tomorrow);
        ValidatorTest.validate(new Object[]{pastDateDto});
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void todayDateTest() {
        PastDateDto pastDateDto = new PastDateDto();
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        pastDateDto.setDate(date);
        ValidatorTest.validate(new Object[]{pastDateDto});
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void todayZonedDateTimeTest() {
        PastDateDto pastDateDto = new PastDateDto();
        Calendar calendar = Calendar.getInstance();
        ZonedDateTime date = calendar.getTime().toInstant().atZone(ZoneId.of("Asia/Tehran"));
        pastDateDto.setZonedDate(date);
        ValidatorTest.validate(new Object[]{pastDateDto});
    }
}
