package com.tosan.validation.core;

import jakarta.validation.MessageInterpolator;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;

import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * در این کلاس فرض می‌شود که annotationها پیغامی با یکی از فرمت‌های زیر دارند:
 * - "The length of the parameter must be between {minKey}{min} and {maxKey}{max}"
 * - "Length must be between {min} and {max}"
 * در حالت اول minKey و maxKey نام propertyهایی هستند که مقدار min و max را تعیین می‌کنند.
 * فرض می‌کنیم در صورتی که هر یک از این دو مقدار تعیین نشوند، مقدار min یا max آن annotation در پیغام قرار می‌گیرد.
 * در این صورت لازم است attributeهایی نظیر min و max برای {@link com.tosan.validation.constraints.Length} مقدار پیش‌فرض معنادار داشته باشند.
 * در فرمت دوم attributeهای min و max مقادیر معنادار دارند و مقدار آن‌ها در پیغام قرار می‌گیرد.
 *
 * @author Boshra Taheri
 * @since 8/12/13
 */
public class CustomMessageInterpolator implements MessageInterpolator {
    /**
     * Regular expression used to do message interpolation.
     */
    private static final Pattern MESSAGE_PARAMETER_PATTERN = Pattern.compile("(\\{[^\\}]+?\\})");
    private static final Pattern DOUBLE_PARAMETER_PATTERN = Pattern.compile("(\\{[^\\}]+?\\})(\\{[^\\}]+?\\})");
    private Map<String, String> parameters;
    private final MessageInterpolator hibernateMessageInterpolator = new ResourceBundleMessageInterpolator();

    public CustomMessageInterpolator(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String interpolate(String messageTemplate, Context context) {
        return interpolateMessage(messageTemplate, context.getConstraintDescriptor().getAttributes(), context);
    }

    @Override
    public String interpolate(String messageTemplate, Context context, Locale locale) {
        return interpolateMessage(messageTemplate, context.getConstraintDescriptor().getAttributes(), context);
    }

    private String interpolateMessage(String message, Map<String, Object> annotationParameters, Context context) {
        String resolvedMessage = null;

        // resolve annotation attributes (step 4)
        resolvedMessage = replaceAnnotationAttributes(message, annotationParameters);


        // search the default bundle non-recursive (step2)
        resolvedMessage = replaceVariables(resolvedMessage, false);

        if (resolvedMessage.contains("{")) {
            resolvedMessage = hibernateMessageInterpolator.interpolate(resolvedMessage, context);
        }
        // last but not least we have to take care of escaped literals
        resolvedMessage = resolvedMessage.replace("\\{", "{");
        resolvedMessage = resolvedMessage.replace("\\}", "}");
        resolvedMessage = resolvedMessage.replace("\\\\", "\\");
        return resolvedMessage;
    }

    private boolean hasReplacementTakenPlace(String origMessage, String newMessage) {
        return !origMessage.equals(newMessage);
    }

    private String replaceVariables(String message, boolean recurse) {
        Matcher matcher = MESSAGE_PARAMETER_PATTERN.matcher(message);
        StringBuilder sb = new StringBuilder();
        String resolvedParameterValue;
        while (matcher.find()) {
            String parameter = matcher.group(1);
            resolvedParameterValue = resolveParameter(parameter, recurse);

            if (resolvedParameterValue != null) {
                matcher.appendReplacement(sb, escapeMetaCharacters(resolvedParameterValue));
            }
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private String replaceAnnotationAttributes(String message, Map<String, Object> annotationParameters) {
        message = resolveDoubleParameters(message, annotationParameters);
        Matcher matcher = MESSAGE_PARAMETER_PATTERN.matcher(message);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            String resolvedParameterValue;
            String parameter = matcher.group(1);
            Object variable = annotationParameters.get(removeCurlyBrace(parameter));
            if (variable != null && variable.toString().length() > 0) {
                if (parameters.get(variable.toString()) != null) {
                    resolvedParameterValue = "{" + escapeMetaCharacters(variable.toString()) + "}";
                } else {
                    resolvedParameterValue = escapeMetaCharacters(variable.toString());
                }
            } else {
                resolvedParameterValue = parameter;
            }
            matcher.appendReplacement(sb, resolvedParameterValue);
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private String resolveDoubleParameters(String message, Map<String, Object> annotationParameters) {
        Matcher doubleParameterMatcher = DOUBLE_PARAMETER_PATTERN.matcher(message);
        StringBuffer sb = new StringBuffer();
        while (doubleParameterMatcher.find()) {
            String parameter = doubleParameterMatcher.group(1) + doubleParameterMatcher.group(2);
            Matcher matcher = MESSAGE_PARAMETER_PATTERN.matcher(parameter);
            matcher.find();
            String param = matcher.group(1);
            Object variable = annotationParameters.get(removeCurlyBrace(param));
            if (variable != null && variable.toString().length() > 0) {
                if (parameters.get(variable.toString()) == null) {
                    matcher.find();
                    param = matcher.group(1);
                }
            } else {
                matcher.find();
                param = matcher.group(1);
            }
            doubleParameterMatcher.appendReplacement(sb, param);
        }
        doubleParameterMatcher.appendTail(sb);
        return sb.toString();
    }

    private String resolveParameter(String parameterName, boolean recurse) {
        String parameterValue;
        if (!parameters.isEmpty()) {
            parameterValue = parameters.get(removeCurlyBrace(parameterName));
            if (recurse) {
                parameterValue = replaceVariables(parameterValue, recurse);
            }
        } else {
            parameterValue = parameterName;
        }
        return parameterValue;
    }

    private String removeCurlyBrace(String parameter) {
        return parameter.substring(1, parameter.length() - 1);
    }

    /**
     * @param s The string in which to replace the meta characters '$' and '\'.
     * @return A string where meta characters relevant for {@link Matcher#appendReplacement} are escaped.
     */
    private String escapeMetaCharacters(String s) {
        String escapedString = s.replace("\\", "\\\\");
        escapedString = escapedString.replace("$", "\\$");
        return escapedString;
    }
}
