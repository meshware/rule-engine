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
public interface OrWrapper<T> extends Wrapper<T, AbstractWrapper> {

    OrWrapper or(String filterCode, Object value);

    OrWrapper or(String filterCode, @NonNull List<Object> values);

    OrWrapper or(Class<AbstractFilter> filterClass, Object value);

    OrWrapper or(Class<AbstractFilter> filterClass, @NonNull List<Object> values);

    Multimap<Class<AbstractFilter>, Object> getOrConditions();

    List getOrConditions(Class filterClass);
}
