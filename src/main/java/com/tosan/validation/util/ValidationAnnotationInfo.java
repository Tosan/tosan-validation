package com.tosan.validation.util;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * @author Boshra Taheri
 * @since 8/21/13
 */
public class ValidationAnnotationInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 2423148094614127601L;
    private ValidationAnnotation annotation;
    private Map<ValidationParametersKey, Object> validParameters;
    private String violationMessage;

    public ValidationAnnotationInfo(ValidationAnnotation annotation) {
        this.annotation = annotation;
    }

    public ValidationAnnotationInfo(ValidationAnnotation annotation, String violationMessage) {
        this.annotation = annotation;
        this.violationMessage = violationMessage;
    }

    public ValidationAnnotationInfo(ValidationAnnotation annotation, Map<ValidationParametersKey, Object> validParameters, String violationMessage) {
        this.annotation = annotation;
        this.validParameters = validParameters;
        this.violationMessage = violationMessage;
    }

    public ValidationAnnotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(ValidationAnnotation annotation) {
        this.annotation = annotation;
    }

    public Map<ValidationParametersKey, Object> getValidParameters() {
        return validParameters;
    }

    public void setValidParameters(Map<ValidationParametersKey, Object> validParameters) {
        this.validParameters = validParameters;
    }

    public String getViolationMessage() {
        return violationMessage;
    }

    public void setViolationMessage(String violationMessage) {
        this.violationMessage = violationMessage;
    }

    @Override
    public String toString() {
        return "" + violationMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValidationAnnotationInfo that = (ValidationAnnotationInfo) o;

        if (annotation != null ? !annotation.equals(that.annotation) : that.annotation != null) return false;
        if (validParameters != null ? !validParameters.equals(that.validParameters) : that.validParameters != null)
            return false;
        if (violationMessage != null ? !violationMessage.equals(that.violationMessage) : that.violationMessage != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = annotation != null ? annotation.hashCode() : 0;
        result = 31 * result + (validParameters != null ? validParameters.hashCode() : 0);
        result = 31 * result + (violationMessage != null ? violationMessage.hashCode() : 0);
        return result;
    }
}