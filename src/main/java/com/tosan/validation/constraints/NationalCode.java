package com.tosan.validation.constraints;

import com.tosan.validation.constraints.impl.NationalCodeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * @author M.Hoseini
 * @since 6/26/2023
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NationalCodeValidator.class)
public @interface NationalCode {

    String message() default "{invalid.nationalCode}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
