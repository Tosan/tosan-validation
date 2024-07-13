package com.tosan.validation.constraints;

import com.tosan.validation.constraints.impl.SpecialCharacterValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @author P.khoshkhou
 * @since 09/04/2023
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = SpecialCharacterValidator.class)
public @interface SpecialCharacter {

    String message() default "{invalid.character}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
