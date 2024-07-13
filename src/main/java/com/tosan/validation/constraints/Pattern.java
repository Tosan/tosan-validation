package com.tosan.validation.constraints;

import com.tosan.validation.constraints.impl.PatternValidator;
import com.tosan.validation.core.MapValidationType;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotated String must match the following regular expression. The regular
 * expression follows the Java regular expression conventions
 * <p/>
 * Accepts String. null elements are considered valid. This Can accept a
 * constant value as a reqexp or a key of the regexp.
 *
 * @author Emmanuel Bernard, Babak Samadi
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {PatternValidator.class})
public @interface Pattern {

    /**
     * @return The regular expression to match.
     */
    String regexp() default "";

    /**
     * @return The regular expression key .
     */
    String regexpKey() default "";

    /**
     * @return Array of <code>Flag</code>s considered when resolving the regular expression.
     */
    Flag[] flags() default {};

    /**
     * @return The error message template.
     */
    String message() default "The value must match the following pattern: {regexpKey}{regexp}";

    /**
     * @return The groups the constraint belongs to.
     */
    Class<?>[] groups() default {};

    /**
     * @return The payload associated to the constraint
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * @return this parameter indicate if type of the attribute is a Map,
     * so either Key of the Map or Value has to be validated.
     */
    MapValidationType[] mapValidateType() default MapValidationType.VALUE;

    /**
     * Possible Regexp flags
     */
    enum Flag {

        /**
         * Enables Unix lines mode
         *
         * @see java.util.regex.Pattern#UNIX_LINES
         */
        UNIX_LINES(java.util.regex.Pattern.UNIX_LINES),

        /**
         * Enables case-insensitive matching
         *
         * @see java.util.regex.Pattern#CASE_INSENSITIVE
         */
        CASE_INSENSITIVE(java.util.regex.Pattern.CASE_INSENSITIVE),

        /**
         * Permits whitespace and comments in pattern
         *
         * @see java.util.regex.Pattern#COMMENTS
         */
        COMMENTS(java.util.regex.Pattern.COMMENTS),

        /**
         * Enables multiline mode
         *
         * @see java.util.regex.Pattern#MULTILINE
         */
        MULTILINE(java.util.regex.Pattern.MULTILINE),

        /**
         * Enables dotall mode
         *
         * @see java.util.regex.Pattern#DOTALL
         */
        DOTALL(java.util.regex.Pattern.DOTALL),

        /**
         * Enables Unicode-aware case folding
         *
         * @see java.util.regex.Pattern#UNICODE_CASE
         */
        UNICODE_CASE(java.util.regex.Pattern.UNICODE_CASE),

        /**
         * Enables canonical equivalence
         *
         * @see java.util.regex.Pattern#CANON_EQ
         */
        CANON_EQ(java.util.regex.Pattern.CANON_EQ);

        // JDK flag value
        private final int value;

        private Flag(int value) {
            this.value = value;
        }

        /**
         * @return flag value as defined in {@link java.util.regex.Pattern}
         */
        public int getValue() {
            return value;
        }
    }

    /**
     * Defines several <code>@Pattern</code> annotations on the same element
     *
     * @author Emmanuel Bernard
     * @see Pattern
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        Pattern[] value();
    }
}
