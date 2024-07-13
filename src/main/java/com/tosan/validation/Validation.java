package com.tosan.validation;

import com.tosan.validation.constraints.*;
import com.tosan.validation.constraints.impl.CollectionValidator;
import com.tosan.validation.constraints.impl.MapValidator;
import com.tosan.validation.core.FieldDescriptorExtractor;
import com.tosan.validation.core.FieldDescriptorExtractorFactory;
import com.tosan.validation.core.ValidatorBuilder;
import com.tosan.validation.util.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.metadata.ConstraintDescriptor;
import org.apache.commons.lang3.EnumUtils;
import org.hibernate.validator.constraints.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringReader;
import java.lang.annotation.Annotation;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Future;

/**
 * @author Boshra Taheri
 * @since 8/21/13
 */
public class Validation {
    private static final Logger logger = LoggerFactory.getLogger(Validation.class);
    private final HashMap<String, String> properties;
    private final Validator validator;
    private List<String> ignoredParameters = new ArrayList<>();
    private List<String> semiIgnoredParameters = new ArrayList<>();

    public Validation(ValidatorBuilder validatorBuilder, HashMap<String, String> validationProperties) {
        this.validator = validatorBuilder.getValidator();
        this.properties = validationProperties;
    }

    public void setIgnoredParameters(List<String> ignoredParameters) {
        this.ignoredParameters = ignoredParameters;
    }

    public void setSemiIgnoredParameters(List<String> semiIgnoredParameters) {
        this.semiIgnoredParameters = semiIgnoredParameters;
    }

    public Map<String, List<ValidationViolationInfo>> validate(Object parameter) {
        if (parameter instanceof Collection<?>) {
            return validateCollectionOfObjects(parameter);
        } else {
            return validateComplexObject(parameter);
        }
    }

    private Map<String, List<ValidationViolationInfo>> validateCollectionOfObjects(Object parameter) {
        Map<String, List<ValidationViolationInfo>> validationViolationInformation = new HashMap<>();
        for (Object element : (Collection<?>) parameter) {
            validationViolationInformation = validateComplexObject(element);
        }
        return validationViolationInformation;
    }

    private Map<String, List<ValidationViolationInfo>> validateComplexObject(Object parameter) {
        Map<String, List<ValidationViolationInfo>> validationViolationInformation = new HashMap<>();
        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(parameter);
        CollectionValidator.removeMessageAndPath();
        MapValidator.removeMessageAndPath();
        if (constraintViolationSet != null && !constraintViolationSet.isEmpty()) {
            validationViolationInformation = getValidationConstraintViolationInfo(constraintViolationSet);
        }
        return validationViolationInformation;
    }

