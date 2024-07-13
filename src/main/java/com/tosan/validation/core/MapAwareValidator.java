package com.tosan.validation.core;

import jakarta.validation.ConstraintValidatorContext;

import java.util.Map;

/**
 * @author Samadi
 */
public abstract class MapAwareValidator<T> {
    private Map<String, String> parameters;

    public MapAwareValidator() {
    }

    public boolean isValid(T value, ConstraintValidatorContext context) {
        return isValidInMap(value, context);
    }

    protected abstract boolean isValidInMap(T value, ConstraintValidatorContext context);

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }
}