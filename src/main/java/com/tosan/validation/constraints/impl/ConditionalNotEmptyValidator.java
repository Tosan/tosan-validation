package com.tosan.validation.constraints.impl;

import com.tosan.validation.constraints.ConditionalNotEmpty;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.reflect.FieldUtils;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

/**
 * @author F.Ebrahimi
 * @since 5/12/2024
 */
public class ConditionalNotEmptyValidator implements ConstraintValidator<ConditionalNotEmpty, Object> {

    private String selected;

    private String message;

    private String condition;

    @Override
    public void initialize(ConditionalNotEmpty conditionalNotEmpty) {
        selected = conditionalNotEmpty.selected();
        message = conditionalNotEmpty.message();
        condition = conditionalNotEmpty.condition();
    }

    @Override
    public boolean isValid(Object objectToValidate, ConstraintValidatorContext context) {
        boolean valid = true;
        try {
            Object actualValue = FieldUtils.readField(objectToValidate, selected, true);
            Object conditionValue = FieldUtils.readField(objectToValidate, condition, true);
            if (conditionValue != null && !isEmpty(conditionValue) && Boolean.parseBoolean(conditionValue.toString())) {
                valid = actualValue != null && !isEmpty(actualValue);
                if (!valid) {
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate(message).addPropertyNode(selected).addConstraintViolation();
                }
            }
            return valid;
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }
}