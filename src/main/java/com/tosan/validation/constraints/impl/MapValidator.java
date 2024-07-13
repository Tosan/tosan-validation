package com.tosan.validation.constraints.impl;

import com.tosan.validation.constraints.ValidMap;
import com.tosan.validation.core.BaseValidator;
import com.tosan.validation.core.MapValidationType;
import com.tosan.validation.core.ValidatorContextAwareConstraintValidator;
import jakarta.validation.*;
import jakarta.validation.metadata.ConstraintDescriptor;
import org.apache.commons.lang3.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author Boshra Taheri
 * @since 7/29/13
 */
@SuppressWarnings("rawtypes")
public class MapValidator extends BaseValidator implements ConstraintValidator<ValidMap, Object>, ValidatorContextAwareConstraintValidator {

    private static final Logger logger = LoggerFactory.getLogger(MapValidator.class);
    private ValidatorContext validatorContext;
    private Class<?> elementType;
    private MapValidationType validationType;
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
    public void initialize(ValidMap constraintAnnotation) {
        elementType = constraintAnnotation.elementType();
//        constraints = constraintAnnotation.constraints();
//        allViolationMessages = constraintAnnotation.allViolationMessages();
        validationType = constraintAnnotation.mapValidateType();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        initializeMessageAndPath();
        Map map = (Map) value;

        boolean valid = true;

        if (map == null || map.isEmpty()) {
            return true;
        }

        Validator validator = validatorContext.getValidator();

        boolean beanConstrained = validator.getConstraintsForClass(elementType).isBeanConstrained();
        if (validationType.equals(MapValidationType.KEY)) {
            for (Object val : map.entrySet()) {
                Map.Entry entry = (Map.Entry) val;
                Object toBeValidated = entry.getKey();
                Set<ConstraintViolation<?>> violations = new HashSet<>();

                if (beanConstrained) {
                    Set<ConstraintViolation<Object>> constraintViolations = validator.validate(toBeValidated);
                    violations.addAll(constraintViolations);
                }
                if (!violations.isEmpty()) {
                    valid = false;
                    for (ConstraintViolation<?> violation : violations) {
                        context.disableDefaultConstraintViolation();
                        String message = createMessage(violation.getConstraintDescriptor(), toBeValidated);
                        logger.debug("massage: {}", message);
                        logger.debug(violation.getMessage());
                        String path = violation.getPropertyPath().toString();
                        String[] pathPart = path.split("\\.");
                        if (path.contains(".") && !path.contains("<")) {
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
                                    violationBuilder = context.buildConstraintViolationWithTemplate(CollectionValidator.getMessageAndPath().get().get(object));
                                }
                            } else {
                                violationBuilder = context.buildConstraintViolationWithTemplate(violation.getMessage());
                            }
                        } else {
                            violationBuilder = context.buildConstraintViolationWithTemplate(message);
                        }
                        String newPropertyPathStr;
                        boolean isWrapperOrString = ClassUtils.wrapperToPrimitive(entry.getValue().getClass()) != null || entry.getValue() instanceof String;
                        if (isWrapperOrString) {
                            newPropertyPathStr = "[key].<#" + toBeValidated.hashCode() +/* "," + entry.getValue() +*/ ">." + violation.getPropertyPath();
                        } else {
                            newPropertyPathStr = "[key].<#" + toBeValidated.hashCode() + /*",#" + entry.getValue().hashCode() +*/ ">." + violation.getPropertyPath();
                        }
                        if (!(violation.getInvalidValue() instanceof Collection)
                                && !(violation.getInvalidValue() instanceof Map)) {
                            violationBuilder.addNode(newPropertyPathStr + "=" + violation.getInvalidValue()).addConstraintViolation();
                        } else {
                            violationBuilder.addNode(newPropertyPathStr).addConstraintViolation();
                        }
                    }
                }
            }
        } else if (validationType.equals(MapValidationType.VALUE)) {
            for (Object val : map.entrySet()) {
                Map.Entry entry = (Map.Entry) val;
                Object toBeValidated = entry.getValue();
                Set<ConstraintViolation<?>> violations = new HashSet<>();

                if (beanConstrained) {
                    Set<ConstraintViolation<Object>> constraintViolations = validator.validate(toBeValidated);
                    violations.addAll(constraintViolations);
                }
                if (!violations.isEmpty()) {
                    valid = false;
                    for (ConstraintViolation<?> violation : violations) {
                        context.disableDefaultConstraintViolation();
                        String message = createMessage(violation.getConstraintDescriptor(), toBeValidated);
                        logger.debug("massage: {}", message);
                        logger.debug(violation.getMessage());
                        String path = violation.getPropertyPath().toString();
                        String[] pathPart = path.split("\\.");
                        if (path.contains(".") && !path.contains("<")) {
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
                                    violationBuilder = context.buildConstraintViolationWithTemplate(CollectionValidator.getMessageAndPath().get().get(object));
                                }
                            } else {
                                violationBuilder = context.buildConstraintViolationWithTemplate(violation.getMessage());
                            }
                        } else {
                            violationBuilder = context.buildConstraintViolationWithTemplate(message);
                        }
                        String newPropertyPathStr;
                        boolean isWrapperOrString = ClassUtils.wrapperToPrimitive(entry.getKey().getClass()) != null || entry.getKey() instanceof String;
                        if (isWrapperOrString) {
                            newPropertyPathStr = "[value].<" + entry.getKey() + /*",#" + toBeValidated.hashCode() +*/ ">." + violation.getPropertyPath();
                        } else {
                            newPropertyPathStr = "[value].<#" + entry.getKey().hashCode() +/* ",#" + toBeValidated.hashCode() +*/ ">." + violation.getPropertyPath();
                        }
                        if (!(violation.getInvalidValue() instanceof Collection)
                                && !(violation.getInvalidValue() instanceof Map)) {
                            violationBuilder.addNode(newPropertyPathStr + "=" + violation.getInvalidValue()).addConstraintViolation();
                        } else {
                            violationBuilder.addNode(newPropertyPathStr).addConstraintViolation();
                        }
                    }
                }
            }
        }
        return valid;
    }

    private String createMessage(ConstraintDescriptor descriptor, Object value) {
        StringBuilder propertiesStrBuilder = new StringBuilder("-- listing properties --" + "\n");
        Properties properties = new Properties();
        for (Object val : descriptor.getAttributes().entrySet()) {
            Map.Entry entry = (Map.Entry) val;
            properties.put(entry.getKey(), entry.getValue().toString());
            propertiesStrBuilder.append(entry.getKey()).append("=").append(entry.getValue().toString()).append("\n");
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

