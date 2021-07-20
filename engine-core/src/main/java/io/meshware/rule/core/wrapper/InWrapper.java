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
public interface InWrapper<T> extends Wrapper<T, AbstractWrapper> {

    InWrapper in(String filterCode, Object value);

    InWrapper in(String filterCode, @NonNull List<Object> values);

    InWrapper in(Class<AbstractFilter> filterClass, Object value);

    InWrapper in(Class<AbstractFilter> filterClass, @NonNull List<Object> values);

    Multimap<Class<AbstractFilter>, Object> getInConditions();

    List getInConditions(Class filterClass);

}
