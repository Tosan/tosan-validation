package com.tosan.validation.core;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * checking map and collection
 *
 * @author Zahra Hajihosseinkhani
 * @since 27/12/2015
 */
public class CollectionFieldDescriptorExtractor implements FieldDescriptorExtractor {
    private static final CollectionFieldDescriptorExtractor collectionFieldDescriptorExtractor = new CollectionFieldDescriptorExtractor();

    private CollectionFieldDescriptorExtractor() {
    }

    public static CollectionFieldDescriptorExtractor getInstance() {
        return collectionFieldDescriptorExtractor;
    }

    @Override
    public String getBusinessTypeParameter(Class rootBeanClass, String propertyPath) {
        String fieldDescriptorParameter;
        String[] propertyPathParts = propertyPath.split("\\.");
        for (int i = 0; i < propertyPathParts.length - 1; i++) {
            Field targetField = FieldDescriptorAnnotationExtractorUtility.findTargetField(rootBeanClass, propertyPathParts[i]);
            if (targetField == null) {
                return "UNKNOWN_FIELD";
            } else {
                if (targetField.getGenericType() instanceof ParameterizedType type) {
                    if (propertyPathParts[i + 1].contains("key")) {
                        Type mapKeyType = type.getActualTypeArguments()[0];
                        if (i + 2 != propertyPathParts.length - 1) {
                            rootBeanClass = (Class) mapKeyType;
                            i = i + 2;
                        } else {
                            return FieldDescriptorAnnotationExtractorUtility.getBusinessTypeParameterFromFieldDescriptorAnnotation(rootBeanClass, propertyPathParts[i]);
                        }
                    } else if (propertyPathParts[i + 1].contains("value")) {
                        Type mapValueType = type.getActualTypeArguments()[1];
                        if (i + 2 != propertyPathParts.length - 1) {
                            rootBeanClass = (Class) mapValueType;
                            i = i + 2;
                        } else {
                            return FieldDescriptorAnnotationExtractorUtility.getBusinessTypeParameterFromFieldDescriptorAnnotation(rootBeanClass, propertyPathParts[i]);
                        }

                    } else {
                        Type collectionValue = type.getActualTypeArguments()[0];
                        if (i + 1 != propertyPathParts.length - 1) {
                            rootBeanClass = (Class) collectionValue;
                            i = i + 1;
                        } else {
                            return FieldDescriptorAnnotationExtractorUtility.getBusinessTypeParameterFromFieldDescriptorAnnotation(rootBeanClass, propertyPathParts[i]);
                        }
                    }
                } else {
                    if (i != propertyPathParts.length - 1) {
                        rootBeanClass = targetField.getType();
                    }
                }
            }
        }
        fieldDescriptorParameter = FieldDescriptorAnnotationExtractorUtility.getBusinessTypeParameterFromFieldDescriptorAnnotation(rootBeanClass, propertyPathParts[propertyPathParts.length - 1]);
        return fieldDescriptorParameter;
    }
}