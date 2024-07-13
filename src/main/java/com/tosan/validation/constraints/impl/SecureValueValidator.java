package com.tosan.validation.constraints.impl;

import com.tosan.validation.constraints.SecureValue;
import com.tosan.validation.core.BaseValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author Akhbari
 * @since 16/10/2016
 */
public class SecureValueValidator extends BaseValidator implements ConstraintValidator<SecureValue, Object> {
    private String excludeValuesKey;

    @Override
    public void initialize(SecureValue constraintAnnotation) {
        excludeValuesKey = constraintAnnotation.excludeValuesKey();
        types = constraintAnnotation.mapValidateType();
    }

    @Override
    protected boolean doValidate(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        Map<String, String> parameters = getParameters();
        if (StringUtils.isNotBlank(excludeValuesKey)) {
            String excludeValues = parameters.get(excludeValuesKey);
            if (StringUtils.isNotBlank(excludeValues)) {
                String stringValue = value.toString();
                String[] splitValues = stringValue.trim().split("\\s");
                String[] blackListValues = excludeValues.split("-");
                for (String excludeValue : blackListValues) {
                    if (excludeValue.trim().isEmpty()) {
                        break;
                    }
                    for (String splitValue : splitValues) {
                        if (splitValue.equalsIgnoreCase(excludeValue)) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        return true;
    }
}

