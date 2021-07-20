package io.meshware.rule.core.wrapper;

import io.meshware.rule.core.filter.AbstractFilter;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Between Wrapper
 *
 * @param <T>
 * @author Zhiguo.Chen
 */
public interface BetweenWrapper<T> extends Wrapper<T, AbstractWrapper<T>> {

    BetweenWrapper<T> lt(String filterCode, Object value);

    BetweenWrapper<T> lt(String filterCode, String groupName, Object value);

    BetweenWrapper<T> lt(Class<AbstractFilter<T>> filterClass, Object value);

    BetweenWrapper<T> lt(Class<AbstractFilter<T>> filterClass, String groupName, Object value);

    BetweenWrapper<T> gt(String filterCode, Object value);

    BetweenWrapper<T> gt(String filterCode, String groupName, Object value);

    BetweenWrapper<T> gt(Class<AbstractFilter<T>> filterClass, Object value);

    BetweenWrapper<T> gt(Class<AbstractFilter<T>> filterClass, String groupName, Object value);

    BetweenWrapper<T> between(String filterCode, BetweenCondition<T> value);

    BetweenWrapper<T> between(Class<AbstractFilter<T>> filterClass, BetweenCondition<T> value);

    BetweenWrapper<T> range(String filterCode, RangeCondition<T> value);

    BetweenWrapper<T> range(Class<AbstractFilter<T>> filterClass, RangeCondition<T> value);

    Map<Class<AbstractFilter<T>>, Map<String, BetweenCondition<T>>> getBetweenConditions();

    Map<String, BetweenCondition<T>> getBetweenConditions(Class filterClass);

    Map<Class<AbstractFilter<T>>, Map<String, RangeCondition<T>>> getRangeConditions();

    Map<String, RangeCondition<T>> getRangeConditions(Class filterClass);

    @Data
    @Accessors(chain = true)
    class BetweenCondition<T> implements Serializable {

        private String groupName;

        private T ltValue;

        private T gtValue;
    }

    @Data
    @Accessors(chain = true)
    class RangeCondition<T> implements Serializable {

        private String groupName;

        private T ltValue;

        private T gtValue;

        private List<BetweenCondition<T>> list;
    }
}
