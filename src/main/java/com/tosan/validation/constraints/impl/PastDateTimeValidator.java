package com.tosan.validation.constraints.impl;

import com.tosan.validation.constraints.PastDateTime;
import com.tosan.validation.core.BaseValidator;
import com.tosan.validation.util.CalendarUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Marjan Mehranfar
 * @since 13/07/2019
 */
public class PastDateTimeValidator extends BaseValidator implements ConstraintValidator<PastDateTime, Object> {

    @Override
    public void initialize(PastDateTime annotation) {
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
        return givenDate.compareTo(now) < 0;
    }
}

