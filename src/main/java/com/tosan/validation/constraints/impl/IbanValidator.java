package com.tosan.validation.constraints.impl;

import com.tosan.validation.constraints.Iban;
import com.tosan.validation.core.BaseValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigInteger;

/**
 * @author Habib Motallebpour
 * @since 16/07/2016
 */
public class IbanValidator extends BaseValidator<String> implements ConstraintValidator<Iban, String> {
    private boolean isTosanBank;

    @Override
    public void initialize(Iban annotation) {
        this.isTosanBank = annotation.isTosanBank();
        types = annotation.mapValidateType();
    }

    @Override
    protected boolean doValidate(Object value, ConstraintValidatorContext context) {
        String iban;

        if (value == null) {
            return true;
        }
        if (value instanceof String) {
            iban = (String) value;
        } else {
            return false;
        }
        //size
        if (iban.length() != 26) {
            return false;
        }
        //IR
        if (!iban.startsWith("IR")) {
            return false;
        }
        //digit
        for (int i = 2; i < (iban.length()); i++) {
            if (!Character.isDigit(iban.charAt(i))) {
                return false;
            }
        }
        //System Type (loan 2 deposit 0)
        int sysType = Integer.parseInt(iban.substring(7, 8));
        if (isTosanBank) {
            if (sysType != 0 && sysType != 2) {
                return false;
            }
        } else if (sysType > 3) {
            return false;
        }
        String strCd = iban.substring(2, 4);
        String strBban = iban.substring(4);
        String strExp = strBban + "1827" + strCd;
        BigInteger bigExp = new BigInteger(strExp);

        return bigExp.remainder(new BigInteger("97")).equals(new BigInteger("1"));
    }
}