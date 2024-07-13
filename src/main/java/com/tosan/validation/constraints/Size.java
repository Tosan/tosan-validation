package com.tosan.validation.constraints;

import com.tosan.validation.constraints.impl.SizeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotated element size must be between the specified boundaries (included).
 * <p/>
 * Supported types are:
 * String (string length is evaluated)
 * Collection(collection size is evaluated)
 * Map (map size is evaluated)
 * Array (array length is evaluated)
 * <p/>
 * null elements are considered valid.
 *
 * @author Babak Samadi
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {SizeValidator.class})
public @interface Size {
    String message() default "The size of the parameter must be between {minSizeKey}{min} and {maxSizeKey}{max}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * @return size the element must be higher or equal to
     */
    int min() default 0;

    /**
     * @return size the element must be lower or equal to
     */
    int max() default Integer.MAX_VALUE;

    /**
     * @return key of the size of the element which must be higher or equal to
     */
    String minSizeKey() default "";

    /**
     * @return key of the size of the element which must be lower or equal to
     */
    String maxSizeKey() default "";


    /**
     * @return this parameter indicate if @Size validation should be applied on inner collection or not
     */
    boolean innerCollectionCheck() default false;


    /**
     * Defines several @Size annotations on the same element
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        Size[] value();
    }
}
