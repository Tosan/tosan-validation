package com.tosan.validation;

import com.tosan.validation.dto.PastDateDto;
import com.tosan.validation.dto.PastDateTimeDto;
import org.testng.annotations.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Marjan Mehranfar
 * @since 13/07/2019
 */
public class PastDateTimeUnitTest {

    @Test
    public void validPastDateTimeTest() {
        PastDateTimeDto pastDateTimeDto = new PastDateTimeDto();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, -13);
        Date date = calendar.getTime();
        pastDateTimeDto.setDate(date);
        ValidatorTest.validate(new Object[]{pastDateTimeDto});
    }

    @Test
    public void validPastZonedDateTimeTest() {
        PastDateTimeDto pastDateTimeDto = new PastDateTimeDto();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, -13);
        ZonedDateTime date = calendar.getTime().toInstant().atZone(ZoneId.of("Asia/Tehran"));
        pastDateTimeDto.setZonedDate(date);
        ValidatorTest.validate(new Object[]{pastDateTimeDto});
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void futureDateTimeTest() {
        PastDateDto pastDateDto = new PastDateDto();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 13);
        Date tomorrow = calendar.getTime();
        pastDateDto.setDate(tomorrow);
        ValidatorTest.validate(new Object[]{pastDateDto});
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void futureZoneDateTimeTest() {
        PastDateDto pastDateDto = new PastDateDto();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 13);
        ZonedDateTime tomorrow = calendar.getTime().toInstant().atZone(ZoneId.of("Asia/Tehran"));
        pastDateDto.setZonedDate(tomorrow);
        ValidatorTest.validate(new Object[]{pastDateDto});
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void todayDateTimeTest() {
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
