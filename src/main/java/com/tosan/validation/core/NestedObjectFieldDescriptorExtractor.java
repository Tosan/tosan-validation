package com.tosan.validation.core;

import java.lang.reflect.Field;

/**
 * @author Zahra Hajihosseinkhani
 * @since 27/12/2015
 */
public class NestedObjectFieldDescriptorExtractor implements FieldDescriptorExtractor {
    private static final NestedObjectFieldDescriptorExtractor nestedObjectFieldDescriptorAnnotationExtractor = new NestedObjectFieldDescriptorExtractor();

    private NestedObjectFieldDescriptorExtractor() {
    }

    public static NestedObjectFieldDescriptorExtractor getInstance() {
        return nestedObjectFieldDescriptorAnnotationExtractor;
    }

    @Override
    public String getBusinessTypeParameter(Class rootBeanClass, String propertyPath) {
        String fieldDescriptorParameter;
        String[] propertyPathParts = propertyPath.split("\\.");
        for (int i = 0; i < propertyPathParts.length; i++) {
            Field targetField = FieldDescriptorAnnotationExtractorUtility.findTargetField(rootBeanClass, propertyPathParts[i]);
            if (targetField == null) {
                return "UNKNOWN_FIELD";
            } else {
                if ((i + 1 <= propertyPathParts.length - 1) && propertyPathParts[i + 1].contains("[")) {
                    return FieldDescriptorAnnotationExtractorUtility.getBusinessTypeParameterFromFieldDescriptorAnnotation(rootBeanClass, propertyPathParts[i]);
                } else if (i != propertyPathParts.length - 1) {
                    rootBeanClass = targetField.getType();
                }
            }
        }
        fieldDescriptorParameter = FieldDescriptorAnnotationExtractorUtility.getBusinessTypeParameterFromFieldDescriptorAnnotation(rootBeanClass, propertyPathParts[propertyPathParts.length - 1]);
        return fieldDescriptorParameter;
    }
}
