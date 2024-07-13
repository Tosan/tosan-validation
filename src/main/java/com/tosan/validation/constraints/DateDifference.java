package com.tosan.validation.constraints;

import com.tosan.validation.constraints.impl.DateDifferenceValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * برای کنترل فاصله‌ی دو تاریخ میلادی یا جلالی
 *
 * @author Boshra Taheri
 * @since 11/24/13
 */
@Documented
@Constraint(validatedBy = DateDifferenceValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateDifference {
    String fromFieldName();

    String toFieldName();

    long maxDifference() default Long.MAX_VALUE;

    String maxDifferenceKey() default "";

    long minDifference() default 0;

    String minDifferenceKey() default "";

    DateDifferenceUnit unit() default DateDifferenceUnit.day;

    String message() default "The difference between {fromFieldName} and {toFieldName} must be between {minDifferenceKey}{minDifference} and {maxDifferenceKey}{maxDifference} {unit}(s).";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String businessType() default "";
}
