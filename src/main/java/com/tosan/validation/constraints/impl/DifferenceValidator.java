package com.tosan.validation.constraints.impl;

import com.tosan.validation.core.MapAwareValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * @author Boshra Taheri
 * @since 11/25/13
 */
public abstract class DifferenceValidator extends MapAwareValidator<Object> {
    private static final Logger logger = LoggerFactory.getLogger(DifferenceValidator.class);

    @Override
    protected boolean isValidInMap(Object value, ConstraintValidatorContext context) {
        return evaluate(value);
    }

    protected static Object getTargetObject(Object value, String fieldName) {
        Field field = findTargetField(value.getClass(), fieldName);
        field.setAccessible(true);
        try {
            return field.get(value);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public abstract long getMaxDifference();

    public abstract long getMinDifference();

    protected abstract long getActualDifference(Object value);

    protected static Field findTargetField(Class rootBeanClass, String fieldName) {
        Field targetField = null;
        NoSuchFieldException exception = new NoSuchFieldException();
        while (targetField == null && !rootBeanClass.equals(Object.class)) {
            try {
                targetField = rootBeanClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                rootBeanClass = rootBeanClass.getSuperclass();
            }
        }
        if (targetField != null) {
            return targetField;
        } else {
            String exceptionMessage = "Field with name " + fieldName + " does not exist.";
            logger.error(exceptionMessage);
            throw new IllegalArgumentException(exceptionMessage, exception);

        }
    }

    private boolean evaluate(Object value) {
        Object fromTargetObject = getTargetObject(value, getFromFieldName());
        Object toTargetObject = getTargetObject(value, getToFieldName());
        if (fromTargetObject == null || toTargetObject == null) {
            return true;
        }
        long actualDifference = getActualDifference(value);
        return !(actualDifference < getMinDifference() || actualDifference > getMaxDifference());
    }

    protected abstract String getFromFieldName();

    protected abstract String getToFieldName();
}
