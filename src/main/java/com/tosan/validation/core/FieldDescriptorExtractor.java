package com.tosan.validation.core;

/**
 * @author Zahra Hajihosseinkhani
 * @since 27/12/2015
 */
public interface FieldDescriptorExtractor {

    String getBusinessTypeParameter(Class rootBeanClass, String propertyPath);
}

