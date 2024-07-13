package com.tosan.validation.core;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorFactory;
import jakarta.validation.ValidatorContext;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorFactoryImpl;

import java.util.Map;

/**
 * @author Samadi
 */
public class MapAwareValidatorFactory implements ConstraintValidatorFactory {

    private Map<String, String> parameters;
    private ValidatorContext validatorContext;
    private final ConstraintValidatorFactory constraintValidatorFactory = new ConstraintValidatorFactoryImpl();

    public MapAwareValidatorFactory() {
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public void setValidatorContext(ValidatorContext validatorContext) {
        this.validatorContext = validatorContext;
    }

    public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
        T constraintValidator = constraintValidatorFactory.getInstance(key);
        if (constraintValidator instanceof MapAwareValidator) {
            ((MapAwareValidator) constraintValidator).setParameters(parameters);
        }
        if (ValidatorContextAwareConstraintValidator.class.isAssignableFrom(key)) {
            ValidatorContextAwareConstraintValidator validator = (ValidatorContextAwareConstraintValidator) constraintValidator;
            validator.setValidatorContext(validatorContext);
        }
        return constraintValidator;
    }

    @Override
    public void releaseInstance(ConstraintValidator<?, ?> constraintValidator) {
        constraintValidatorFactory.releaseInstance(constraintValidator);
    }
}