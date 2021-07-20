package io.meshware.rule.core.wrapper;

import com.google.common.collect.Multimap;
import io.meshware.rule.core.filter.AbstractFilter;
import lombok.NonNull;

import java.util.List;

/**
 * In Wrapper
 *
 * @param <T>
 * @author Zhiguo.Chen
 */
public interface InWrapper<T> extends Wrapper<T, AbstractWrapper<T>> {

    InWrapper<T> in(String filterCode, Object value);

    InWrapper<T> in(String filterCode, @NonNull List<Object> values);

    InWrapper<T> in(Class<AbstractFilter<T>> filterClass, Object value);

    InWrapper<T> in(Class<AbstractFilter<T>> filterClass, @NonNull List<Object> values);

    Multimap<Class<AbstractFilter<T>>, Object> getInConditions();

    List getInConditions(Class filterClass);

}
