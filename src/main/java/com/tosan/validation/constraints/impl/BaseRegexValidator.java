package com.tosan.validation.constraints.impl;

import com.tosan.validation.constraints.SpecialCharacter;
import com.tosan.validation.core.BaseValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 *
 * @author P.khoshkhou
 * @since 09/04/2023
 */
public abstract class BaseRegexValidator extends BaseValidator {

    protected abstract String getRegex();

    @Override
    protected boolean doValidate(Object value, ConstraintValidatorContext context) {
        String stringValue = (String) value;
        return (stringValue == null || (stringValue.matches(getRegex())));
    }
}