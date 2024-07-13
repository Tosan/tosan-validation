package com.tosan.validation.constraints;

import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Boshra Taheri
 * @since 11/25/13
 * Time: 11:27 AM
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RUNTIME)
public @interface DateDifferences {
    DateDifference[] value();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