    @SuppressWarnings("unchecked")
    private Map<String, List<ValidationViolationInfo>> getValidationConstraintViolationInfo(Set<ConstraintViolation<Object>> constraintViolationSet) {
        Map<String, ValidationConstraintViolationInfo> invalidParameters = new HashMap<>();
        Map<String, List<ValidationViolationInfo>> validationViolationInformations = new HashMap<>();
        for (ConstraintViolation<Object> invalidValue : constraintViolationSet) {
            boolean validateMapOrCollection = false;
            Class<? extends Annotation> annotationClass = invalidValue.getConstraintDescriptor().getAnnotation().annotationType();
            if (annotationClass.equals(ValidMap.class) || annotationClass.equals(ValidCollection.class)) {
                String annotationName = (String) load(invalidValue.getMessage()).get("annotation");
                validateMapOrCollection = true;
                try {
                    annotationClass = (Class<? extends Annotation>) Class.forName(annotationName);
                } catch (ClassNotFoundException e) {
                    logger.error("Validation annotation '{}' does not exist.", annotationName);
                }
            }
            String annotation = annotationClass.getSimpleName();
            ValidationConstraintViolationInfo violationInfo;
            ValidationAnnotationInfo validationAnnotationInfo
                    = new ValidationAnnotationInfo(validateEnumParameter(annotation.toUpperCase()));
            String propertyPath;
            String violationMessage = invalidValue.getMessage();
            if (invalidValue.getPropertyPath() != null && !invalidValue.getPropertyPath().toString().isEmpty()) {
                propertyPath = invalidValue.getPropertyPath().toString();
            } else {
                propertyPath = invalidValue.getRootBeanClass().getSimpleName();
            }
            violationInfo = invalidParameters.get(propertyPath);
            if (violationInfo == null) {
                violationInfo = new ValidationConstraintViolationInfo();
            }

            if (propertyPath.contains("=")) {
                String wholePath;
                wholePath = propertyPath;
                propertyPath = wholePath.substring(0, wholePath.indexOf('='));
                String value = wholePath.substring(wholePath.indexOf('=') + 1);
                if (annotationClass.equals(FutureDate.class) || annotationClass.equals(PastDate.class)) {
                    violationInfo.setInvalidValue(formatDate(value));
                } else {
                    violationInfo.setInvalidValue(value);
                }
                if (invalidValue.getConstraintDescriptor().getAnnotation() instanceof ValidMap
                        || invalidValue.getConstraintDescriptor().getAnnotation() instanceof ValidCollection) {
                    ViolationInfo info = getValidationParameters(invalidValue.getMessage());
                    validationAnnotationInfo.setValidParameters(info.getValidationParameters());
                    violationMessage = info.getViolationMessage();
                } else {
                    validationAnnotationInfo.setValidParameters(getValidationParameters(invalidValue));
                }
            } else if (!(invalidValue.getInvalidValue() instanceof Collection) && !(invalidValue.getInvalidValue() instanceof Map)
                    ||
                    (((invalidValue.getInvalidValue() instanceof Collection) || (invalidValue.getInvalidValue() instanceof Map)))
                            && (!(invalidValue.getConstraintDescriptor().getAnnotation() instanceof ValidMap)
                            && !(invalidValue.getConstraintDescriptor().getAnnotation() instanceof ValidCollection))) {
                violationInfo.setInvalidValue(invalidValue.getInvalidValue() != null ? invalidValue.getInvalidValue().toString() : null);
                if (annotationClass.equals(FutureDate.class) || annotationClass.equals(PastDate.class)) {
                    violationInfo.setInvalidValue(formatDate(invalidValue.getInvalidValue().toString()));
                } else {
                    validationAnnotationInfo.setValidParameters(getValidationParameters(invalidValue));
                }
            } else {
                continue;
            }
            violationInfo.setPropertyPath(propertyPath);
            validationAnnotationInfo.setViolationMessage(violationMessage);
            String fieldName = getFieldName(propertyPath, validateMapOrCollection);
            if (annotationClass.equals(Expression.class) || annotationClass.equals(Compare.class)
                    || annotationClass.equals(DateDifference.class) || annotationClass.equals(Difference.class)) {
                violationInfo.addValidationAnnotationInfo(validationAnnotationInfo);
                violationInfo.setInvalidValue(null);
                putValidationViolationInformationMapEntry(getBusinessTypeParameter(invalidValue, annotationClass), fieldName,
                        violationInfo, validationViolationInformations);
            } else {
                String businessType = getBusinessType(invalidValue.getRootBeanClass(), propertyPath, validateMapOrCollection);
                if (invalidParameters.get(propertyPath) == null) {
                    violationInfo.addValidationAnnotationInfo(validationAnnotationInfo);
                    invalidParameters.put(propertyPath, violationInfo);
                    putValidationViolationInformationMapEntry(businessType, fieldName, violationInfo, validationViolationInformations);
                } else {
                    invalidParameters.get(propertyPath).addValidationAnnotationInfo(validationAnnotationInfo);
                }
            }
        }
        logger.info(validationViolationInformations.toString());
        return validationViolationInformations;
    }

