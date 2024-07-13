package com.tosan.validation.constraints.impl;

import com.tosan.validation.constraints.UUID;
import jakarta.validation.ConstraintValidator;

/**
 * @author P.khoshkhou
 * @since 09/04/2023
 */
public class UUIDValidator extends BaseRegexValidator implements ConstraintValidator<UUID, Object> {

    public static final String UUID_REGEX = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}";

    @Override
    public void initialize(UUID constraintAnnotation) {
    }

    @Override
    protected String getRegex() {
        return UUID_REGEX;
    }
}