package io.meshware.rule.core.wrapper;

import io.meshware.rule.core.filter.AbstractFilter;

import java.util.List;
import java.util.Set;

/**
 * Wrapper interface
 *
 * @author Zhiguo.Chen
 */
public interface Wrapper<T, E extends Wrapper<T, E>> {

    /**
     * 增加被过滤的实体对象
     *
     * @param entity Entity
     */
    void addEntity(T entity);

    /**
     * <p>
     * 实体对象（子类实现）
     * </p>
     *
     * @return 泛型 T
     */
    List<T> getEntities();

    Set<Class<AbstractFilter<T>>> getFilters();

    //List<AbstractFilter> getCustomFilters();

    void loadCondition(E wrapper);

    E and();

}
