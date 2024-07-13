package com.tosan.validation.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Boshra Taheri
 * @since 7/31/13
 */
public class ValidationConstraintViolationInfoTreeGenerator {
    private static String invalidValue = null;
    private static List<ValidationAnnotationInfo> validationAnnotationsInfo = null;

    public static Map<String, ValidationConstraintViolationInfo> generateTree(Map<String, ValidationConstraintViolationInfo> violationsInfo, ValidationConstraintViolationInfo rootNode) {
        Map<String, ValidationConstraintViolationInfo> validationConstraintViolationInfoTree = new HashMap<>();
        for (Map.Entry<String, ValidationConstraintViolationInfo> entry : violationsInfo.entrySet()) {
            invalidValue = entry.getValue().getInvalidValue();
            validationAnnotationsInfo = entry.getValue().getValidationAnnotations();
            String propertyPath = entry.getKey();
            String[] pathParts = propertyPath.split("\\.");
            String root = pathParts[0];
            entry.getValue().setPropertyPath(root);
            entry.getValue().setPropertyPathType(PropertyPathType.FIELD_NAME);
            entry.getValue().setParent(rootNode);
            if (pathParts.length == 1) {
                if (validationConstraintViolationInfoTree.get(root) != null) {
                    validationConstraintViolationInfoTree.get(root).addValidationAnnotationInfo(entry.getValue().getValidationAnnotations());
                } else {
                    entry.getValue().setNodeType(NodeType.PRIMITIVE);
                    validationConstraintViolationInfoTree.put(entry.getValue().getPropertyPath(), entry.getValue());
                }
                continue;
            }
            entry.getValue().setInvalidValue(null);
            entry.getValue().setValidationAnnotations(new LinkedList<>());
            entry.getValue().setNodeType(NodeType.COMPOSITE);
            if (validationConstraintViolationInfoTree.get(root) != null) {
                ValidationConstraintViolationInfo temp = getSubtree(pathParts, 1, validationConstraintViolationInfoTree.get(root));
                if (validationConstraintViolationInfoTree.get(root).getSubtree().get(pathParts[1]) == null) {
                    validationConstraintViolationInfoTree.get(root).getSubtree().put(pathParts[1], temp);
                } else {
                    validationConstraintViolationInfoTree.get(root).getSubtree().get(pathParts[1]).getSubtree().putAll(temp.getSubtree());
                }
            } else {
                ValidationConstraintViolationInfo temp = getSubtree(pathParts, 1, entry.getValue());
                if (entry.getValue().getSubtree().get(pathParts[1]) == null) {
                    entry.getValue().getSubtree().put(pathParts[1], temp);
                } else {
                    entry.getValue().getSubtree().get(pathParts[1]).getSubtree().putAll(temp.getSubtree());
                }
                validationConstraintViolationInfoTree.put(entry.getValue().getPropertyPath(), entry.getValue());
            }
        }
        return validationConstraintViolationInfoTree;
    }

    private static ValidationConstraintViolationInfo getSubtree(String[] pathParts, int startIndex, ValidationConstraintViolationInfo root) {

        ValidationConstraintViolationInfo subTreeRoot;
        if (root.getSubtree().get(pathParts[startIndex]) != null) {
            subTreeRoot = root.getSubtree().get(pathParts[startIndex]);
        } else {
            subTreeRoot = new ValidationConstraintViolationInfo();
            subTreeRoot.setParent(root);
            if (pathParts[startIndex].startsWith("[@")) {
                root.setNodeType(NodeType.COLLECTION);
                subTreeRoot.setPropertyPath(getInnerValue(pathParts[startIndex], 3));
                subTreeRoot.setPropertyPathType(PropertyPathType.INDEX);
            } else if (pathParts[startIndex].startsWith("[#")) {
                root.setNodeType(NodeType.COLLECTION);
                subTreeRoot.setPropertyPath(getInnerValue(pathParts[startIndex], 3));
                subTreeRoot.setPropertyPathType(PropertyPathType.HASH);
            } else if (pathParts[startIndex].startsWith("[key]")) {
                subTreeRoot.setNodeType(NodeType.COLLECTION);
                subTreeRoot.setPropertyPath(pathParts[startIndex]);
                root.setNodeType(NodeType.MAP);
                subTreeRoot.setPropertyPathType(PropertyPathType.MAP_KEY);
            } else if (pathParts[startIndex].startsWith("[value]")) {
                subTreeRoot.setNodeType(NodeType.COLLECTION);
                root.setNodeType(NodeType.MAP);
                subTreeRoot.setPropertyPath(pathParts[startIndex]);
                subTreeRoot.setPropertyPathType(PropertyPathType.MAP_VALUE);
            } else if (pathParts[startIndex].startsWith("[")) {
                subTreeRoot.setNodeType(NodeType.COLLECTION);
                root.setNodeType(NodeType.COLLECTION);
                subTreeRoot.setPropertyPath(pathParts[startIndex]);
                subTreeRoot.setPropertyPathType(PropertyPathType.VALUE);
            } else if (pathParts[startIndex].startsWith("<#")) {
                subTreeRoot.setNodeType(NodeType.COMPOSITE);
                subTreeRoot.setPropertyPath(getInnerValue(pathParts[startIndex], 2));
                subTreeRoot.setPropertyPathType(PropertyPathType.HASH);
            } else if (pathParts[startIndex].startsWith("<")) {
                subTreeRoot.setNodeType(NodeType.COMPOSITE);
                subTreeRoot.setPropertyPath(getInnerValue(pathParts[startIndex], 1));
                subTreeRoot.setPropertyPathType(PropertyPathType.VALUE);
            } else {
                subTreeRoot.setNodeType(NodeType.COMPOSITE);
                subTreeRoot.setPropertyPath(pathParts[startIndex]);
                subTreeRoot.setPropertyPathType(PropertyPathType.FIELD_NAME);
            }
        }
        if (pathParts.length > startIndex + 1) {
            if (root.getNodeType() == null) {
                root.setNodeType(subTreeRoot.getNodeType());
            }
            ValidationConstraintViolationInfo temp = getSubtree(pathParts, startIndex + 1, subTreeRoot);
            if (subTreeRoot.getSubtree().get(pathParts[startIndex + 1]) == null) {
                subTreeRoot.getSubtree().put(pathParts[startIndex + 1], temp);
            } else {
                subTreeRoot.getSubtree().get(pathParts[startIndex + 1]).getSubtree().putAll(temp.getSubtree());
            }
        } else {
            if (root.getNodeType() == null) {
                root.setNodeType(NodeType.COMPOSITE);
            }
            subTreeRoot.setInvalidValue(invalidValue);
            subTreeRoot.setNodeType(NodeType.PRIMITIVE);
            subTreeRoot.addValidationAnnotationInfo(validationAnnotationsInfo);
        }
        return subTreeRoot;
    }

    private static String getInnerValue(String pathPart, int innerValueOffset) {
        return pathPart.substring(innerValueOffset, pathPart.length() - 1);
    }
}
