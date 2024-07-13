package com.tosan.validation.constraints;

import com.tosan.validation.constraints.impl.MapValidator;
import com.tosan.validation.core.MapValidationType;
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
@Constraint(validatedBy = MapValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
public @interface ValidMap {

    String message() default "Validation failed for an object of {elementType}";

    Class<?> elementType();

    /**
     * @return this parameter indicate if type of the attribute is a Map, so
     * either Key of the Map or Value has to be validated.
     */
    MapValidationType mapValidateType() default MapValidationType.VALUE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String businessType() default "";
}
