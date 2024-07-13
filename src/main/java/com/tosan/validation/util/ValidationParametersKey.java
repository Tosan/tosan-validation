package com.tosan.validation.util;

/**
 * @author Boshra Taheri
 * @see com.tosan.validation.Validation
 * @see ValidationAnnotationInfo
 * @since 7/31/13
 */
public enum ValidationParametersKey {
    /**
     * {@link com.tosan.validation.constraints.Pattern}
     */
    REGEXP,
    /**
     * {@link org.hibernate.validator.constraints.Range}
     * {@link com.tosan.validation.constraints.Max}
     * {@link jakarta.validation.constraints.Max}
     * {@link com.tosan.validation.constraints.Length}
     * {@link org.hibernate.validator.constraints.Length}
     * {@link com.tosan.validation.constraints.Size}
     * {@link jakarta.validation.constraints.Size}
     * {@link com.tosan.validation.constraints.DateDifference}
     * {@link com.tosan.validation.constraints.Difference}
     */
    MIN,
    /**
     * {@link org.hibernate.validator.constraints.Range}
     * {@link com.tosan.validation.constraints.Max}
     * {@link jakarta.validation.constraints.Max}
     * {@link com.tosan.validation.constraints.Length}
     * {@link org.hibernate.validator.constraints.Length}
     * {@link com.tosan.validation.constraints.Size}
     * {@link jakarta.validation.constraints.Size}
     * {@link com.tosan.validation.constraints.DateDifference}
     * {@link com.tosan.validation.constraints.Difference}
     */
    MAX,
    /**
     * {@link com.tosan.validation.constraints.Digits}
     */
    INTEGER,
    /**
     * {@link com.tosan.validation.constraints.Digits}
     */
    FRACTION,
    /**
     * {@link com.tosan.validation.constraints.Expression}
     */
    EXPRESSION,
    /**
     * {@link com.tosan.validation.constraints.Expression}
     */
    IDENTIFIER,
    /**
     * {@link com.tosan.validation.constraints.Compare}
     */
    LESS_FIELD_NAME,
    /**
     * {@link com.tosan.validation.constraints.Compare}
     */
    GREATER_FIELD_NAME,
    /**
     * {@link com.tosan.validation.constraints.Compare}
     * {@link com.tosan.validation.constraints.Min}
     * {@link com.tosan.validation.constraints.Max}
     */
    CHECK_EQUALITY,
    /**
     * {@link com.tosan.validation.constraints.DateDifference}
     * {@link com.tosan.validation.constraints.Difference}
     */
    FROM,
    /**
     * {@link com.tosan.validation.constraints.DateDifference}
     * {@link com.tosan.validation.constraints.Difference}
     */
    TO,
    /**
     * {@link com.tosan.validation.constraints.DateDifference}
     */
    UNIT
}
