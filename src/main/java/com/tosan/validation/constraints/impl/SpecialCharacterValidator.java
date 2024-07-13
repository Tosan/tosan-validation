package com.tosan.validation.constraints.impl;

import com.tosan.validation.constraints.SpecialCharacter;
import jakarta.validation.ConstraintValidator;

/**
 * @author P.khoshkhou
 * @since 09/04/2023
 */
public class SpecialCharacterValidator extends BaseRegexValidator implements ConstraintValidator<SpecialCharacter, Object> {

    public static final String SPECIAL_CHARACTER_PATTERN = "^[^^<>%$@#&*()!{}':;`]*$";

    @Override
    public void initialize(SpecialCharacter constraintAnnotation) {
    }

    @Override
    protected String getRegex() {
        return SPECIAL_CHARACTER_PATTERN;
    }
}