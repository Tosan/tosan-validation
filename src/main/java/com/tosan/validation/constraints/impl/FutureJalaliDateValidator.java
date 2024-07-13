package com.tosan.validation.constraints.impl;

import com.tosan.tools.jalali.JalaliCalendar;
import com.tosan.tools.jalali.JalaliDate;
import com.tosan.validation.constraints.DurationValidator;
import com.tosan.validation.constraints.FutureJalaliDate;
import com.tosan.validation.core.BaseValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author Hooman Norooziniam, Babak Samadi
 * @version 3.0.1
 * @since Dec 30, 2009
 */
public class FutureJalaliDateValidator extends BaseValidator implements ConstraintValidator<FutureJalaliDate, Object> {
    private DurationValidator durationType;

    public void initialize(FutureJalaliDate parameters) {
        durationType = parameters.type();
        types = parameters.mapValidateType();
    }

    @Override
    protected boolean doValidate(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        JalaliDate jalaliDate = (JalaliDate) value;
        JalaliDate dateTime = new JalaliDate(jalaliDate.getYear(), jalaliDate.getMonth(), jalaliDate.getDay(),
                jalaliDate.getHour(), jalaliDate.getMinute(), jalaliDate.getSecond());
        if (!dateTime.isValid()) {
            return false;
        }
        JalaliCalendar inputDate = new JalaliCalendar(dateTime);
        switch (durationType) {
            case FROM_TODAY:
                JalaliCalendar today = new JalaliCalendar();
                today.set(JalaliCalendar.HOUR_OF_DAY, 0);
                today.set(JalaliCalendar.MINUTE, 0);
                today.set(JalaliCalendar.SECOND, 0);
                today.set(JalaliCalendar.MILLISECOND, 0);
                return inputDate.compareTo(today) >= 0;
            case FROM_TOMORROW:
            default:
                JalaliCalendar tomorrow = new JalaliCalendar();
                tomorrow.add(JalaliCalendar.DAY_OF_MONTH, 1);
                tomorrow.set(JalaliCalendar.HOUR_OF_DAY, 0);
                tomorrow.set(JalaliCalendar.MINUTE, 0);
                tomorrow.set(JalaliCalendar.SECOND, 0);
                tomorrow.set(JalaliCalendar.MILLISECOND, 0);
                return inputDate.compareTo(tomorrow) >= 0;
        }
    }
}
