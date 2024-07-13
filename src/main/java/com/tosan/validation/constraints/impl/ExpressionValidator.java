package com.tosan.validation.constraints.impl;

import com.tosan.validation.constraints.Expression;
import com.tosan.validation.core.MapAwareValidator;
import com.tosan.validation.util.Context;
import com.tosan.validation.util.ExpressionUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * @author shamsolebad, Samadi
 * @since Sep 10, 2009
 */
public class ExpressionValidator extends MapAwareValidator<Object>
        implements ConstraintValidator<Expression, Object>, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String expression;
    private String key;

    public void initialize(Expression expression) {
        this.expression = expression.value();
        this.key = expression.keyValue();
    }

    @Override
    public boolean isValidInMap(Object o, ConstraintValidatorContext context) {
        return o == null || evaluate(o);
    }

    /**
     * Can not use both keyValue and value as parameters in the Expression annotation
     * or both keyValue and value can not be null otherwise an IllegalArgumentException will be thrown.
     */
    protected boolean evaluate(Object value) {

        if ((StringUtils.isBlank(key) && StringUtils.isBlank(expression))
                || (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(expression))) {
            throw new IllegalArgumentException(
                    "both of keyValue and value parameters of the Expression annotation can not be null or filled, please use one of theme.");
        }

        Context context = new Context();
        context.put("this", value);
        Map<String, String> map = getParameters();

        String val;
        if (StringUtils.isNotBlank(key)) {
            if (StringUtils.isBlank(map.get(key))) {
                throw new IllegalArgumentException("No value was found for defined key(" + key + ")");
            }
            val = map.get(key);
        } else
            val = expression;

        return ExpressionUtil.getValue(context, format(val), Boolean.class);
    }

    protected String format(final String exp) {
        if (!exp.contains("#{")) {
            return "#{" + exp + "}";
        } else {
            return exp;
        }
    }
}
