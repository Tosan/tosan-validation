package com.tosan.validation.constraints.impl;

import com.tosan.validation.constraints.DurationValidator;
import com.tosan.validation.constraints.FutureDate;
import com.tosan.validation.core.BaseValidator;
import com.tosan.validation.util.CalendarUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Boshra Taheri
 * @since 12/14/13
 */
public class FutureDateValidator extends BaseValidator implements ConstraintValidator<FutureDate, Object> {
    private DurationValidator durationType;

    public void initialize(FutureDate parameters) {
        durationType = parameters.type();
        types = parameters.mapValidateType();
    }

    @Override
    protected boolean doValidate(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        Date date = CalendarUtils.toDate(value);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        switch (durationType) {
            case FROM_TODAY:
                Calendar today = Calendar.getInstance();
                today.set(Calendar.HOUR_OF_DAY, 0);
                today.set(Calendar.MINUTE, 0);
                today.set(Calendar.SECOND, 0);
                today.set(Calendar.MILLISECOND, 0);
                return calendar.compareTo(today) >= 0;
            case FROM_TODAY_TIME:
                Calendar todayTime = Calendar.getInstance();
                return calendar.compareTo(todayTime) >= 0;
            case FROM_TOMORROW_TIME:
                Calendar tomorrowTime = Calendar.getInstance();
                tomorrowTime.add(Calendar.DAY_OF_MONTH, 1);
                return calendar.compareTo(tomorrowTime) >= 0;
            case FROM_TOMORROW:
            default:
                Calendar tomorrow = Calendar.getInstance();
                tomorrow.add(Calendar.DAY_OF_MONTH, 1);
                tomorrow.set(Calendar.HOUR_OF_DAY, 0);
                tomorrow.set(Calendar.MINUTE, 0);
                tomorrow.set(Calendar.SECOND, 0);
                tomorrow.set(Calendar.MILLISECOND, 0);
                return calendar.compareTo(tomorrow) >= 0;
        }
    }
}
