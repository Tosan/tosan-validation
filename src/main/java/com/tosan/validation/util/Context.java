package com.tosan.validation.util;

import jakarta.el.*;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shamsolebad
 */
public class Context extends ELContext {

    private final Map<String, Object> variables = new HashMap<>();
    private final CompositeELResolver resolver;
    private final VariableMapper variableMapper = new VariableMapperImpl();
    private final FunctionMapper functionMapper = new FunctionMapperImpl();

    public Context() {
        resolver = new CompositeELResolver();
        resolver.add(new ELResolverImpl());
        resolver.add(new MapELResolver());
        resolver.add(new ListELResolver());
        resolver.add(new ArrayELResolver());
        // resolver.add(new ResourceBundleELResolver());
        resolver.add(new BeanELResolver());
    }

    public void put(String variable, Object obj) {
        variables.put(variable, obj);
    }

    @Override
    public ELResolver getELResolver() {
        return resolver;
    }

    @Override
    public VariableMapper getVariableMapper() {
        return variableMapper;
    }

    @Override
    public FunctionMapper getFunctionMapper() {
        return functionMapper;
    }

    private class ELResolverImpl extends ELResolver {

        @Override
        public Object getValue(ELContext context, Object base, Object property) {
            if (base == null) {
                Object result = variables.get(property.toString());
                if (result != null) {
                    context.setPropertyResolved(true);
                    return result;
                }
            }
            return null;
        }

        @Override
        public Class<?> getCommonPropertyType(ELContext arg0, Object arg1) {
            return null;
        }

        @Override
        public Class<?> getType(ELContext arg0, Object arg1, Object arg2) {
            return null;
        }

        @Override
        public boolean isReadOnly(ELContext arg0, Object arg1, Object arg2) {
            return false;
        }

        @Override
        public void setValue(ELContext arg0, Object arg1, Object arg2,
                             Object arg3) {
        }
    }

    private static class VariableMapperImpl extends VariableMapper {
        public ValueExpression resolveVariable(String s) {
            return null;
        }

        public ValueExpression setVariable(String s,
                                           ValueExpression valueExpression) {
            return valueExpression;
        }
    }

    private static class FunctionMapperImpl extends FunctionMapper {
        public Method resolveFunction(String s, String s1) {
            return null;
        }
    }
}
