package com.tosan.validation.constraints.impl;

import com.tosan.tools.jalali.JalaliDate;
import com.tosan.validation.constraints.TypeOfDateValidator;
import com.tosan.validation.constraints.ValidateJalaliDate;
import com.tosan.validation.core.BaseValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author shamsolebad, Babak Samadi
 */
public class JalaliDateValidator extends BaseValidator implements ConstraintValidator<ValidateJalaliDate, Object> {
    private TypeOfDateValidator typeOfDate;

    public void initialize(ValidateJalaliDate parameters) {
        typeOfDate = parameters.type();
        types = parameters.mapValidateType();
    }

    @Override
    protected boolean doValidate(Object value, ConstraintValidatorContext context) {

        JalaliDate jalaliDate = (JalaliDate) value;
        if (jalaliDate == null)
            return true;
        boolean result = false;
        if (jalaliDate.getYear() >= 1300 && jalaliDate.getYear() <= 2000) {
            if (jalaliDate.getMonth() >= 1 && jalaliDate.getMonth() <= 12) {
                if (jalaliDate.getDay() >= 1 && jalaliDate.getDay() <= 31) {
                    result = true;
                }
            }
        }
        if (typeOfDate.equals(TypeOfDateValidator.DATE_TIME)) {
            result = false;
            if (jalaliDate.getHour() >= 0 && jalaliDate.getHour() <= 23) {
                if (jalaliDate.getMinute() >= 0 && jalaliDate.getMinute() <= 59) {
                    if (jalaliDate.getSecond() >= 0 && jalaliDate.getSecond() <= 59) {
                        result = true;
                    }
                }
            }
        }
        return result;
    }
}
