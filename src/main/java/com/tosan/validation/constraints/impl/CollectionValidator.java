package com.tosan.validation.constraints.impl;

import com.tosan.validation.constraints.ValidCollection;
import com.tosan.validation.core.BaseValidator;
import com.tosan.validation.core.ValidatorContextAwareConstraintValidator;
import jakarta.validation.*;
import jakarta.validation.metadata.ConstraintDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author Boshra Taheri
 * @since 7/29/13
 */
@SuppressWarnings("rawtypes")
public class CollectionValidator extends BaseValidator implements ConstraintValidator<ValidCollection, Object>, ValidatorContextAwareConstraintValidator {

    private static final Logger logger = LoggerFactory.getLogger(CollectionValidator.class);
    private ValidatorContext validatorContext;
    private Class<?> elementType;
    private static final ThreadLocal<Map<String, String>> messageAndPath = new ThreadLocal<>();

    public static void removeMessageAndPath() {
        messageAndPath.remove();
    }

    @Override
    protected boolean doValidate(Object value, ConstraintValidatorContext context) {
        //todo
        return false;
    }

    @Override
    public void setValidatorContext(ValidatorContext validatorContext) {
        this.validatorContext = validatorContext;
    }

    @Override
    public void initialize(ValidCollection constraintAnnotation) {
        elementType = constraintAnnotation.elementType();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        initializeMessageAndPath();
        Collection collection = (Collection) value;
        boolean isOrdered = collection instanceof List;
        boolean valid = true;

        if (collection == null || collection.isEmpty()) {
            return true;
        }

        Validator validator = validatorContext.getValidator();

        boolean beanConstrained = validator.getConstraintsForClass(elementType).isBeanConstrained();
        int i = 0;
        for (Object element : collection) {
            Set<ConstraintViolation<?>> violations = new HashSet<>();

            if (beanConstrained) {
                Set<ConstraintViolation<Object>> constraintViolations = validator.validate(element);
                violations.addAll(constraintViolations);
            }

            if (!violations.isEmpty()) {
                valid = false;
                for (ConstraintViolation<?> violation : violations) {
                    context.disableDefaultConstraintViolation();
                    String message = createMessage(violation, element);
                    logger.debug("massage: {}", message);
                    logger.debug(violation.getMessage());
                    String path = violation.getPropertyPath().toString();
                    String[] pathPart = path.split("\\.");
                    if (path.contains(".") && !path.contains("[")) {
                        messageAndPath.get().put(pathPart[pathPart.length - 1], message);
                    } else {
                        messageAndPath.get().put(violation.getPropertyPath().toString(), message);
                    }
                    ConstraintValidatorContext.ConstraintViolationBuilder violationBuilder;
                    if (pathPart.length > 1 && path.contains("=")) {
                        if (!path.contains(".=")) {
                            String object = pathPart[pathPart.length - 1].substring(0, pathPart[pathPart.length - 1].indexOf("="));
                            if (messageAndPath.get().get(object) != null) {
                                violationBuilder = context.buildConstraintViolationWithTemplate(messageAndPath.get().get(object));
                            } else {
                                violationBuilder = context.buildConstraintViolationWithTemplate(MapValidator.getMessageAndPath().get().get(object));
                            }
                        } else {
                            violationBuilder = context.buildConstraintViolationWithTemplate(violation.getMessage());
                        }
                    } else {
                        violationBuilder = context.buildConstraintViolationWithTemplate(message);
                    }


                    String newPropertyPathStr;
                    /*boolean isWrapperOrString = ClassUtils.wrapperToPrimitive(element.getClass()) != null || element instanceof String;
                    if (isWrapperOrString) {
                        newPropertyPathStr = "[" + element + "]." + violation.getPropertyPath();
                    } else*/
                    if (isOrdered) {
                        newPropertyPathStr = "[@" + i + "]." + violation.getPropertyPath();
                    } else {
                        newPropertyPathStr = "[#" + element.hashCode() + "]." + violation.getPropertyPath();
                    }
                    if (!(violation.getInvalidValue() instanceof Collection)
                            && !(violation.getInvalidValue() instanceof Map)) {
                        violationBuilder.addNode(newPropertyPathStr + "=" + violation.getInvalidValue()).addConstraintViolation();
                    } else {
                        violationBuilder.addNode(newPropertyPathStr).addConstraintViolation();
                    }
                }
            }
            i++;

        }
        return valid;
    }

    private String createMessage(ConstraintViolation violation, Object value) {
        ConstraintDescriptor descriptor = violation.getConstraintDescriptor();
        StringBuilder propertiesStrBuilder = new StringBuilder("-- listing properties --" + "\n");
        Properties properties = new Properties();

        for (Object val : descriptor.getAttributes().entrySet()) {
            Map.Entry entry = (Map.Entry) val;
            properties.put(entry.getKey(), entry.getValue().toString());
            if (entry.getKey().equals("message")) {
                propertiesStrBuilder.append(entry.getKey()).append("=").append(violation.getMessage()).append("\n");

            } else {
                propertiesStrBuilder.append(entry.getKey()).append("=").append(entry.getValue().toString()).append("\n");
            }
        }
        properties.put("annotation", descriptor.getAnnotation().annotationType().getCanonicalName());
        propertiesStrBuilder.append("annotation=").append(properties.get("annotation").toString());
        String propertiesStr = propertiesStrBuilder.toString();
        if (propertiesStr.startsWith("-- listing properties --")) {
            propertiesStr = propertiesStr.replace("-- listing properties --", "");
        }
        return propertiesStr;
    }

    public static ThreadLocal<Map<String, String>> getMessageAndPath() {
        return messageAndPath;
    }

    public void initializeMessageAndPath() {
        if (messageAndPath.get() == null) {
            messageAndPath.set(new HashMap<>());
        }
    }
}

