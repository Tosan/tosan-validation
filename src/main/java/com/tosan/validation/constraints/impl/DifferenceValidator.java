package com.tosan.validation.constraints.impl;

import com.tosan.validation.core.MapAwareValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author Boshra Taheri
 * @since 11/25/13
 */
public abstract class DifferenceValidator extends MapAwareValidator<Object> {

    @Override
    protected boolean isValidInMap(Object value, ConstraintValidatorContext context) {
        return evaluate(value);
    }

    private boolean evaluate(Object value) {
        long actualDifference = getActualDifference(value);
        return !(actualDifference < getMinDifference() || actualDifference > getMaxDifference());
    }

    public abstract long getMaxDifference();

    public abstract long getMinDifference();

    protected abstract long getActualDifference(Object value);
}
