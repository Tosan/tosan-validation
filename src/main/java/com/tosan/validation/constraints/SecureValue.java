package com.tosan.validation.constraints;

import com.tosan.validation.constraints.impl.SecureValueValidator;
import com.tosan.validation.core.MapValidationType;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * @author Akhbari
 * @since 16/10/2016
 */
@Constraint(validatedBy = {SecureValueValidator.class})
@Target({FIELD, METHOD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface SecureValue {

    /**
     * The excluded values must be separated by dash.
     */
    String excludeValuesKey() default "";

    String message() default "The value is included in exclude values : {excludeValues}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    MapValidationType[] mapValidateType() default MapValidationType.VALUE;
}
