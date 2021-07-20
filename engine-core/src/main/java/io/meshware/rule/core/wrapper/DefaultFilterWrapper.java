package io.meshware.rule.core.wrapper;

import java.util.List;

/**
 * Default Filter Wrapper
 *
 * @author Zhiguo.Chen
 */
public class DefaultFilterWrapper<T> extends AbstractWrapper<T> {

    public DefaultFilterWrapper() {
    }

    public DefaultFilterWrapper(List<T> entity) {
        setEntities(entity);
    }

    @Override
    public DefaultFilterWrapper<T> and() {
        return this;
    }

}
