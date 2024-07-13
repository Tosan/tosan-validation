package com.tosan.validation.constraints;

import com.tosan.validation.constraints.impl.GroupValidator;
import com.tosan.validation.core.MapValidationType;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * This annotation can use in validate a filed or parameter value which force
 * the value has to be included or exclude in defined group of values.
 * this annotation only accept one type of value set(longSet or doubleSet) and also
 * can not work without one of theme.
 *
 * @author Samadi.
 */
@Documented
@Constraint(validatedBy = {GroupValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
public @interface Group {

    String message() default "Given value is not included in/excluded from the defined group";

    double[] pointLiterals() default {};

    long[] integerLiterals() default {};

    /**
     * @return This parameter indicate if type of the attribute is a Map, so
     * either Key of the Map or Value has to be validated.
     */
    MapValidationType[] mapValidateType() default MapValidationType.VALUE;

    /**
     * check the value has to be included in the given set of value or to be excluded.
     */
    boolean isInclude() default true;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
