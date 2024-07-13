package com.tosan.validation.constraints.impl;

import com.tosan.tools.jalali.JalaliCalendar;
import com.tosan.tools.jalali.JalaliDate;
import com.tosan.validation.constraints.PastJalaliDate;
import com.tosan.validation.core.BaseValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author Hooman Noroozinia, Babak Samadi
 * @version 3.0.1
 * @since Dec 30, 2009
 */
public class PastJalaliDateValidator extends BaseValidator implements ConstraintValidator<PastJalaliDate, Object> {
    public void initialize(PastJalaliDate parameters) {
        types = parameters.mapValidateType();
    }

    @Override
    protected boolean doValidate(Object value, ConstraintValidatorContext context) {
        JalaliDate jalaliDate = (JalaliDate) value;
        if (jalaliDate == null) {
            return true;
        }
        JalaliDate dateTime = new JalaliDate(jalaliDate.getYear(), jalaliDate.getMonth(), jalaliDate.getDay(),
                jalaliDate.getHour(), jalaliDate.getMinute(), jalaliDate.getSecond());
        if (!dateTime.isValid()) {
            return false;
        }
        JalaliCalendar jalaliCalendar = new JalaliCalendar(dateTime);
        JalaliCalendar now = new JalaliCalendar();
        return jalaliCalendar.compareTo(now) < 0;
    }
}
