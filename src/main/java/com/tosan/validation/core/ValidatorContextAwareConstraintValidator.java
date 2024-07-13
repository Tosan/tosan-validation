package com.tosan.validation.core;

import jakarta.validation.ValidatorContext;

/**
 * @author Boshra Taheri
 * @since 7/31/13
 */
public interface ValidatorContextAwareConstraintValidator {

    void setValidatorContext(ValidatorContext validatorContext);
}
