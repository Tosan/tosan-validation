package com.tosan.validation.constraints;

import com.tosan.validation.constraints.impl.PastDateTimeValidator;
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
 * @author Marjan Mehranfar
 * @since 13/07/2019
 */
@Documented
@Constraint(validatedBy = {PastDateTimeValidator.class})
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface PastDateTime {

    String message() default "The date should be before now";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * @return this parameter indicate if type of the attribute is a Map, so
     * either Key of the Map or Value has to be validated.
     */
    MapValidationType[] mapValidateType() default MapValidationType.VALUE;
}
