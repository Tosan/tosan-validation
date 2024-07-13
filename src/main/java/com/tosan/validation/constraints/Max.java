package com.tosan.validation.constraints;

import com.tosan.validation.constraints.impl.MaxValidator;
import com.tosan.validation.core.MapValidationType;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Custom Max validation which can use key-value for validation instead using a constant value.
 * can not use both keyValue and value as parameters in the Max annotation or both keyValue and value can not be null
 * otherwise an IllegalArgumentException will be thrown.
 *
 * @author Samadi
 */
@Documented
@Constraint(validatedBy = {MaxValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
public @interface Max {

    String message() default "The value must be less than {keyValue}{value}";

    /**
     * a key in a propertied file or in a map which use in validation, instead
     * use a constant value.
     */
    String keyValue() default "";

    long value() default -1;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean checkEquality() default true;

    /**
     * @return this parameter indicate if type of the attribute is a Map, so
     * either Key of the Map or Value has to be validated.
     */
    MapValidationType[] mapValidateType() default MapValidationType.VALUE;
}
