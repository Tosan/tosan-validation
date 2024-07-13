package com.tosan.validation;

import com.tosan.validation.dto.FutureDateDto;
import org.testng.annotations.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Habib Motallebpour
 * @since 30/10/2016
 */
public class FutureDateUnitTest {

    @Test
    public void validFutureZonedDateTest() {
        FutureDateDto futureDateDto = new FutureDateDto();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        ZonedDateTime tomorrow = calendar.getTime().toInstant().atZone(ZoneId.of("Asia/Tehran"));
        futureDateDto.setZonedDate(tomorrow);
        ValidatorTest.validate(new Object[]{futureDateDto});
    }

    @Test
    public void validFutureZonedDateTimeTest() {
        FutureDateDto futureDateDto = new FutureDateDto();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.SECOND, 13);
        ZonedDateTime tomorrow = calendar.getTime().toInstant().atZone(ZoneId.of("Asia/Tehran"));
        futureDateDto.setZonedDate(tomorrow);
        ValidatorTest.validate(new Object[]{futureDateDto});
    }

    @Test
    public void validFutureDateTest() {
        FutureDateDto futureDateDto = new FutureDateDto();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date tomorrow = calendar.getTime();
        futureDateDto.setDate(tomorrow);
        ValidatorTest.validate(new Object[]{futureDateDto});
    }

    @Test
    public void validFutureDateTimeTest() {
        FutureDateDto futureDateDto = new FutureDateDto();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.SECOND, 13);
        Date tomorrow = calendar.getTime();
        futureDateDto.setDate(tomorrow);
        ValidatorTest.validate(new Object[]{futureDateDto});
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void todayDateTest() {
        FutureDateDto futureDateDto = new FutureDateDto();
        Calendar calendar = Calendar.getInstance();
        Date tomorrow = calendar.getTime();
        futureDateDto.setDate(tomorrow);
        ValidatorTest.validate(new Object[]{futureDateDto});
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void todayZonedDateTest() {
        FutureDateDto futureDateDto = new FutureDateDto();
        Calendar calendar = Calendar.getInstance();
        ZonedDateTime tomorrow = calendar.getTime().toInstant().atZone(ZoneId.of("Asia/Tehran"));
        futureDateDto.setZonedDate(tomorrow);
        ValidatorTest.validate(new Object[]{futureDateDto});
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void pastDateTest() {
        FutureDateDto futureDateDto = new FutureDateDto();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date tomorrow = calendar.getTime();
        futureDateDto.setDate(tomorrow);
        ValidatorTest.validate(new Object[]{futureDateDto});
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void pastDateTimeTest() {
        FutureDateDto futureDateDto = new FutureDateDto();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, -13);
        Date tomorrow = calendar.getTime();
        futureDateDto.setDate(tomorrow);
        ValidatorTest.validate(new Object[]{futureDateDto});
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void pastZonedDateTest() {
        FutureDateDto futureDateDto = new FutureDateDto();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        ZonedDateTime tomorrow = calendar.getTime().toInstant().atZone(ZoneId.of("Asia/Tehran"));
        futureDateDto.setZonedDate(tomorrow);
        ValidatorTest.validate(new Object[]{futureDateDto});
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void pastZonedDateTimeTest() {
        FutureDateDto futureDateDto = new FutureDateDto();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, -13);
        ZonedDateTime tomorrow = calendar.getTime().toInstant().atZone(ZoneId.of("Asia/Tehran"));
        futureDateDto.setZonedDate(tomorrow);
        ValidatorTest.validate(new Object[]{futureDateDto});
    }
}
