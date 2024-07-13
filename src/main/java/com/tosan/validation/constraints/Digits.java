package com.tosan.validation.constraints;

import com.tosan.validation.constraints.impl.DigitsValidator;
import com.tosan.validation.core.MapValidationType;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotated element must be a number within accepted range Supported types are:
 * <ul>
 * <li><code>BigDecimal</code></li>
 * <li><code>BigInteger</code></li>
 * <li><code>String</code></li>
 * <li><code>byte</code>, <code>short</code>, <code>int</code>,
 * <code>long</code>, and their respective wrapper types</li>
 * </ul>
 * <p/>
 * <code>null</code> elements are considered valid This can also take a key
 * instead of a constant value.
 *
 * @author Emmanuel Bernard, Babak Samadi
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {DigitsValidator.class})
public @interface Digits {

    String message() default "The number of integer digits must be less than {integerKey}{integer} and" +
            " the number of fraction digits must be less than {fractionKey}{fraction}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * @return maximum number of integral digits accepted for this number.
     */
    int integer() default 0;

    /**
     * @return maximum number of fractional digits accepted for this number.
     */
    int fraction() default Integer.MAX_VALUE;

    /**
     * @return maximum number of integral digits key accepted for this number.
     */
    String integerKey() default "";

    /**
     * @return maximum number of fractional digits key accepted for this number.
     */
    String fractionKey() default "";

    /**
     * @return this parameter can be used to ignore trailing Zeros after maximum fraction digits in validation
     */
    boolean ignoreTrailingZerosAfterMaxFractionLength() default false;

    /**
     * @return this parameter indicate if type of the attribute is a Map, so
     * either Key of the Map or Value has to be validated.
     */
    MapValidationType[] mapValidateType() default MapValidationType.VALUE;

    /**
     * Defines several <code>@Digits</code> annotations on the same element
     *
     * @author Emmanuel Bernard
     * @see Digits
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        Digits[] value();
    }
}
