package com.tosan.validation.constraints.impl;

import com.tosan.validation.constraints.Size;
import com.tosan.validation.core.BaseValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * Check that the length of a given collection is between min and max
 *
 * @author Hardy Ferentschik, Babak Samadi
 */
public class SizeValidator extends BaseValidator implements ConstraintValidator<Size, Object> {
    private int min;
    private int max;

    public void initialize(Size annotation) {
        Map<String, String> map = getParameters();

        int minConstant = annotation.min();
        int maxConstant = annotation.max();

        String minKey = annotation.minSizeKey();
        String maxKey = annotation.maxSizeKey();
        innerCollectionCheck = annotation.innerCollectionCheck();
        isSizeValidation = true;

        if ((StringUtils.isNotBlank(maxKey) && maxConstant != Integer.MAX_VALUE)
                || (StringUtils.isNotBlank(minKey) && minConstant != 0)) {
            throw new IllegalArgumentException(
                    "The min parameter cannot be negative, please use one type of the parameters.");
        }

        if (StringUtils.isNotBlank(maxKey)) {
            if (StringUtils.isBlank(map.get(maxKey))) {
                throw new IllegalArgumentException("Can not find key(" + maxKey + ") in the given Map");
            } else {
                max = Integer.parseInt(map.get(maxKey));
            }

        } else {
            max = maxConstant;
        }

        if (StringUtils.isNotBlank(minKey)) {
            if (StringUtils.isBlank(map.get(minKey))) {
                throw new IllegalArgumentException("Can not find key(" + minKey + ") in the given Map");
            } else {
                min = Integer.parseInt(map.get(minKey));
            }

        } else {
            min = minConstant;
        }

        validateParameters();
    }

    private void validateParameters() {
        if (min < 0) {
            throw new IllegalArgumentException("The min parameter cannot be negative.");
        }
        if (max < 0) {
            throw new IllegalArgumentException("The max parameter cannot be negative.");
        }
        if (max < min) {
            throw new IllegalArgumentException("The length cannot be negative.");
        }
    }

    /**
     * Checks the number of entries in an array.
     *
     * @param value   The param to validate.
     * @param context context in which the constraint is evaluated.
     * @return Returns true if the array is null or the number of entries in
     * array is between the specified min and max values (inclusive),
     * false otherwise.
     */
    @Override
    public boolean doValidate(Object value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }

        int length = 0;
        if (value instanceof String) {
            length = ((String) value).length();
        } else if (value instanceof Map) {
            length = ((Map) value).size();
        } else if (value instanceof Collection) {
            length = ((Collection) value).size();
        } else {
            length = Array.getLength(value);
        }
        return length >= min && length <= max;
    }
}
