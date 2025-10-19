package com.tosan.validation.constraints.impl;

import com.tosan.validation.constraints.ValidBase64;
import com.tosan.validation.core.BaseValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Base64;
import java.util.Map;

/**
 * @author Amirhossein Zamanzade
 * @since 10/19/25
 */
public class Base64Validator extends BaseValidator implements ConstraintValidator<ValidBase64, Object> {

    private Long maxBytesSize;

    @Override
    public void initialize(ValidBase64 constraintAnnotation) {
        Map<String, String> parameters = getParameters();
        if (constraintAnnotation.maxBytesSize() > 0) {
            this.maxBytesSize = constraintAnnotation.maxBytesSize();
        } else {
            if (!parameters.containsKey(constraintAnnotation.maxBytesSizeKey())) {
                throw new IllegalArgumentException(
                        "No configuration found for the provided maxBytesSizeKey: " + constraintAnnotation.maxBytesSizeKey());
            }
            this.maxBytesSize = Long.valueOf(parameters.get(constraintAnnotation.maxBytesSizeKey()));
        }
    }

    @Override
    protected boolean doValidate(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        String base64Image = value.toString();
        if (base64Image.isBlank()) {
            return true;
        }
        // check for memory exhaustion attacks
        // Base64 encoding expands data by ~33%, so decoded size ≈ (base64 length * 3 / 4)
        int estimatedDecodedSize = (base64Image.length() * 3) / 4;
        if (estimatedDecodedSize > maxBytesSize) {
            return false;
        }
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(base64Image);
            return decodedBytes.length <= maxBytesSize;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