    private Map<ValidationParametersKey, Object> getValidationParameters(ConstraintViolation<Object> violation) {
        Map<ValidationParametersKey, Object> validationParameters = new HashMap<>();
        ConstraintDescriptor constraintDescriptor = violation.getConstraintDescriptor();
        Map<Object, Object> attributes = constraintDescriptor.getAttributes();
        Annotation constraintAnnotation = constraintDescriptor.getAnnotation();
        if (constraintAnnotation instanceof Pattern) {
            validationParameters.putAll(getPatternValidatorParameters(attributes));
        } else if (constraintAnnotation instanceof Length) {
            validationParameters.putAll(getLengthValidatorParameters(attributes));
        } else if (constraintAnnotation instanceof Size) {
            validationParameters.putAll(getSizeValidatorParameters(attributes));
        } else if (constraintAnnotation instanceof Digits) {
            validationParameters.putAll(getDigitsValidatorParameters(attributes));
        } else if (constraintAnnotation instanceof Max) {
            validationParameters.putAll(getMinMaxValidatorParameters(attributes, ValidationParametersKey.MAX));
        } else if (constraintAnnotation instanceof Min) {
            validationParameters.putAll(getMinMaxValidatorParameters(attributes, ValidationParametersKey.MIN));
        } else if (constraintAnnotation instanceof jakarta.validation.constraints.Min) {
            String value = attributes.get("value").toString();
            validationParameters.put(ValidationParametersKey.MIN, value);
        } else if (constraintAnnotation instanceof jakarta.validation.constraints.Size) {
            validationParameters.putAll(getNonTosanValidatorParameters(attributes));
        } else if (constraintAnnotation instanceof org.hibernate.validator.constraints.Length) {
            validationParameters.putAll(getNonTosanValidatorParameters(attributes));
        } else if (constraintAnnotation instanceof Range) {
            validationParameters.putAll(getNonTosanValidatorParameters(attributes));
        } else if (constraintAnnotation instanceof Expression) {
            String keyValue = (String) attributes.get("keyValue");
            String value;
            if (keyValue.isEmpty()) {
                value = (String) attributes.get("value");
            } else {
                value = properties.get(keyValue);
            }
            String identifier = (String) attributes.get("identifier");
            validationParameters.put(ValidationParametersKey.EXPRESSION, value);
            validationParameters.put(ValidationParametersKey.IDENTIFIER, identifier);
        } else if (constraintAnnotation instanceof Compare) {
            String lessEqFieldName = (String) attributes.get("lessFieldName");
            String greaterFieldName = (String) attributes.get("greaterFieldName");
            Boolean checkEquality = Boolean.valueOf(attributes.get("checkEquality").toString());
            validationParameters.put(ValidationParametersKey.LESS_FIELD_NAME, lessEqFieldName);
            validationParameters.put(ValidationParametersKey.GREATER_FIELD_NAME, greaterFieldName);
            validationParameters.put(ValidationParametersKey.CHECK_EQUALITY, checkEquality);
        } else if (constraintAnnotation instanceof DateDifference) {
            String fromFieldName = (String) attributes.get("fromFieldName");
            String toFieldName = (String) attributes.get("toFieldName");
            String maxDifferenceKey = (String) attributes.get("maxDifferenceKey");
            String minDifferenceKey = (String) attributes.get("minDifferenceKey");
            DateDifferenceUnit unit = (DateDifferenceUnit) attributes.get("unit");
            String maxDifference;
            String minDifference;
            if (!maxDifferenceKey.isEmpty()) {
                maxDifference = properties.get(maxDifferenceKey);
            } else {
                maxDifference = attributes.get("maxDifference").toString();
            }
            if (!minDifferenceKey.isEmpty()) {
                minDifference = properties.get(minDifferenceKey);
            } else {
                minDifference = attributes.get("minDifference").toString();
            }
            validationParameters.put(ValidationParametersKey.FROM, fromFieldName);
            validationParameters.put(ValidationParametersKey.TO, toFieldName);
            validationParameters.put(ValidationParametersKey.MAX, maxDifference);
            validationParameters.put(ValidationParametersKey.MIN, minDifference);
            validationParameters.put(ValidationParametersKey.UNIT, unit);
        } else if (constraintAnnotation instanceof Difference) {
            String fromFieldName = (String) attributes.get("fromFieldName");
            String toFieldName = (String) attributes.get("toFieldName");
            String maxDifferenceKey = (String) attributes.get("maxDifferenceKey");
            String minDifferenceKey = (String) attributes.get("minDifferenceKey");
            String maxDifference;
            String minDifference;
            if (!maxDifferenceKey.isEmpty()) {
                maxDifference = properties.get(maxDifferenceKey);
            } else {
                maxDifference = attributes.get("maxDifference").toString();
            }
            if (!minDifferenceKey.isEmpty()) {
                minDifference = properties.get(minDifferenceKey);
            } else {
                minDifference = attributes.get("minDifference").toString();
            }
            validationParameters.put(ValidationParametersKey.FROM, fromFieldName);
            validationParameters.put(ValidationParametersKey.TO, toFieldName);
            validationParameters.put(ValidationParametersKey.MAX, maxDifference);
            validationParameters.put(ValidationParametersKey.MIN, minDifference);
        } else if (constraintAnnotation instanceof Expressions) {
            //Hichvaght in annotation ro barnemigardoone;be ezaye har Expression yek ConstraintViolation barmigardoone
        } else if (constraintAnnotation instanceof Group) {
            //TODO nemidunam pointLiterals va integerLiterals chi hastan
        } else if (constraintAnnotation instanceof ValidateJalaliDate) {
            //TODO attributei nadare ke meghdaresh be darde kanal bokhore
        } else if (constraintAnnotation instanceof PastJalaliDate) {
            //TODO attributei nadare ke meghdaresh be darde kanal bokhore
        } else if (constraintAnnotation instanceof FutureJalaliDate) {
            //TODO attributei nadare ke meghdaresh be darde kanal bokhore
        } else if (constraintAnnotation instanceof FutureDate) {
            //TODO attributei nadare ke meghdaresh be darde kanal bokhore
        } else if (constraintAnnotation instanceof Future) {
            //TODO attributei nadare ke meghdaresh be darde kanal bokhore
        } else if (constraintAnnotation instanceof Past) {
            //TODO attributei nadare ke meghdaresh be darde kanal bokhore
        } else if (constraintAnnotation instanceof Email) {
            //TODO attributei nadare ke meghdaresh be darde kanal bokhore
        } else if (constraintAnnotation instanceof NotEmpty) {
            //TODO attributei nadare ke meghdaresh be darde kanal bokhore
        } else if (constraintAnnotation instanceof Iban) {
            //TODO attributei nadare ke meghdaresh be darde kanal bokhore
        } else if (constraintAnnotation instanceof SecureValue) {
            //TODO attributei nadare ke meghdaresh be darde kanal bokhore
        } else if (constraintAnnotation instanceof Encoding) {
            //TODO attributei nadare ke meghdaresh be darde kanal bokhore
        } else {
            logger.error("Validator '{}' is unknown.", constraintAnnotation.annotationType().getName());
        }
        return validationParameters;
    }

