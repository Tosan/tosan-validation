package com.tosan.validation.constraints.impl;

import com.tosan.validation.constraints.Min;
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
public class MinValidator extends BaseValidator implements ConstraintValidator<Min, Object> {
    private String key;
    private Long constantValue;
    private boolean checkEquality;

    public void initialize(Min annotation) {
        this.key = annotation.keyValue();
        this.constantValue = annotation.value();
        types = annotation.mapValidateType();
        checkEquality = annotation.checkEquality();
    }

    /**
     * Can not use both keyValue and value as parameters in the Min annotation
     * or both keyValue and value can not be null otherwise an
     * IllegalArgumentException will be thrown.
     */
    @Override
    public boolean doValidate(Object value, ConstraintValidatorContext context) {

        if ((StringUtils.isBlank(key) && constantValue == -1L) || (StringUtils.isNotBlank(key) && constantValue != -1L)) {
            throw new IllegalArgumentException(
                    "can not use both keyValue and value as parameters on the Min annotation or both keyValue and value can not be null, please use only one of theme.");
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
            int compareResult = ((BigDecimal) value).compareTo(BigDecimal.valueOf(validValue));
            return checkEquality ? compareResult >= 0 : compareResult > 0;
        } else if (value instanceof BigInteger) {
            int compareResult = ((BigInteger) value).compareTo(BigInteger.valueOf(validValue));
            return checkEquality ? compareResult >= 0 : compareResult > 0;
        } else if (value instanceof Long) {
            long longValue = (Long) value;
            return checkEquality ? longValue >= validValue : longValue > validValue;
        } else {
            try {
                int compareResult = new BigDecimal(String.valueOf(value)).compareTo(BigDecimal.valueOf(validValue));
                return checkEquality ? compareResult >= 0 : compareResult > 0;
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException("Can not do Min validation on the value(" + value + ")");
            }
        }
    }
}