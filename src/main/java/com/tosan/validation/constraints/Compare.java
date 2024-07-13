package com.tosan.validation.constraints;

import com.tosan.validation.constraints.impl.ComparisonValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Boshra Taheri
 * @since 11/23/13
 */
@Documented
@Constraint(validatedBy = ComparisonValidator.class)
@Target({ElementType.TYPE})
@Retention(RUNTIME)
public @interface Compare {
    String lessFieldName();

    String greaterFieldName();

    boolean checkEquality() default true;

    //TODO ba tavajjoh be checkEquality bayad eslah beshe
    String message() default "{lessFieldName} must be less than (or equal to) {greaterFieldName}.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String businessType() default "";
}
