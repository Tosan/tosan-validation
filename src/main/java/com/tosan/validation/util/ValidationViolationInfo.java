package com.tosan.validation.util;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @author Zahra Hajihosseinkhani
 * @since 1/11/15
 */
public class ValidationViolationInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = -5351019677121560266L;
    private static final List<String> securityBusinessTypes = Arrays.asList("password", "pan", "pin", "cvv2");
    private String invalidValue;
    private String businessType;
    private String propertyPath;
    private List<ValidationAnnotationInfo> validationAnnotations;

    public String getInvalidValue() {
        return invalidValue;
    }

    public void setInvalidValue(String invalidValue) {
        this.invalidValue = invalidValue;
    }

    public List<ValidationAnnotationInfo> getValidationAnnotations() {
        return validationAnnotations;
    }

    public void setValidationAnnotations(List<ValidationAnnotationInfo> validationAnnotations) {
        this.validationAnnotations = validationAnnotations;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getPropertyPath() {
        return propertyPath;
    }

    public void setPropertyPath(String propertyPath) {
        this.propertyPath = propertyPath;
    }

    public void addValidationAnnotationInfo(ValidationAnnotationInfo info) {
        this.validationAnnotations.add(info);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("invalidValue: '").append(getMaskedInvalidValue()).append("'\t");
        sb.append("businessType: '").append(businessType).append("'\t");
        sb.append("propertyPath: '").append(propertyPath).append("'\t");
        sb.append(validationAnnotations);
        return sb.toString();
    }

    private String getMaskedInvalidValue() {
        if (securityBusinessTypes.contains(businessType)) {
            return invalidValue.substring(0, 2) + "***";
        }
        return invalidValue;
    }

    public int hashCode() {
        return validationAnnotations.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValidationViolationInfo that = (ValidationViolationInfo) o;
        return validationAnnotations.equals(that.validationAnnotations);
    }
}