    private ViolationInfo getValidationParameters(String paramtersString) {
        Map<ValidationParametersKey, Object> validationParameters = new HashMap<>();
        StringBuilder violationMessage = new StringBuilder();
        Properties attributes = load(paramtersString);
        String constraintAnnotation = attributes.getProperty("annotation");
        if (constraintAnnotation.equals(Pattern.class.getCanonicalName())) {
            validationParameters.putAll(getPatternValidatorParameters(attributes));
            violationMessage.append("The value must match the following pattern: ").append(validationParameters.get(ValidationParametersKey.REGEXP));
        } else if (constraintAnnotation.equals(Length.class.getCanonicalName())) {
            validationParameters.putAll(getLengthValidatorParameters(attributes));
            violationMessage.append("The Length of the parameter must be between ").append(validationParameters.get(ValidationParametersKey.MIN)).append(" and ").append(validationParameters.get(ValidationParametersKey.MAX));
        } else if (constraintAnnotation.equals(Size.class.getCanonicalName())) {
            validationParameters.putAll(getSizeValidatorParameters(attributes));
            violationMessage.append("The size of the parameter should be between ").append(validationParameters.get(ValidationParametersKey.MIN)).append(" and ").append(validationParameters.get(ValidationParametersKey.MAX));
        } else if (constraintAnnotation.equals(Digits.class.getCanonicalName())) {
            validationParameters.putAll(getDigitsValidatorParameters(attributes));
            violationMessage.append("The number of integer digits must be less than ").append(validationParameters.get(ValidationParametersKey.INTEGER)).append("and").append(" the number of fraction digits must be less than ").append(validationParameters.get(ValidationParametersKey.FRACTION));
        } else if (constraintAnnotation.equals(Max.class.getCanonicalName())) {
            validationParameters.putAll(getMinMaxValidatorParameters(attributes, ValidationParametersKey.MAX));
            violationMessage.append("The value must be less than ").append(validationParameters.get(ValidationParametersKey.MAX));
        } else if (constraintAnnotation.equals(Min.class.getCanonicalName())) {
            validationParameters.putAll(getMinMaxValidatorParameters(attributes, ValidationParametersKey.MIN));
            violationMessage.append("The value must be bigger than ").append(validationParameters.get(ValidationParametersKey.MIN));
        } else if (constraintAnnotation.equals(jakarta.validation.constraints.Min.class.getCanonicalName())) {
            String value = attributes.get("value").toString();
            validationParameters.put(ValidationParametersKey.MIN, value);
            violationMessage.append("The value must be bigger than ").append(value);
        } else if (constraintAnnotation.equals(jakarta.validation.constraints.Size.class.getCanonicalName())) {
            validationParameters.putAll(getNonTosanValidatorParameters(attributes));
            violationMessage.append("The size of the parameter should be between ").append(validationParameters.get(ValidationParametersKey.MIN)).append(" and ").append(validationParameters.get(ValidationParametersKey.MAX));
        } else if (constraintAnnotation.equals(org.hibernate.validator.constraints.Length.class.getCanonicalName())) {
            validationParameters.putAll(getNonTosanValidatorParameters(attributes));
            violationMessage.append("The Length of the parameter must be between ").append(validationParameters.get(ValidationParametersKey.MIN)).append(" and ").append(validationParameters.get(ValidationParametersKey.MAX));
        } else if (constraintAnnotation.equals(Range.class.getCanonicalName())) {
            validationParameters.putAll(getNonTosanValidatorParameters(attributes));
            violationMessage.append("The value of the parameter must be between ").append(validationParameters.get(ValidationParametersKey.MIN)).append(" and ").append(validationParameters.get(ValidationParametersKey.MAX));
        } else if (constraintAnnotation.equals(NotNull.class.getCanonicalName())) {
            violationMessage.append("The value of the parameter must not be null ");
        } else if (constraintAnnotation.equals(Expression.class.getCanonicalName())) {
            String keyValue = (String) attributes.get("keyValue");
            String value;
            if (keyValue.isEmpty()) {
                value = (String) attributes.get("value");
            } else {
                value = properties.get(keyValue);
            }
            if (!value.isEmpty()) {
                validationParameters.put(ValidationParametersKey.EXPRESSION, value);
                if (!attributes.get("message").toString().contains("{")) {
                    violationMessage.append(attributes.get("message"));
                } else {
                    violationMessage.append("The following condition must be satisfied: ").append(value);
                }
            }
            String identifier = properties.get("identifier");
            validationParameters.put(ValidationParametersKey.IDENTIFIER, identifier);
        } else if (constraintAnnotation.equals(Compare.class.getCanonicalName())) {
            String lessEqFieldName = (String) attributes.get("lessFieldName");
            String greaterFieldName = (String) attributes.get("greaterFieldName");
            Boolean checkEquality = Boolean.valueOf(attributes.get("checkEquality").toString());
            validationParameters.put(ValidationParametersKey.LESS_FIELD_NAME, lessEqFieldName);
            validationParameters.put(ValidationParametersKey.GREATER_FIELD_NAME, greaterFieldName);
            validationParameters.put(ValidationParametersKey.CHECK_EQUALITY, checkEquality);
            violationMessage.append(attributes.get("message"));
        } else if (constraintAnnotation.equals(DateDifference.class.getCanonicalName())) {
            String fromFieldName = (String) attributes.get("fromFieldName");
            String toFieldName = (String) attributes.get("toFieldName");
            String maxDifferenceKey = (String) attributes.get("maxDifferenceKey");
            String minDifferenceKey = (String) attributes.get("minDifferenceKey");
            DateDifferenceUnit unit = DateDifferenceUnit.valueOf((String) attributes.get("unit"));
            String maxDifference;
            String minDifference;
            if (!maxDifferenceKey.isEmpty()) {
                maxDifference = properties.get(maxDifferenceKey);
            } else {
                maxDifference = attributes.get("maxDifference").toString();
            }
            if (!minDifferenceKey.isEmpty()) {
                minDifference = properties.get(minDifferenceKey);
            } else {
                minDifference = attributes.get("minDifference").toString();
            }
            validationParameters.put(ValidationParametersKey.FROM, fromFieldName);
            validationParameters.put(ValidationParametersKey.TO, toFieldName);
            validationParameters.put(ValidationParametersKey.MAX, maxDifference);
            validationParameters.put(ValidationParametersKey.MIN, minDifference);
            validationParameters.put(ValidationParametersKey.UNIT, unit);
            violationMessage.append(attributes.get("message"));
        } else if (constraintAnnotation.equals(Difference.class.getCanonicalName())) {
            String fromFieldName = (String) attributes.get("fromFieldName");
            String toFieldName = (String) attributes.get("toFieldName");
            String maxDifferenceKey = (String) attributes.get("maxDifferenceKey");
            String minDifferenceKey = (String) attributes.get("minDifferenceKey");
            String maxDifference;
            String minDifference;
            if (!maxDifferenceKey.isEmpty()) {
                maxDifference = properties.get(maxDifferenceKey);
            } else {
                maxDifference = attributes.get("maxDifference").toString();
            }
            if (!minDifferenceKey.isEmpty()) {
                minDifference = properties.get(minDifferenceKey);
            } else {
                minDifference = attributes.get("minDifference").toString();
            }
            validationParameters.put(ValidationParametersKey.FROM, fromFieldName);
            validationParameters.put(ValidationParametersKey.TO, toFieldName);
            validationParameters.put(ValidationParametersKey.MAX, maxDifference);
            validationParameters.put(ValidationParametersKey.MIN, minDifference);
        } else if (constraintAnnotation.equals(Expressions.class.getCanonicalName())) {
            //Hichvaght in annotation ro barnemigardoone;be ezaye har Expression yek ConstraintViolation barmigardoone
        } else if (constraintAnnotation.equals(Group.class.getCanonicalName())) {
            //TODO nemidunam pointLiterals va integerLiterals chi hastan
        } else if (constraintAnnotation.equals(ValidateJalaliDate.class.getCanonicalName())) {
            //TODO attributei nadare ke meghdaresh be darde kanal bokhore
        } else if (constraintAnnotation.equals(PastJalaliDate.class.getCanonicalName())) {
            //TODO attributei nadare ke meghdaresh be darde kanal bokhore
        } else if (constraintAnnotation.equals(FutureJalaliDate.class.getCanonicalName())) {
            //TODO attributei nadare ke meghdaresh be darde kanal bokhore
        } else if (constraintAnnotation.equals(FutureDate.class.getCanonicalName())) {
            //TODO attributei nadare ke meghdaresh be darde kanal bokhore
            violationMessage.append(attributes.get("message"));
        } else if (constraintAnnotation.equals(PastDate.class.getCanonicalName())) {
            violationMessage.append(attributes.get("message"));
        } else if (constraintAnnotation.equals(Future.class.getCanonicalName())) {
            //TODO attributei nadare ke meghdaresh be darde kanal bokhore
        } else if (constraintAnnotation.equals(Past.class.getCanonicalName())) {
            //TODO attributei nadare ke meghdaresh be darde kanal bokhore
        } else if (constraintAnnotation.equals(Email.class.getCanonicalName())) {
            //TODO attributei nadare ke meghdaresh be darde kanal bokhore
        } else if (constraintAnnotation.equals(NotEmpty.class.getCanonicalName())) {
            //TODO attributei nadare ke meghdaresh be darde kanal bokhore
        } else if (constraintAnnotation.equals(Iban.class.getCanonicalName())) {
            //TODO attributei nadare ke meghdaresh be darde kanal bokhore
        } else if (constraintAnnotation.equals(SecureValue.class.getCanonicalName())) {
            //TODO attributei nadare ke meghdaresh be darde kanal bokhore
        } else if (constraintAnnotation.equals(Pervasive.class.getCanonicalName())) {
            //TODO attributei nadare ke meghdaresh be darde kanal bokhore
        } else {
            logger.error("Validator '{}' is unknown.", constraintAnnotation);
        }
        return new ViolationInfo(violationMessage.toString(), validationParameters);
    }

