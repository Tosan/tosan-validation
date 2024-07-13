package com.tosan.validation.constraints.impl;

import com.tosan.validation.constraints.MobileNumber;
import jakarta.validation.ConstraintValidator;

/**
 * @author P.khoshkhou
 * @since 09/04/2023
 */
public class MobileNumberValidator extends BaseRegexValidator implements ConstraintValidator<MobileNumber, Object> {

    public static final String MOBILE_NUMBER_PATTERN = "^(\\+\\d{1,4}\\s?)?\\d{7,14}$";

    @Override
    public void initialize(MobileNumber constraintAnnotation) {
    }

    @Override
    protected String getRegex() {
        return MOBILE_NUMBER_PATTERN;
    }
}