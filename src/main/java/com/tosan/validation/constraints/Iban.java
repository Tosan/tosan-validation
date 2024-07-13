package com.tosan.validation.constraints;

import com.tosan.validation.constraints.impl.IbanValidator;
import com.tosan.validation.core.MapValidationType;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * @author Habib Motallebpour
 * @since 16/07/2016
 */
@Constraint(validatedBy = {IbanValidator.class})
@Target({FIELD, METHOD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Iban {

    String message() default "IBAN must be 26 Character starting with IR and with IBAN pattern";

    String iban() default "";

    boolean isTosanBank() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * @return this parameter indicate if type of the attribute is a Map, so
     * either Key of the Map or Value has to be validated.
     */
    MapValidationType[] mapValidateType() default MapValidationType.VALUE;
}
