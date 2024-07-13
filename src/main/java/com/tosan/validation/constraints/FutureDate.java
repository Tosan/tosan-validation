package com.tosan.validation.constraints;

import com.tosan.validation.constraints.impl.FutureDateValidator;
import com.tosan.validation.core.MapValidationType;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @author Boshra Taheri
 * @since 12/14/13
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = FutureDateValidator.class)
@Target(ElementType.FIELD)
public @interface FutureDate {

    DurationValidator type() default DurationValidator.FROM_TOMORROW;

    String message() default "The date should be after now.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * @return this parameter indicate if type of the attribute is a Map, so
     * either Key of the Map or Value has to be validated.
     */
    MapValidationType[] mapValidateType() default MapValidationType.VALUE;
}
