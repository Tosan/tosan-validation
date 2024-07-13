package com.tosan.validation.core;

/**
 * @author Zahra Hajihosseinkhani
 * @since 03/01/2016
 */
public class NormalFieldDescriptorExtractor implements FieldDescriptorExtractor {

    private static final NormalFieldDescriptorExtractor normalFieldDescriptorAnnotationExtractor = new NormalFieldDescriptorExtractor();

    private NormalFieldDescriptorExtractor() {
    }

    public static NormalFieldDescriptorExtractor getInstance() {
        return normalFieldDescriptorAnnotationExtractor;
    }

    @Override
    public String getBusinessTypeParameter(Class rootBeanClass, String propertyPath) {
        return FieldDescriptorAnnotationExtractorUtility.getBusinessTypeParameterFromFieldDescriptorAnnotation(rootBeanClass, propertyPath);
    }
}
