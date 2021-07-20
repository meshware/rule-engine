package io.meshware.rule.core.wrapper;

import com.google.common.collect.Multimap;
import io.meshware.rule.core.filter.AbstractFilter;
import lombok.NonNull;

import java.util.List;

/**
 * Not in Wrapper
 *
 * @param <T>
 * @author Zhiguo.Chen
 */
public interface NinWrapper<T> extends Wrapper<T, AbstractWrapper<T>> {

    NinWrapper<T> nin(String filterCode, Object value);

    NinWrapper<T> nin(String filterCode, @NonNull List<Object> values);

    NinWrapper<T> nin(Class<AbstractFilter<T>> filterClass, Object value);

    NinWrapper<T> nin(Class<AbstractFilter<T>> filterClass, @NonNull List<Object> values);

    Multimap<Class<AbstractFilter<T>>, Object> getNinConditions();

    List getNinConditions(Class filterClass);
}
