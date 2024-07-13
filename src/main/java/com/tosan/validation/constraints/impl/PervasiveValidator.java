package com.tosan.validation.constraints.impl;

import com.tosan.validation.constraints.Pervasive;
import com.tosan.validation.core.BaseValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author R.Mehri
 * @since 1/9/2022
 */
public class PervasiveValidator extends BaseValidator<String> implements ConstraintValidator<Pervasive, String> {
    public static final String PERVASIVE_ID_PATTERN = "\\d{7,13}$";

    @Override
    protected boolean doValidate(Object value, ConstraintValidatorContext context) {
        String pervasiveCode;
        if (value == null) {
            return true;
        }
        if (value instanceof String) {
            pervasiveCode = (String) value;
        } else {
            return false;
        }
        Pattern pattern = Pattern.compile(PERVASIVE_ID_PATTERN);
        Matcher matcher = pattern.matcher(pervasiveCode);
        return matcher.matches() && doLuhn(pervasiveCode, false) % 10 == 0;
    }

    @Override
    public void initialize(Pervasive annotation) {
        types = annotation.mapValidateType();
    }

    private int doLuhn(String s, boolean evenPosition) {
        int sum = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(s.substring(i, i + 1));
            if (evenPosition) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            evenPosition = !evenPosition;
        }
        return sum;
    }
}
