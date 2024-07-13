package com.tosan.validation.constraints;

import com.tosan.validation.constraints.impl.JalaliDateValidator;
import com.tosan.validation.core.MapValidationType;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @author shamsolebad
 * @since Jul 18, 2009
 */
@Constraint(validatedBy = JalaliDateValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidateJalaliDate {

    TypeOfDateValidator type() default TypeOfDateValidator.DATE;

    String message() default "The year must be between 1300 and 1400, the month must be between 1 and 12 and the day must be between 1 and 31";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * @return this parameter indicate if type of the attribute is a Map, so
     * either Key of the Map or Value has to be validated.
     */
    MapValidationType[] mapValidateType() default MapValidationType.VALUE;
}
