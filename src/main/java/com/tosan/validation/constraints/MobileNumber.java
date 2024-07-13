package com.tosan.validation.constraints;

import com.tosan.validation.constraints.impl.MobileNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

/**
 * @author a.ebrahimi
 * @since 09/04/2023
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = MobileNumberValidator.class)
public @interface MobileNumber {

    String message() default "{invalid.mobile}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
