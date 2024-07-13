package com.tosan.validation;

import com.tosan.tools.jalali.JalaliCalendar;
import com.tosan.tools.jalali.JalaliDate;
import com.tosan.validation.dto.JalaliDateDto;
import org.testng.annotations.Test;

/**
 * @author Mostafa Abdollahi
 * @since 3/17/13
 */
public class JalaliDateTest {

    @Test
    public void jalaliTest() {
        JalaliCalendar jalaliCalendar = new JalaliCalendar();
        JalaliDate jalaliDate = getJalaliDateFromCalendar(jalaliCalendar);
        JalaliDateDto dto = new JalaliDateDto();

        jalaliCalendar.add(JalaliCalendar.HOUR, -1);
        dto.setNow(getJalaliDateFromCalendar(jalaliCalendar));
        dto.setPast(jalaliDate);

        jalaliCalendar.add(JalaliCalendar.DAY_OF_MONTH, 1);
        JalaliDate futureDate = getJalaliDateFromCalendar(jalaliCalendar);
        dto.setFuture(futureDate);

        ValidatorTest.validate(new Object[]{dto});
    }

    private static JalaliDate getJalaliDateFromCalendar(JalaliCalendar jalaliCalendar) {

        return new JalaliDate(jalaliCalendar.get(JalaliCalendar.YEAR),
                jalaliCalendar.get(JalaliCalendar.MONTH) + 1, jalaliCalendar.get(JalaliCalendar.DAY_OF_MONTH),
                jalaliCalendar.get(JalaliCalendar.HOUR_OF_DAY), jalaliCalendar.get(JalaliCalendar.MINUTE),
                jalaliCalendar.get(JalaliCalendar.SECOND));
    }
}
