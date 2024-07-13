package com.tosan.validation.constraints.impl;

import com.tosan.validation.constraints.Email;
import com.tosan.validation.core.BaseValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;

/**
 * @author Boshra Taheri
 * @since 12/20/2014
 */
public class EmailValidator extends BaseValidator implements ConstraintValidator<Email, Object> {

    private static final String ATOM = "[a-z0-9!#$%&'*+/=?^_`{|}~-]";
    private static final String DOMAIN = "(" + ATOM + "+(\\." + ATOM + "+)*";
    private static final String IP_DOMAIN = "\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\]";

    private final java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(
            "^" + ATOM + "+(\\." + ATOM + "+)*@"
                    + DOMAIN
                    + "|"
                    + IP_DOMAIN
                    + ")$",
            java.util.regex.Pattern.CASE_INSENSITIVE
    );

    @Override
    public void initialize(Email annotation) {
        types = annotation.mapValidateType();
    }

    @Override
    protected boolean doValidate(Object value, ConstraintValidatorContext context) {
        String givenEmail = (String) value;
        if (givenEmail == null || givenEmail.length() == 0) {
            return true;
        }
        Matcher m = pattern.matcher(givenEmail);
        return m.matches();
    }
}
