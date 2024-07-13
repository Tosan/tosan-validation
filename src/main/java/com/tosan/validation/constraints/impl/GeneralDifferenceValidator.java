package com.tosan.validation.constraints.impl;

import com.tosan.tools.jalali.JalaliDate;
import com.tosan.validation.constraints.Difference;
import com.tosan.validation.util.CalendarUtils;
import jakarta.validation.ConstraintValidator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * @author Boshra Taheri
 * @since 11/24/13
 */
public class GeneralDifferenceValidator extends DifferenceValidator implements ConstraintValidator<Difference, Object> {
    private static final Logger logger = LoggerFactory.getLogger(GeneralDifferenceValidator.class);
    private String fromFieldName;
    private String toFieldName;
    private String maxDifferenceKey;
    private long maxDifference;
    private String minDifferenceKey;
    private long minDifference;

    @Override
    public void initialize(Difference constraintAnnotation) {
        this.fromFieldName = constraintAnnotation.fromFieldName();
        this.toFieldName = constraintAnnotation.toFieldName();
        this.maxDifference = constraintAnnotation.maxDifference();
        this.maxDifferenceKey = constraintAnnotation.maxDifferenceKey();
        this.minDifference = constraintAnnotation.minDifference();
        this.minDifferenceKey = constraintAnnotation.minDifferenceKey();
    }

    @Override
    public long getMaxDifference() {
        if (StringUtils.isNotBlank(maxDifferenceKey)) {
            if (StringUtils.isBlank(getParameters().get(maxDifferenceKey))) {
                throw new IllegalArgumentException("No value was found for defined key(" + maxDifferenceKey + ")");
            }
            try {
                maxDifference = Long.parseLong(getParameters().get(maxDifferenceKey));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("value of the defined key(" + maxDifferenceKey + ") is not correct");
            }
        }
        return maxDifference;
    }

    @Override
    public long getMinDifference() {
        if (StringUtils.isNotBlank(minDifferenceKey)) {
            if (StringUtils.isBlank(getParameters().get(minDifferenceKey))) {
                throw new IllegalArgumentException("No value was found for defined key(" + minDifferenceKey + ")");
            }
            try {
                minDifference = Long.parseLong(getParameters().get(minDifferenceKey));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("value of the defined key(" + minDifferenceKey + ") is not correct");
            }
        }
        return minDifference;
    }

    @Override
    protected long getActualDifference(Object value) {
        long actualDifference;
        try {
            Field fromField = value.getClass().getDeclaredField(fromFieldName);
            fromField.setAccessible(true);
            Field toField = value.getClass().getDeclaredField(toFieldName);
            toField.setAccessible(true);
            Object fromObj = fromField.get(value);
            Object toObj = toField.get(value);
            if (fromObj instanceof Date && toObj instanceof Date) {
                Date from = (Date) fromField.get(value);
                Date to = (Date) toField.get(value);
                actualDifference = to.getTime() - from.getTime();
            } else if (fromObj instanceof JalaliDate && toObj instanceof JalaliDate) {
                JalaliDate fromJalaliDate = (JalaliDate) fromField.get(value);
                JalaliDate toJalaliDate = (JalaliDate) toField.get(value);
                Date from = CalendarUtils.shamsiToMiladiDate(fromJalaliDate);
                Date to = CalendarUtils.shamsiToMiladiDate(toJalaliDate);
                actualDifference = to.getTime() - from.getTime();
            } else if (fromObj instanceof Number && toObj instanceof Number) {
                long from = Long.parseLong(fromObj.toString());
                long to = Long.parseLong(toObj.toString());
                actualDifference = to - from;
            } else {
                throw new IllegalArgumentException("DateDifferenceValidator only accepts Date, JalaliDate and numeric fields.");
            }
        } catch (NoSuchFieldException e) {
            logger.error("Field with name " + fromFieldName + " or " + toFieldName + " does not exist.");
            throw new IllegalArgumentException(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }

        return actualDifference;
    }
}
