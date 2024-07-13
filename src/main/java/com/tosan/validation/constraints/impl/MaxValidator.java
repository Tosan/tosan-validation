package com.tosan.validation.constraints.impl;

import com.tosan.validation.constraints.Max;
import com.tosan.validation.core.BaseValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

/**
 * @author Samadi
 */
public class MaxValidator extends BaseValidator implements ConstraintValidator<Max, Object> {
    private String key;
    private Long constantValue;
    private boolean checkEquality;

    public void initialize(Max annotation) {
        this.key = annotation.keyValue();
        this.constantValue = annotation.value();
        types = annotation.mapValidateType();
        checkEquality = annotation.checkEquality();
    }

    /**
     * Can not use both keyValue and value as parameters in the Max annotation
     * or both keyValue and value can not be null otherwise an
     * IllegalArgumentException will be thrown.
     */
    @Override
    public boolean doValidate(Object value, ConstraintValidatorContext context) {

        if ((StringUtils.isBlank(key) && constantValue == -1L) || (StringUtils.isNotBlank(key) && constantValue != -1L)) {
            throw new IllegalArgumentException(
                    "can not use both keyValue and value as parameters on the Max annotation or both keyValue and value can not be null, please use only one of theme.");
        }

        Map<String, String> map = getParameters();

        Long validValue;
        if (StringUtils.isNotBlank(key)) {
            if (StringUtils.isBlank(map.get(key))) {
                throw new IllegalArgumentException("No value was found for defined key(" + key + ")");
            }
            try {
                validValue = Long.valueOf(map.get(key));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("value of the defined key(" + key + ") is not correct");
            }
        } else {
            validValue = constantValue;
        }

        if (value == null) {
            return true;
        }

        if (value instanceof BigDecimal) {
            return ((BigDecimal) value).compareTo(BigDecimal.valueOf(validValue)) != 1;
        } else if (value instanceof BigInteger) {
            return ((BigInteger) value).compareTo(BigInteger.valueOf(validValue)) != 1;
        } else if (value instanceof Long) {
            long longValue = (Long) value;
            return checkEquality ? longValue <= validValue : longValue < validValue;
        } else {
            try {
                return new BigDecimal(String.valueOf(value)).compareTo(BigDecimal.valueOf(validValue)) != 1;
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException("Can not do Max validation on the value(" + value + ")");
            }
        }
    }
}