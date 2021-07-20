package io.meshware.rule.core.wrapper;

import java.util.List;

/**
 * Default Filter Wrapper
 *
 * @author Zhiguo.Chen
 */
public class DefaultFilterWrapper<T> extends AbstractWrapper<T, DefaultFilterWrapper> {

    public DefaultFilterWrapper() {
    }

    public DefaultFilterWrapper(List<T> entity) {
        setEntities(entity);
    }

    @Override
    public DefaultFilterWrapper and() {
        return this;
    }

}
