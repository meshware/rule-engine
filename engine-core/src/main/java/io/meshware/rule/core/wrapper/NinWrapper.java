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
public interface NinWrapper<T> extends Wrapper<T, AbstractWrapper> {

    NinWrapper nin(String filterCode, Object value);

    NinWrapper nin(String filterCode, @NonNull List<Object> values);

    NinWrapper nin(Class<AbstractFilter> filterClass, Object value);

    NinWrapper nin(Class<AbstractFilter> filterClass, @NonNull List<Object> values);

    Multimap<Class<AbstractFilter>, Object> getNinConditions();

    List getNinConditions(Class filterClass);
}
