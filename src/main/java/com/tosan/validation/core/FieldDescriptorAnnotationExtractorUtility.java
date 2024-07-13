package com.tosan.validation.core;

import com.tosan.validation.constraints.FieldDescriptor;

import java.lang.reflect.Field;

/**
 * @author Zahra Hajihosseinkhani
 * @since 06/01/2016
 */
public class FieldDescriptorAnnotationExtractorUtility {

    public static Field findTargetField(Class rootBeanClass, String fieldName) {
        Field targetField = null;
        while (targetField == null && rootBeanClass != null && !rootBeanClass.equals(Object.class)) {
            try {
                targetField = rootBeanClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                rootBeanClass = rootBeanClass.getSuperclass();
            }
        }
        return targetField;
    }

    public static String getBusinessTypeParameterFromFieldDescriptorAnnotation(Class rootBeanClass, String fieldName) {
        Field targetField = FieldDescriptorAnnotationExtractorUtility.findTargetField(rootBeanClass, fieldName);
        if (targetField == null) {
            return "UNKNOWN_FIELD";
        } else {
            FieldDescriptor annotation = targetField.getAnnotation(FieldDescriptor.class);
            if (annotation != null) {
                return annotation.businessType();
            } else {
                return fieldName;
            }
        }
    }
}
