package com.tosan.validation.constraints;

import com.tosan.validation.constraints.impl.ConditionalNotEmptyValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @author F.Ebrahimi
 * @since 5/12/2024
 */
@Repeatable(ConditionalsNotEmpty.class)
@Constraint(validatedBy = ConditionalNotEmptyValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConditionalNotEmpty {

    String message() default "This field is required.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String selected();

    String condition();
}
