package com.tosan.validation.constraints.impl;

import com.tosan.validation.constraints.Compare;
import com.tosan.validation.util.Context;
import com.tosan.validation.util.ExpressionUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.io.Serializable;

/**
 * @author Boshra Taheri
 * @since 11/23/13
 */
public class ComparisonValidator implements ConstraintValidator<Compare, Object>, Serializable {

    private String lessFieldName;
    private String greaterFieldName;
    private String resultingExpression;

    @Override
    public void initialize(Compare constraintAnnotation) {
        this.lessFieldName = constraintAnnotation.lessFieldName();
        this.greaterFieldName = constraintAnnotation.greaterFieldName();
        this.resultingExpression = generateExpression(constraintAnnotation.checkEquality());
    }

    private String generateExpression(boolean checkEquality) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("this.").append(lessFieldName).append(" eq null or this.")
                .append(greaterFieldName).append(" eq null or this.")
                .append(lessFieldName);
        if (checkEquality) {
            stringBuilder.append(" le ");
        } else {
            stringBuilder.append(" lt ");
        }
        stringBuilder.append("this.");
        stringBuilder.append(greaterFieldName);
        return stringBuilder.toString();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return evaluate(value);
    }

    private boolean evaluate(Object value) {
        Context elContext = new Context();
        elContext.put("this", value);
        return ExpressionUtil.getValue(elContext, format(resultingExpression), boolean.class);
    }

    protected String format(final String exp) {
        if (!exp.contains("#{")) {
            return "#{" + exp + "}";
        } else {
            return exp;
        }
    }
}
