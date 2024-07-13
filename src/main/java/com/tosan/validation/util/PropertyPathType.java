package com.tosan.validation.util;

/**
 * نوع proprtyPath هر گره از درخت مربوط به اطلاعات validation را نشان می‌دهد
 *
 * @author Boshra Taheri
 * @see ValidationConstraintViolationInfoTreeGenerator
 * @see ValidationConstraintViolationInfo
 * @since 8/21/13
 */
public enum PropertyPathType {
    /**
     * نام فیلدی که مقدار آن نامعتبر است
     */
    FIELD_NAME,
    /**
     * مقدار یک فیلد که یا کلید یک Map است، یا یکی از المان‌های یک Collection
     */
    VALUE,
    /**
     * اندیس المان نامعتبر در یک لیست، وقتی که المان از نوع مرکب(composite) است
     */
    INDEX,
    /**
     * مقدار hash المان نامعتبر در یک Collection بدون ترتیب، وقتی که المان از نوع مرکب(composite) است
     */
    HASH,
    /**
     * این نوع نشان می‌دهد که در ادامه‌ی درخت، مقادیر نامعتبر مربوط به کلیدهای Map قرار دارند
     */
    MAP_KEY,
    /**
     * این نوع نشان می‌دهد که در ادامه‌ی درخت، مقادیر نامعتبر مربوط به مقادیر Map قرار دارند
     */
    MAP_VALUE
}
