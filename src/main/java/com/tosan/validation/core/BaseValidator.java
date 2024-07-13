package com.tosan.validation.core;

import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.ClassUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * a super type of the validations which indicate which type of the attribute has
 * to be validated and how!
 *
 * @author Samadi
 */
@SuppressWarnings("rawtypes")
public abstract class BaseValidator<T> extends MapAwareValidator<T> {

    protected MapValidationType[] types;
    protected boolean innerCollectionCheck;
    protected boolean isSizeValidation;

    private static String getPropertyAsString(Properties prop) {
        StringWriter writer = new StringWriter();
        prop.list(new PrintWriter(writer));
        return writer.getBuffer().toString();
    }

    @Override
    public boolean isValidInMap(T value, ConstraintValidatorContext context) {
        if (isSizeValidation) {
            if (!innerCollectionCheck)
                return doValidate(value, context);
        }

        if (value instanceof Map map) {
            boolean isValid = true;
            for (MapValidationType type : types) {
                if (type == MapValidationType.KEY) {
                    for (Object val : map.entrySet()) {
                        Map.Entry entry = (Map.Entry) val;
                        if (!doValidate(entry.getKey(), context)) {
                            boolean isWrapperOrString = ClassUtils.wrapperToPrimitive(entry.getValue().getClass()) != null || entry.getValue() instanceof String;
                            context.disableDefaultConstraintViolation();
                            if (isWrapperOrString) {
                                context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addNode("[key].<" + entry.getKey() + /*"," + entry.getValue() +*/ ">=" + entry.getKey()).addConstraintViolation();
                            } else {
                                context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addNode("[key].<" + entry.getKey() + /*",#" + entry.getValue().hashCode() +*/ ">=" + entry.getKey()).addConstraintViolation();
                            }
                            isValid = false;
                        }
                    }
                }

                if (type == MapValidationType.VALUE) {
                    for (Object val : map.entrySet()) {
                        Map.Entry entry = (Map.Entry) val;
                        if (!doValidate(entry.getValue(), context)) {
                            boolean isWrapperOrString = ClassUtils.wrapperToPrimitive(entry.getKey().getClass()) != null || entry.getKey() instanceof String;
                            context.disableDefaultConstraintViolation();
                            if (isWrapperOrString) {
                                context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addNode("[value].<" + entry.getKey() +/* "," + entry.getValue() +*/ ">=" + entry.getValue()).addConstraintViolation();
                            } else {
                                context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addNode("[value].<#" + entry.getKey().hashCode() +/* "," + entry.getValue() + */">=" + entry.getValue()).addConstraintViolation();
                            }
                            isValid = false;
                        }
                    }
                }
            }
            return isValid;
        } else if (value instanceof List list) {
            boolean isValid = true;
            for (Object object : list) {
                if (!doValidate(object, context)) {
                    String newPropertyPathStr;
                    newPropertyPathStr = "[" + object + "] =" + object;
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                            .addNode(newPropertyPathStr).addConstraintViolation();
                    isValid = false;
                }
            }
            return isValid;
        } else if (value instanceof Collection collection) {
            boolean isValid = true;
            for (Object object : collection) {
                if (!doValidate(object, context)) {
                    String newPropertyPathStr;
                    newPropertyPathStr = "[" + object + "] =" + object;
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                            .addNode(newPropertyPathStr).addConstraintViolation();
                    isValid = false;
                }
            }
            return isValid;
        }
        return doValidate(value, context);
    }

    protected abstract boolean doValidate(Object value, ConstraintValidatorContext context);
}