    private Properties load(String propertiesString) {
        Properties properties = new Properties();
        try {
            properties.load(new StringReader(propertiesString));
        } catch (IOException e) {
            logger.error("Cannot parse propertyString.");
        }
        return properties;
    }

    private Map<ValidationParametersKey, String> getSizeValidatorParameters(Map<Object, Object> attributes) {
        Map<ValidationParametersKey, String> validationParameters = new HashMap<>();
        String minSizeKey = (String) attributes.get("minSizeKey");
        String maxSizeKey = (String) attributes.get("maxSizeKey");
        String minValue;
        if (minSizeKey.isEmpty()) {
            minValue = attributes.get("min").toString();
        } else {
            minValue = properties.get(minSizeKey);
        }
        String maxValue;
        if (maxSizeKey.isEmpty()) {
            maxValue = attributes.get("max").toString();
        } else {
            maxValue = properties.get(maxSizeKey);
        }
        validationParameters.put(ValidationParametersKey.MIN, minValue);
        validationParameters.put(ValidationParametersKey.MAX, maxValue);
        return validationParameters;
    }

    private Map<ValidationParametersKey, String> getLengthValidatorParameters(Map<Object, Object> attributes) {
        Map<ValidationParametersKey, String> validationParameters = new HashMap<>();
        String minKey = (String) attributes.get("minKey");
        String maxKey = (String) attributes.get("maxKey");
        String minValue = properties.get(minKey);
        if (minValue == null) {
            minValue = String.valueOf(attributes.get("min"));
        }
        String maxValue = properties.get(maxKey);
        if (maxValue == null) {
            maxValue = String.valueOf(attributes.get("max"));
        }
        validationParameters.put(ValidationParametersKey.MIN, minValue);
        validationParameters.put(ValidationParametersKey.MAX, maxValue);
        return validationParameters;
    }

