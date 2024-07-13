package com.tosan.validation.constraints.impl;

import com.tosan.validation.constraints.Pattern;
import com.tosan.validation.core.BaseValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.PatternSyntaxException;

/**
 * @author Babak Samadi
 */
public class PatternValidator extends BaseValidator implements ConstraintValidator<Pattern, Object> {
    private String regexpKey;
    private Pattern.Flag[] flags;
    private String regexp;

    @Override
    public void initialize(Pattern annotation) {
        regexpKey = annotation.regexpKey();
        regexp = annotation.regexp();
        types = annotation.mapValidateType();
        flags = annotation.flags();
    }

    @Override
    protected boolean doValidate(Object value, ConstraintValidatorContext context) {

        Map<String, String> map = getParameters();

        if ((StringUtils.isBlank(regexpKey) && StringUtils.isBlank(regexp))
                || (StringUtils.isNotBlank(regexpKey) && StringUtils.isNotBlank(regexp))) {
            throw new IllegalArgumentException(
                    "can not use both regexpKey and regexp as parameters on the Pattern annotation or both regexpKey and regexp can not be null, please use only one of theme.");
        }

        int intFlag = 0;
        for (Pattern.Flag flag : flags) {
            intFlag = intFlag | flag.getValue();
        }

        String reg;
        java.util.regex.Pattern pattern;
        if (StringUtils.isNotBlank(regexpKey)) {
            if (StringUtils.isBlank(map.get(regexpKey))) {
                throw new IllegalArgumentException("No value was found for defined key(" + regexpKey + ")");
            }
            reg = map.get(regexpKey);
        } else {
            reg = regexp;
        }

        try {
            pattern = java.util.regex.Pattern.compile(reg, intFlag);
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("Invalid regular expression (" + reg + ")", e);
        }

        if (value == null) {
            return true;
        }
        String val;

        try {
            val = String.valueOf(value);
        } catch (Exception e) {
            throw new IllegalArgumentException("Can not apply regular expression validation on the give parameter(" + value + ")", e);
        }

        Matcher m = pattern.matcher(val);
        return m.matches();
    }
}
