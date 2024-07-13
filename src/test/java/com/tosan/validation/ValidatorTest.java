package com.tosan.validation;

import com.tosan.validation.core.ValidatorBuilder;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Mostafa Abdollahi
 * @since 3/17/13
 */
public class ValidatorTest {
    private static final Logger logger = LoggerFactory.getLogger(Validator.class);
    private static final jakarta.validation.Validator validator;

    static {
        ValidatorBuilder validatorBuilder = new ValidatorBuilder();
        validator = validatorBuilder.getValidator();
    }

    public static void validate(Object[] parameters, Class<?>... groups) throws RuntimeException {
        StringBuilder message = new StringBuilder();
        Object param = parameters[0];
        if (param != null) {
            logger.info("Validating request object: {}", param.getClass().getSimpleName());
            Set<ConstraintViolation<Object>> constraintViolationSet = new HashSet<>();
            if (param instanceof Collection<?>) {
                for (Object object : (Collection<?>) param) {
                    constraintViolationSet.addAll(validator.validate(object, groups));
                }
            } else {
                constraintViolationSet = validator.validate(param, groups);
            }
            for (ConstraintViolation<Object> invalidValue : constraintViolationSet) {
                message.append(" ").append(invalidValue.getPropertyPath().toString()).append(" : ")
                        .append(invalidValue.getMessage()).append(" , ");
            }
        }
        if (!message.isEmpty()) {//has error
            logger.info("Validation Exception: {}", message);
            throw new RuntimeException(message.toString());
        }
    }
}
