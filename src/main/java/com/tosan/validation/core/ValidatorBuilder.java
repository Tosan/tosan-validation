package com.tosan.validation.core;

import jakarta.validation.Validation;
import jakarta.validation.ValidatorContext;
import jakarta.validation.ValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author Samadi
 */
public class ValidatorBuilder {
    private static final String DEFAULT_PROP_FILE_NAME = "validation-key-value.properties";
    private static final Logger logger = LoggerFactory.getLogger(ValidatorBuilder.class);
    private Map<String, String> parameter = new HashMap<>();

    /**
     * No argument constructor will search "validation-key-value.properties" in
     * CLASSPATH to create a map for validation parameters.
     */
    public ValidatorBuilder() {
        this(DEFAULT_PROP_FILE_NAME);
    }

    /**
     * @param paramsFileName paramsFileName is a name of a property file which contains
     *                       keys-values for validation. this file should be in CLASSPATH.
     */
    public ValidatorBuilder(String paramsFileName) {
        logger.debug("try to find params file (" + paramsFileName + ") in the classpath");

        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream(paramsFileName));
            Set<Object> propKeys = properties.keySet();

            for (Object key : propKeys) {
                parameter.put((String) key, (String) properties.get(key));
            }

            logger.debug("Validation params file(" + paramsFileName + ") was loaded");

        } catch (NullPointerException e) {
            logger.warn("Validator params file( " + paramsFileName + " ), not found in classpath!");
        } catch (IOException e) {
            logger.warn("I/O problem when loading validator params file(" + paramsFileName + ")", e);
        }
    }

    public ValidatorBuilder(Map<String, String> externalMap) {
        parameter = new HashMap<>();
        if (externalMap != null) {
            Set<String> mapKeys = externalMap.keySet();

            for (String key : mapKeys) {
                parameter.put(key, externalMap.get(key));
            }
        }
    }

    public jakarta.validation.Validator getValidator() {
        jakarta.validation.Configuration<?> configuration = Validation.byDefaultProvider().configure();
        configuration.messageInterpolator(new CustomMessageInterpolator(parameter));
        ValidatorFactory validatorFactory = configuration.constraintValidatorFactory(new MapAwareValidatorFactory())
                .buildValidatorFactory();
        ((MapAwareValidatorFactory) validatorFactory.getConstraintValidatorFactory()).setParameters(parameter);
        ValidatorContext validatorContext = validatorFactory.usingContext();
        ((MapAwareValidatorFactory) validatorFactory.getConstraintValidatorFactory()).setValidatorContext(validatorContext);

        validatorContext.constraintValidatorFactory((validatorFactory.getConstraintValidatorFactory()));

        return validatorFactory.getValidator();
    }
}
