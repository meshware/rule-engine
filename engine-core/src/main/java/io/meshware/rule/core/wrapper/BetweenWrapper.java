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
public interface BetweenWrapper<T> extends Wrapper<T, AbstractWrapper> {

    BetweenWrapper lt(String filterCode, Object value);

    BetweenWrapper lt(String filterCode, String groupName, Object value);

    BetweenWrapper lt(Class<AbstractFilter> filterClass, Object value);

    BetweenWrapper lt(Class<AbstractFilter> filterClass, String groupName, Object value);

    BetweenWrapper gt(String filterCode, Object value);

    BetweenWrapper gt(String filterCode, String groupName, Object value);

    BetweenWrapper gt(Class<AbstractFilter> filterClass, Object value);

    BetweenWrapper gt(Class<AbstractFilter> filterClass, String groupName, Object value);

    BetweenWrapper between(String filterCode, BetweenCondition value);

    BetweenWrapper between(Class<AbstractFilter> filterClass, BetweenCondition value);

    BetweenWrapper range(String filterCode, RangeCondition value);

    BetweenWrapper range(Class<AbstractFilter> filterClass, RangeCondition value);

    Map<Class<AbstractFilter>, Map<String, BetweenCondition>> getBetweenConditions();

    Map<String, BetweenCondition> getBetweenConditions(Class filterClass);

    Map<Class<AbstractFilter>, Map<String, RangeCondition>> getRangeConditions();

    Map<String, RangeCondition> getRangeConditions(Class filterClass);

    @Data
    @Accessors(chain = true)
    class BetweenCondition<E> implements Serializable {

        private String groupName;

        private E ltValue;

        private E gtValue;
    }

    @Data
    @Accessors(chain = true)
    class RangeCondition<E> implements Serializable {

        private String groupName;

        private E ltValue;

        private E gtValue;

        private List<BetweenCondition<E>> list;
    }
}
