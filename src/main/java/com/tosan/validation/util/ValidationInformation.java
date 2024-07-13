package com.tosan.validation.util;

import java.util.Collection;
import java.util.List;

/**
 * @author Boshra Taheri
 * @since 8/21/13
 */
public interface ValidationInformation {

    NodeType getNodeType();

    void setNodeType(NodeType nodeType);

    String getPropertyPath();

    void setPropertyPath(String propertyPath);

    PropertyPathType getPropertyPathType();

    void setPropertyPathType(PropertyPathType pathType);

    String getInvalidValue();

    void setInvalidValue(String invalidValue);

    List<ValidationAnnotationInfo> getValidationAnnotations();

    ValidationInformation getParent();

    Collection<? extends ValidationInformation> getChildren();
}
