package com.tosan.validation.constraints;

import com.tosan.validation.constraints.impl.CollectionValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Boshra Taheri
 * @since 7/29/13
 */
@Documented
@Constraint(validatedBy = CollectionValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
public @interface ValidCollection {
    String message() default "Validation failed for an object of {elementType}";

    Class<?> elementType();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String businessType() default "";
}
