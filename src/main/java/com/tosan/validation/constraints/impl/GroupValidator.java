package com.tosan.validation.constraints.impl;

import com.tosan.validation.constraints.Group;
import com.tosan.validation.core.BaseValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author Samadi
 */
public class GroupValidator extends BaseValidator implements ConstraintValidator<Group, Object> {
    private double[] pointLiterals;
    private long[] integerLiterals;
    private boolean isInclude;

    @Override
    public void initialize(Group annotation) {
        this.pointLiterals = annotation.pointLiterals();
        this.integerLiterals = annotation.integerLiterals();
        this.isInclude = annotation.isInclude();
        types = annotation.mapValidateType();
    }

    @Override
    protected boolean doValidate(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        if (pointLiterals.length == 0 && integerLiterals.length == 0) {
            throw new IllegalArgumentException("Can not use Group annotation without any group definition");
        }

        if (pointLiterals.length > 0 && integerLiterals.length > 0) {
            throw new IllegalArgumentException("Can not use Group annotation with both type of sets, please use only one of theme");
        }

        for (double setVal : pointLiterals) {
            try {
                if (setVal == Double.parseDouble(value.toString())) {
                    return isInclude;
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                        "type of the parameter or filed is not match to given pointLiterals, it should be a Number");
            }
        }

        for (long setVal : integerLiterals) {
            try {
                if (setVal == Long.parseLong(value.toString())) {
                    return isInclude;
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                        "type of the parameter or filed is not match to given integerLiterals, it should be a Number");
            }
        }
        return !isInclude;
    }
}
