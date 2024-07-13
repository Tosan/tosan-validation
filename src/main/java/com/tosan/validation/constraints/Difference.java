package com.tosan.validation.constraints;

import com.tosan.validation.constraints.impl.GeneralDifferenceValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * برای کنترل فاصله‌ی دو مقدار عددی و یا دو تاریخ میلادی یا جلالی
 * در صورتی که برای کنترل فاصله‌ی تاریخی از این validation استفاده شود، فاصله بر حسب میلی‌ثانیه سنجیده می‌شود.
 *
 * @author Boshra Taheri
 * @since 11/24/13
 */
@Documented
@Constraint(validatedBy = GeneralDifferenceValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Difference {

    String fromFieldName();

    String toFieldName();

    long maxDifference() default Long.MAX_VALUE;

    String maxDifferenceKey() default "";

    long minDifference() default Long.MIN_VALUE;

    String minDifferenceKey() default "";

    String message() default "The difference between {fromFieldName} and {toFieldName} must be between {minDifferenceKey}{minDifference} and {maxDifferenceKey}{maxDifference}.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String businessType() default "";
}
