package io.meshware.rule.core.wrapper;

import io.meshware.rule.core.filter.AbstractFilter;

import java.util.Map;

/**
 * Equal Wrapper
 *
 * @param <T>
 * @author Zhiguo.Chen
 */
public interface EqualWrapper<T> extends Wrapper<T, AbstractWrapper> {

    EqualWrapper eq(String filterCode, Object value);

    EqualWrapper eq(Class<AbstractFilter> filterClass, Object value);

    Map<Class<AbstractFilter>, Object> getEqConditions();

    Object getEqConditions(Class filterClass);
}
