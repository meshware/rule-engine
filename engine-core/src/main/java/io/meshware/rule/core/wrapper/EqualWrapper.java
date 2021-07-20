package io.meshware.rule.core.wrapper;

import io.meshware.rule.core.filter.AbstractFilter;

import java.util.Map;

/**
 * Equal Wrapper
 *
 * @param <T>
 * @author Zhiguo.Chen
 */
public interface EqualWrapper<T> extends Wrapper<T, AbstractWrapper<T>> {

    EqualWrapper<T> eq(String filterCode, Object value);

    EqualWrapper<T> eq(Class<AbstractFilter<T>> filterClass, Object value);

    Map<Class<AbstractFilter<T>>, Object> getEqConditions();

    Object getEqConditions(Class filterClass);
}
