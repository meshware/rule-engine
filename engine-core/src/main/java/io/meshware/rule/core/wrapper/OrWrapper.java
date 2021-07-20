package io.meshware.rule.core.wrapper;

import com.google.common.collect.Multimap;
import io.meshware.rule.core.filter.AbstractFilter;
import lombok.NonNull;

import java.util.List;

/**
 * Or Wrapper
 *
 * @param <T>
 * @author Zhiguo.Chen
 */
public interface OrWrapper<T> extends Wrapper<T, AbstractWrapper<T>> {

    OrWrapper<T> or(String filterCode, Object value);

    OrWrapper<T> or(String filterCode, @NonNull List<Object> values);

    OrWrapper<T> or(Class<AbstractFilter<T>> filterClass, Object value);

    OrWrapper<T> or(Class<AbstractFilter<T>> filterClass, @NonNull List<Object> values);

    Multimap<Class<AbstractFilter<T>>, Object> getOrConditions();

    List getOrConditions(Class filterClass);
}