    private Map<ValidationParametersKey, String> getPatternValidatorParameters(Map<Object, Object> attributes) {
        Map<ValidationParametersKey, String> validationParameters = new HashMap<>();
        String regexpKey = (String) attributes.get("regexpKey");
        if (regexpKey == null) {
            regexpKey = (String) attributes.get("regexp");
        }
        String regexpValue = properties.get(regexpKey);
        validationParameters.put(ValidationParametersKey.REGEXP, regexpValue);
        return validationParameters;
    }

    private Map<ValidationParametersKey, String> getDigitsValidatorParameters(Map<Object, Object> attributes) {
        Map<ValidationParametersKey, String> validationParameters = new HashMap<>();
        String integerKey = (String) attributes.get("integerKey");
        String fractionKey = (String) attributes.get("fractionKey");
        String integerValue;
        if (integerKey.isEmpty()) {
            integerValue = attributes.get("integer").toString();
        } else {
            integerValue = properties.get(integerKey);
        }
        String fractionValue;
        if (fractionKey.isEmpty()) {
            fractionValue = attributes.get("fraction").toString();
        } else {
            fractionValue = properties.get(fractionKey);
        }
        validationParameters.put(ValidationParametersKey.INTEGER, integerValue);
        validationParameters.put(ValidationParametersKey.FRACTION, fractionValue);
        return validationParameters;
    }

