package com.tosan.validation.constraints;

import com.tosan.validation.constraints.impl.EncodingValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Maryam Madani
 * @since 18/01/2017
 */
@Constraint(validatedBy = EncodingValidator.class)
@Target({PARAMETER, FIELD})
@Retention(RUNTIME)
public @interface Encoding {

    String encoding();

    String message() default "The Encoding is not supported.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}