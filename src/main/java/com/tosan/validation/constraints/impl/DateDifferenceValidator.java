package com.tosan.validation.constraints.impl;

import com.tosan.validation.constraints.DateDifference;
import com.tosan.validation.constraints.DateDifferenceUnit;
import com.tosan.validation.util.date.DateTimeComparatorContext;
import jakarta.validation.ConstraintValidator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * @author Boshra Taheri
 * @since 11/24/13
 */
public class DateDifferenceValidator extends DifferenceValidator
        implements ConstraintValidator<DateDifference, Object> {
    private static final Logger logger = LoggerFactory.getLogger(DateDifferenceValidator.class);
    private String fromFieldName;
    private String toFieldName;
    private String maxDifferenceKey;
    private long maxDifference;
    private String minDifferenceKey;
    private long minDifference;
    private DateDifferenceUnit unit;

    @Override
    public void initialize(DateDifference constraintAnnotation) {
        this.fromFieldName = constraintAnnotation.fromFieldName();
        this.toFieldName = constraintAnnotation.toFieldName();
        this.maxDifference = constraintAnnotation.maxDifference();
        this.maxDifferenceKey = constraintAnnotation.maxDifferenceKey();
        this.minDifference = constraintAnnotation.minDifference();
        this.minDifferenceKey = constraintAnnotation.minDifferenceKey();
        this.unit = constraintAnnotation.unit();
    }

    @Override
    public long getMaxDifference() {
        if (StringUtils.isNotBlank(maxDifferenceKey)) {
            if (StringUtils.isBlank(getParameters().get(maxDifferenceKey))) {
                throw new IllegalArgumentException("No value was found for defined key(" + maxDifferenceKey + ")");
            }
            try {
                maxDifference = Long.parseLong(getParameters().get(maxDifferenceKey));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("value of the defined key(" + maxDifferenceKey + ") is not correct");
            }
        }
        return maxDifference;
    }

    @Override
    public long getMinDifference() {
        if (StringUtils.isNotBlank(minDifferenceKey)) {
            if (StringUtils.isBlank(getParameters().get(minDifferenceKey))) {
                throw new IllegalArgumentException("No value was found for defined key(" + minDifferenceKey + ")");
            }
            try {
                minDifference = Long.parseLong(getParameters().get(minDifferenceKey));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("value of the defined key(" + minDifferenceKey + ") is not correct");
            }
        }
        return minDifference;
    }

    @Override
    protected long getActualDifference(Object value) {
        long actualDifference;
        long differenceInMilliseconds;
        try {
            Field fromField = findTargetField(value.getClass(), fromFieldName);
            Field toField = findTargetField(value.getClass(), toFieldName);
            if (!fromField.getType().getName().equals(toField.getType().getName())) {
                throw new IllegalArgumentException("Types of 'from' date and 'to' date must be the same.");
            }
            fromField.setAccessible(true);
            toField.setAccessible(true);
            Object fromObj = fromField.get(value);
            Object toObj = toField.get(value);
            Class fieldType = fromField.getType();
            DateTimeComparatorContext dateTimeComparatorContext = new DateTimeComparatorContext(fieldType);
            differenceInMilliseconds = dateTimeComparatorContext.doCompare(fieldType.cast(toObj),
                    fieldType.cast(fromObj), DateDifferenceUnit.millisecond);
            actualDifference = dateTimeComparatorContext.doCompare(fieldType.cast(toObj), fieldType.cast(fromObj),
                    unit);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
        if (differenceInMilliseconds < 0) {
            logger.info("'to' date must be after 'from' date.");
            return differenceInMilliseconds;
        }
        return actualDifference;
    }

    @Override
    protected String getFromFieldName() {
        return fromFieldName;
    }

    @Override
    protected String getToFieldName() {
        return toFieldName;
    }
}
