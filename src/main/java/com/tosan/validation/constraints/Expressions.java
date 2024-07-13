package com.tosan.validation.constraints;

import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author shamsolebad
 * @since Sep 10, 2009
 */
@Documented
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RUNTIME)
public @interface Expressions {

    Expression[] value();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
