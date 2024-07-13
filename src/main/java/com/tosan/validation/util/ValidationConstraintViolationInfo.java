package com.tosan.validation.util;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * @author Boshra Taheri
 * @since 7/31/13
 */
public class ValidationConstraintViolationInfo implements Serializable, ValidationInformation {

    @Serial
    private static final long serialVersionUID = -5178178244014143801L;
    private NodeType nodeType;
    /**
     * propertyPath can be a field name, a value, index or hash code of an element in a collection, or '[key]' or '[value]'
     */
    private String propertyPath = null;
    private PropertyPathType propertyPathType = null;
    private String invalidValue;
    private List<ValidationAnnotationInfo> validationAnnotations;
    private ValidationInformation parent = null;
    private Map<String, ValidationConstraintViolationInfo> children;

    public ValidationConstraintViolationInfo() {
        this.children = new HashMap<>();
        this.validationAnnotations = new LinkedList<>();
    }

    public ValidationConstraintViolationInfo(String invalidValue) {
        this();
        this.invalidValue = invalidValue;
    }

    public ValidationConstraintViolationInfo(String propertyPath, String invalidValue) {
        this();
        this.propertyPath = propertyPath;
        this.invalidValue = invalidValue;
    }

    public ValidationConstraintViolationInfo(NodeType nodeType) {
        this();
        this.nodeType = nodeType;
    }

    public ValidationConstraintViolationInfo(NodeType nodeType, String invalidValue) {
        this();
        this.nodeType = nodeType;
        this.invalidValue = invalidValue;
    }

    @Override
    public NodeType getNodeType() {
        return nodeType;
    }

    @Override
    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    @Override
    public String getPropertyPath() {
        return propertyPath;
    }

    @Override
    public void setPropertyPath(String propertyPath) {
        this.propertyPath = propertyPath;
    }

    @Override
    public PropertyPathType getPropertyPathType() {
        return propertyPathType;
    }

    @Override
    public void setPropertyPathType(PropertyPathType pathType) {
        this.propertyPathType = pathType;
    }

    @Override
    public String getInvalidValue() {
        return invalidValue;
    }

    @Override
    public void setInvalidValue(String invalidValue) {
        this.invalidValue = invalidValue;
    }

    @Override
    public List<ValidationAnnotationInfo> getValidationAnnotations() {
        return validationAnnotations;
    }

    public void setValidationAnnotations(List<ValidationAnnotationInfo> validationAnnotations) {
        this.validationAnnotations = validationAnnotations;
    }

    @Override
    public ValidationInformation getParent() {
        return parent;
    }

    public void setParent(ValidationInformation parent) {
        this.parent = parent;
    }

    public void addValidationAnnotationInfo(ValidationAnnotationInfo info) {
        this.validationAnnotations.add(info);
    }

    public void addValidationAnnotationInfo(Collection<ValidationAnnotationInfo> infos) {
        this.validationAnnotations.addAll(infos);
    }

    public Map<String, ValidationConstraintViolationInfo> getSubtree() {
        return children;
    }

    public void setSubtree(Map<String, ValidationConstraintViolationInfo> children) {
        this.children = children;
    }

    @Override
    public Collection<? extends ValidationInformation> getChildren() {
        return children.values();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\"").append(propertyPath);
        if (nodeType == NodeType.PRIMITIVE) {
            stringBuilder.append("(").append(invalidValue).append(")");
        }
        stringBuilder.append("\":");
        if (!children.isEmpty()) {
            stringBuilder.append("{");
        }
        if (!validationAnnotations.isEmpty()) {
            if (!children.isEmpty()) {
                //TODO eslah beshe ba tavajjoh be inke alan 3 ta validatione dige dar sathe class ezafe shode
                stringBuilder.append("\"CLASS VALIDATION\":");
            }
            stringBuilder.append(validationAnnotations);
        }
        if (!validationAnnotations.isEmpty() && !children.isEmpty()) {
            stringBuilder.append(", ");
        }
        if (!children.isEmpty()) {
            Iterator<Map.Entry<String, ValidationConstraintViolationInfo>> iterator = children.entrySet().iterator();
            for (; ; ) {
                Map.Entry<String, ValidationConstraintViolationInfo> subtree = iterator.next();
                stringBuilder.append(subtree.getValue());
                if (!iterator.hasNext()) {
                    break;
                }
                stringBuilder.append(", ");
            }
            stringBuilder.append("}");
        }

        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValidationConstraintViolationInfo info = (ValidationConstraintViolationInfo) o;

        return propertyPath.equals(info.propertyPath);
    }

    @Override
    public int hashCode() {
        return propertyPath.hashCode();
    }
}
