package com.tosan.validation.constraints;


import com.tosan.validation.constraints.impl.Base64Validator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Amirhossein Zamanzade
 * @since 10/19/25
 */
@Documented
@Constraint(validatedBy = Base64Validator.class)
@Target({ElementType.FIELD})
@Retention(RUNTIME)
public @interface ValidBase64 {

    String message() default "Base64 size exceeds the maximum allowed limit or is not a valid Base64 string.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    long maxBytesSize() default 0;

    String maxBytesSizeKey() default "";
}
