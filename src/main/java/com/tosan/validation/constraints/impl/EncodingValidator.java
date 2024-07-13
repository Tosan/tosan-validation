package com.tosan.validation.constraints.impl;

import com.tosan.validation.constraints.Encoding;
import com.tosan.validation.core.BaseValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author Maryam Madani
 * @since 24/01/2017
 */
public class EncodingValidator extends BaseValidator implements ConstraintValidator<Encoding, Object> {
    private String encoding;

    @Override
    public void initialize(Encoding annotation) {
        this.encoding = annotation.encoding();
    }

    @Override
    protected boolean doValidate(Object value, ConstraintValidatorContext context) {
        String text = (String) value;
        Map<String, String> parameters = getParameters();

        return StringUtils.isBlank(text) || Charset.forName(parameters.get(encoding)).newEncoder().canEncode(text);
    }
}
