package com.tosan.validation.util;

import jakarta.el.ELContext;
import jakarta.el.ExpressionFactory;
import jakarta.el.ValueExpression;
import org.apache.commons.lang3.StringUtils;

/**
 * @author shamsolebad
 * @since Sep 10, 2009
 */
public class ExpressionUtil {

    static {
        if (StringUtils.isNotBlank(getFactoryClass())) {
            setFactoryClass("de.odysseus.el.ExpressionFactoryImpl");
        }
    }

    private static void setFactoryClass(String factoryClass) {
        System.setProperty("jakarta.el.ExpressionFactory", factoryClass);
    }

    private static String getFactoryClass() {
        return System.getProperty("jakarta.el.ExpressionFactory");
    }

    private static ExpressionFactory getFactory() {
        return ExpressionFactory.newInstance();
    }

    private static ValueExpression createValueExpression(ELContext context, String expression, Class type) {
        return getFactory().createValueExpression(context, expression, type);
    }

    public static <T> T getValue(ELContext context, String expression, Class<T> type) {
        ValueExpression exp = createValueExpression(context, expression, type);
        // noinspection unchecked
        return (T) exp.getValue(context);
    }

    public static Object getValue(ELContext context, String expression) {
        return getValue(context, expression, Object.class);
    }
}
