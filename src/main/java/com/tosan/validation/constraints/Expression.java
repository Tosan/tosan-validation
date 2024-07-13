package com.tosan.validation.constraints;

import com.tosan.validation.constraints.impl.ExpressionValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * One of the constant value or keyValue of the Expression annotation parameters
 * will use in validation. only one of theme should be use otherwise an
 * IllegalArgumentException will throw
 *
 * @author shamsolebad
 * @since Sep 10, 2009
 */
@Documented
@Constraint(validatedBy = ExpressionValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RUNTIME)
public @interface Expression {

    String identifier() default "";

    /**
     * The expression to validate against. if expression return true, annotated subject is valid.
     */
    String value() default "";

    String message() default "The following condition must be satisfied: {keyValue}{value}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * a key in a propertied file or in a map which use in validation, instead use a constant value.
     */
    String keyValue() default "";
}
