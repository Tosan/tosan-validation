package com.tosan.validation.constraints.impl;

import com.tosan.validation.constraints.Digits;
import com.tosan.validation.core.BaseValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

/**
 * Validates that the given attribute being validates matches the pattern defined in the constraint.
 *
 * @author Babak Samadi
 */
public class DigitsValidator extends BaseValidator implements ConstraintValidator<Digits, Object> {
	private int value;
	private int fraction;
	private String valueKey;
	private String fractionKey;
	private boolean ignoreTrailingZerosAfterMaxFractionLength;


	@Override
	public void initialize(Digits annotation) {
		value = annotation.integer();
		fraction = annotation.fraction();
		valueKey = annotation.integerKey();
		fractionKey = annotation.fractionKey();
		types = annotation.mapValidateType();
		ignoreTrailingZerosAfterMaxFractionLength = annotation.ignoreTrailingZerosAfterMaxFractionLength();
	}

	@Override
	protected boolean doValidate(Object value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}

		if ((StringUtils.isBlank(valueKey) && this.value == 0) || (StringUtils.isNotBlank(valueKey) && this.value != 0)) {
			throw new IllegalArgumentException(
					"can not use both valueKey and value as parameters on the Digits annotation or both valueKey and value can not be null, please use only one of theme.");
		}

		if ((StringUtils.isBlank(fractionKey) && this.fraction == Integer.MAX_VALUE)
				|| (StringUtils.isNotBlank(fractionKey) && this.fraction != Integer.MAX_VALUE)) {
			throw new IllegalArgumentException(
					"can not use both fractionKey and fraction as parameters on the Digits annotation or both fractionKey and fraction can not be null, please use only one of theme.");
		}

		Map<String, String> parameters = getParameters();

		int maxIntegerLength;
		int maxFractionLength;

		if (StringUtils.isNotBlank(valueKey)) {
			if (StringUtils.isBlank(parameters.get(valueKey))) {
				throw new IllegalArgumentException("No value was found for defined key(" + valueKey + ")");
			}
			try {
				maxIntegerLength = Integer.parseInt(parameters.get(valueKey));
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("value of the defined key(" + valueKey + ") is not correct");
			}
		} else {
			maxIntegerLength = this.value;
		}

		if (StringUtils.isNotBlank(fractionKey)) {
			if (StringUtils.isBlank(parameters.get(fractionKey))) {
				throw new IllegalArgumentException("No value was found for defined key(" + fractionKey + ")");
			}
			try {
				maxFractionLength = Integer.parseInt(parameters.get(fractionKey));
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("value of the defined key(" + fractionKey + ") is not correct");
			}
		} else {
			maxFractionLength = this.fraction;
		}

		validateParameters(maxIntegerLength, maxFractionLength);

		BigDecimal bigNum;
		if (value instanceof BigDecimal) {
			bigNum = (BigDecimal) value;
		} else {
			try {
				bigNum = new BigDecimal(value.toString()).stripTrailingZeros();
			} catch (NumberFormatException nfe) {
				throw new IllegalArgumentException("Can not do Digits validation on the value(" + value + ")", nfe);
			}
		}

		int integerPartLength = bigNum.precision() - bigNum.scale();
		int fractionPartLength = getFractionPartLength(bigNum , maxFractionLength, this.ignoreTrailingZerosAfterMaxFractionLength);

		return (maxIntegerLength >= integerPartLength && maxFractionLength >= fractionPartLength);
	}

	private void validateParameters(int maxIntegerLength, int maxFractionLength) {
		if (maxIntegerLength < 0) {
			throw new IllegalArgumentException("The length of the integer part cannot be negative.");
		}
		if (maxFractionLength < 0) {
			throw new IllegalArgumentException("The length of the fraction part cannot be negative.");
		}
	}

	private int getFractionPartLength(BigDecimal value, int maxFractionLength, boolean ignoreTrailingZerosAfterMaxFractionLength) {
		BigDecimal currentValue = value;
		if (ignoreTrailingZerosAfterMaxFractionLength && currentValue.scale() > maxFractionLength) {
			BigDecimal RoundedValue = currentValue.setScale(maxFractionLength, RoundingMode.DOWN);
			if (currentValue.subtract(RoundedValue).compareTo(BigDecimal.ZERO) == 0) {
				currentValue = RoundedValue;
			}
		}
		return Math.max(currentValue.scale(), 0);
	}
}
