package com.tosan.validation.constraints.impl;

import com.tosan.validation.constraints.Length;
import com.tosan.validation.core.BaseValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Check that a string's length is between min and max.
 *
 * @author Samadi
 */
public class LengthValidator extends BaseValidator implements ConstraintValidator<Length, Object> {
    private String minKey;
    private String maxKey;
    private int min;
    private int max;
    private Map<String, String> parameters;

    public void initialize(Length annotation) {
        minKey = annotation.minKey();
        maxKey = annotation.maxKey();
        min = annotation.min();
        max = annotation.max();
        parameters = getParameters();
        types = annotation.mapValidateType();
    }

    @Override
    public boolean doValidate(Object value, ConstraintValidatorContext context) {
        int minValue = min;
        if (StringUtils.isNotBlank(minKey)) {
            if (StringUtils.isBlank(parameters.get(minKey))) {
                throw new IllegalArgumentException("No value was found for defined key(" + minKey + ")");
            }
            try {
                minValue = Integer.parseInt(parameters.get(minKey));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("value of the defined key(" + minKey + ") is not correct");
            }
        }

        int maxValue = max;
        if (StringUtils.isNotBlank(maxKey)) {
            if (StringUtils.isBlank(parameters.get(maxKey))) {
                throw new IllegalArgumentException("No value was found for defined key(" + maxKey + ")");
            }
            try {
                maxValue = Integer.parseInt(parameters.get(maxKey));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("value of the defined key(" + maxKey + ") is not correct");
            }
        }

        if (minValue < 0) {
            throw new IllegalArgumentException("The min parameter cannot be negative.");
        }
        if (maxValue < 0) {
            throw new IllegalArgumentException("The max parameter cannot be negative.");
        }
        if (maxValue < minValue) {
            throw new IllegalArgumentException("The maxValue must be greater than minValue.");
        }

        if (value == null) {
            return true;
        }

        int length = value.toString().length();
        return length >= minValue && length <= maxValue;
    }
}