    /**
     * For Range, Length and Size validators defined in Hibernate and Java
     */
    private Map<ValidationParametersKey, String> getNonTosanValidatorParameters(Map<Object, Object> attributes) {
        Map<ValidationParametersKey, String> validationParameters = new HashMap<>();
        String minValue = attributes.get("min").toString();
        String maxValue = attributes.get("max").toString();
        validationParameters.put(ValidationParametersKey.MIN, minValue);
        validationParameters.put(ValidationParametersKey.MAX, maxValue);
        return validationParameters;
    }

    private Map<ValidationParametersKey, Object> getMinMaxValidatorParameters(Map<Object, Object> attributes, ValidationParametersKey key) {
        Map<ValidationParametersKey, Object> validationParameters = new HashMap<>();
        String keyValue = (String) attributes.get("keyValue");
        Boolean checkEquality = Boolean.valueOf(attributes.get("checkEquality").toString());
        String value;
        if (keyValue.isEmpty()) {
            value = attributes.get("value").toString();
        } else {
            value = properties.get(keyValue);
        }
        validationParameters.put(key, value);
        validationParameters.put(ValidationParametersKey.CHECK_EQUALITY, checkEquality);
        return validationParameters;
    }

    private void putValidationViolationInformationMapEntry(String businessType, String fieldName, ValidationConstraintViolationInfo violationInfo,
                                                           Map<String, List<ValidationViolationInfo>> validationViolationInformations) {
        String name = fieldName.toLowerCase();
        ValidationViolationInfo validationViolationInfo = new ValidationViolationInfo();
        if (!ignoredParameters.contains(name)) {
            if (semiIgnoredParameters.contains(name)) {
                if (name.equals("pan")) {
                    validationViolationInfo.setInvalidValue(Encryption.getPanEncryptedString(violationInfo.getInvalidValue()));
                } else {
                    validationViolationInfo.setInvalidValue(Encryption.makeSemiEncryptString(violationInfo.getInvalidValue()));
                }
            } else {
                validationViolationInfo.setInvalidValue(violationInfo.getInvalidValue());
            }
        } else {
            validationViolationInfo.setInvalidValue("*ENCRYPTED*");
        }
        validationViolationInfo.setBusinessType(businessType);
        validationViolationInfo.setPropertyPath(violationInfo.getPropertyPath());
        validationViolationInfo.setValidationAnnotations(violationInfo.getValidationAnnotations());

        if (validationViolationInformations.containsKey(fieldName)) {
            validationViolationInformations.get(fieldName).add(validationViolationInfo);
        } else {
            List<ValidationViolationInfo> violationInformation = new ArrayList<>();
            violationInformation.add(validationViolationInfo);
            validationViolationInformations.put(fieldName, violationInformation);
        }
    }

