package com.tosan.validation.constraints;

import com.tosan.validation.constraints.impl.PastJalaliDateValidator;
import com.tosan.validation.core.MapValidationType;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Hooman Norooziniam, Babak Samadi
 * @version 3.0.1
 * @since Dec 30, 2009
 */
@Constraint(validatedBy = PastJalaliDateValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PastJalaliDate {

    String message() default "The date should be before now";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * @return this parameter indicate if type of the attribute is a Map, so
     * either Key of the Map or Value has to be validated.
     */
    MapValidationType[] mapValidateType() default MapValidationType.VALUE;
}
