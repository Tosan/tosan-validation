package com.tosan.validation.constraints.impl;

import com.tosan.validation.constraints.NationalCode;
import com.tosan.validation.core.BaseValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author M.Hoseini
 * @since 6/26/2023
 */
public class NationalCodeValidator extends BaseValidator implements ConstraintValidator<NationalCode, Object> {

    @Override
    public void initialize(NationalCode constraintAnnotation) {
    }

    @Override
    protected boolean doValidate(Object value, ConstraintValidatorContext context) {
        String stringValue = (String) value;
        return (stringValue == null || (stringValue.matches("[0-9]+") && isNationalCodeValid(stringValue)));
    }

    private boolean isNationalCodeValid(String nationalCodeStr) {
        char[] nationalCode = nationalCodeStr.toCharArray();
        if (nationalCode.length != 10) {
            return false;
        }
        int nCD = 0;
        int nSum = 0;
        for (int i = 0; i < 10; i++) {
            int d = Integer.parseInt(String.valueOf(nationalCode[i]));
            if (i == 9) {
                nCD = d;    //Extract existing check digit
            } else {
                nSum += d * (10 - i);
            }
        }
        int nRmndr = nSum % 11;    //Calculate check digit according to the given number
        if (nRmndr > 1) {
            return (nCD == (11 - nRmndr));    // OK, check digit matchs correctly
        } else {
            return nCD == nRmndr;  // OK, check digit matchs correctly
        }
    }
}