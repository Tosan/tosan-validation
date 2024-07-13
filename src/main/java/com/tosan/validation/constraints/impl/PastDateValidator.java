package com.tosan.validation.constraints.impl;

import com.tosan.validation.constraints.PastDate;
import com.tosan.validation.core.BaseValidator;
import com.tosan.validation.util.CalendarUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Boshra Taheri
 * @since 12/20/2014
 */
public class PastDateValidator extends BaseValidator implements ConstraintValidator<PastDate, Object> {
    @Override
    public void initialize(PastDate annotation) {
        types = annotation.mapValidateType();
    }

    @Override
    protected boolean doValidate(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        Date date = CalendarUtils.toDate(value);
        Calendar givenDate = Calendar.getInstance();
        givenDate.setTime(date);

        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        return givenDate.compareTo(now) < 0;
    }
}