    private String getBusinessType(Class rootBeanClass, String propertyPath, boolean validMapOrCollection) {
        FieldDescriptorExtractor fieldDescriptorExtractor =
                FieldDescriptorExtractorFactory.getInstance(propertyPath, validMapOrCollection);
        return fieldDescriptorExtractor.getBusinessTypeParameter(rootBeanClass, propertyPath);
    }

    private String getFieldName(String propertyPath, boolean validMapOrCollection) {
        if (validMapOrCollection) {
            String[] propertyPathParts = propertyPath.split("\\.");
            int size = propertyPathParts.length;
            return propertyPathParts[size - 1];
        } else
            return propertyPath;
    }

    private String formatDate(String invalidValue) {
        DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        Date date;
        try {
            date = format.parse(invalidValue);
            invalidValue = Iso8601DateTimeFormatter.format(date);
        } catch (ParseException e) {
            logger.error("Can not parse this time", e);
        }
        return invalidValue;
    }

    private ValidationAnnotation validateEnumParameter(String annotation) {
        ValidationAnnotation validationAnnotation;
        if (EnumUtils.isValidEnum(ValidationAnnotation.class, annotation)) {
            validationAnnotation = ValidationAnnotation.valueOf(annotation);
        } else {
            logger.info("The annotation is not Exist, Using OTHER instead.");
            validationAnnotation = ValidationAnnotation.OTHER;
        }
        return validationAnnotation;
    }

    private String getBusinessTypeParameter(ConstraintViolation<Object> invalidValue, Class annotationClass) {
        String businessType = null;
        if (!annotationClass.equals(Expression.class)) {
            if (!invalidValue.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().equals("ValidCollection")
                    && !invalidValue.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().equals("ValidMap")) {
                return invalidValue.getConstraintDescriptor().getAttributes().get("businessType").toString();
            } else {
                String[] parameters = invalidValue.getMessage().split("\\n");
                for (String parameter : parameters) {
                    if (parameter.contains("businessType")) {
                        businessType = parameter.substring(parameter.indexOf("=") + 1);
                    }
                }
                return businessType;
            }
        } else {
            return "DTO";
        }
    }

    private static class ViolationInfo {
        private final String violationMessage;
        private final Map<ValidationParametersKey, Object> validationParameters;

        public ViolationInfo(String violationMessage, Map<ValidationParametersKey, Object> validationParameters) {
            this.violationMessage = violationMessage;
            this.validationParameters = validationParameters;
        }

        private String getViolationMessage() {
            return violationMessage;
        }

        private Map<ValidationParametersKey, Object> getValidationParameters() {
            return validationParameters;
        }
    }
}
