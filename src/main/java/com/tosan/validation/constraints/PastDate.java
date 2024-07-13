package com.tosan.validation.constraints;

import com.tosan.validation.constraints.impl.PastDateValidator;
import com.tosan.validation.core.MapValidationType;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Boshra Taheri
 * @since 12/20/2014
 */
@Documented
@Constraint(validatedBy = {PastDateValidator.class})
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface PastDate {

    String message() default "The date should be before now";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * @return this parameter indicate if type of the attribute is a Map, so
     * either Key of the Map or Value has to be validated.
     */
    MapValidationType[] mapValidateType() default MapValidationType.VALUE;
}
