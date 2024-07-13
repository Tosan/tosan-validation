package com.tosan.validation.core;

/**
 * @author Zahra Hajihosseinkhani
 * @since 27/12/2015
 */
public class FieldDescriptorExtractorFactory {

    private FieldDescriptorExtractorFactory() {
    }

    public static FieldDescriptorExtractor getInstance(String propertyPath, boolean validMapOrCollection) {

        if ((propertyPath.contains(".[#") || propertyPath.contains(".[@") || propertyPath.contains("[")) && validMapOrCollection) {
            return CollectionFieldDescriptorExtractor.getInstance();
        } else if (propertyPath.contains(".")) {
            return NestedObjectFieldDescriptorExtractor.getInstance();
        } else {
            return NormalFieldDescriptorExtractor.getInstance();
        }
    }
}
