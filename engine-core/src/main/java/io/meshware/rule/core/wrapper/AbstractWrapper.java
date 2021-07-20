package io.meshware.rule.core.wrapper;

import com.google.common.collect.*;
import io.meshware.rule.core.enums.FilterEnum;
import io.meshware.rule.core.filter.AbstractFilter;
import lombok.Data;
import lombok.NonNull;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Abstract Wrapper
 *
 * @author Zhiguo.Chen
 */
@Slf4j
@Data
public abstract class AbstractWrapper<T>
        implements Wrapper<T, AbstractWrapper<T>>, EqualWrapper<T>, InWrapper<T>, OrWrapper<T>, NinWrapper<T>, BetweenWrapper<T> {

    /**
     * Entities instance
     */
    protected List<T> entities;

    private Map<Class<AbstractFilter<T>>, Object> eqConditions = Maps.newConcurrentMap();

    private Multimap<Class<AbstractFilter<T>>, Object> inConditions = ArrayListMultimap.create();

    private Multimap<Class<AbstractFilter<T>>, Object> ninConditions = ArrayListMultimap.create();

    private Multimap<Class<AbstractFilter<T>>, Object> orConditions = ArrayListMultimap.create();

    private Map<Class<AbstractFilter<T>>, Map<String, BetweenCondition<T>>> betweenConditions = Maps.newConcurrentMap();

    private Map<Class<AbstractFilter<T>>, Map<String, RangeCondition<T>>> rangeConditions = Maps.newConcurrentMap();

    /**
     * 增加被过滤的实体对象
     *
     * @param entity entity
     */
    @Override
    @Synchronized
    public void addEntity(T entity) {
        if (CollectionUtils.isEmpty(entities)) {
            entities = Lists.newArrayList();
        }
        entities.add(entity);
    }

    @Override
    public void loadCondition(AbstractWrapper<T> wrapper) {
        this.eqConditions = wrapper.getEqConditions();
        this.inConditions = wrapper.getInConditions();
        this.ninConditions = wrapper.getNinConditions();
        this.orConditions = wrapper.getOrConditions();
        this.betweenConditions = wrapper.getBetweenConditions();
        this.rangeConditions = wrapper.getRangeConditions();
    }

    @Override
    public AbstractWrapper<T> and() {
        return (AbstractWrapper<T>) this;
    }

    @Override
    public EqualWrapper<T> eq(String filterCode, Object value) {
        try {
            eqConditions.put(FilterEnum.getByCode(filterCode).getClazz(), value);
        } catch (Exception e) {
            log.error("Add condition value error, filterCode={}", filterCode);
        }
        return this;
    }

    @Override
    public EqualWrapper<T> eq(Class<AbstractFilter<T>> filterClass, Object value) {
        eqConditions.put(filterClass, value);
        return this;
    }

    @Override
    public InWrapper<T> in(String filterCode, Object value) {
        try {
            if (value instanceof List) {
                in(filterCode, (List) value);
            } else {
                inConditions.put(FilterEnum.getByCode(filterCode).getClazz(), value);
            }
        } catch (Exception e) {
            log.error("Add condition value error, filterCode={}", filterCode);
        }
        return this;
    }

    @Override
    public InWrapper<T> in(String filterCode, @NonNull List<Object> values) {
        try {
            Class<AbstractFilter<T>> filterClass = FilterEnum.getByCode(filterCode).getClazz();
            for (Object o : values) {
                in(filterClass, o);
            }
        } catch (Exception e) {
            log.error("Add condition value error, filterCode={}", filterCode);
        }
        return this;
    }

    @Override
    public InWrapper<T> in(Class<AbstractFilter<T>> filterClass, Object value) {
        if (value instanceof List) {
            in(filterClass, (List) value);
        } else {
            inConditions.put(filterClass, value);
        }
        return this;
    }

    @Override
    public InWrapper<T> in(Class<AbstractFilter<T>> filterClass, @NonNull List<Object> values) {
        for (Object o : values) {
            in(filterClass, o);
        }
        return this;
    }

    @Override
    public NinWrapper<T> nin(String filterCode, Object value) {
        try {
            if (value instanceof List) {
                nin(filterCode, (List) value);
            } else {
                ninConditions.put(FilterEnum.getByCode(filterCode).getClazz(), value);
            }
        } catch (Exception e) {
            log.error("Add condition value error, filterCode={}", filterCode);
        }
        return this;
    }

    @Override
    public NinWrapper<T> nin(String filterCode, @NonNull List<Object> values) {
        try {
            Class<AbstractFilter<T>> filterClass = FilterEnum.getByCode(filterCode).getClazz();
            for (Object o : values) {
                nin(filterClass, o);
            }
        } catch (Exception e) {
            log.error("Add condition value error, filterCode={}", filterCode);
        }
        return this;
    }

    @Override
    public NinWrapper<T> nin(Class<AbstractFilter<T>> filterClass, Object value) {
        if (value instanceof List) {
            nin(filterClass, (List) value);
        } else {
            ninConditions.put(filterClass, value);
        }
        return this;
    }

    @Override
    public NinWrapper<T> nin(Class<AbstractFilter<T>> filterClass, @NonNull List<Object> values) {
        for (Object o : values) {
            nin(filterClass, o);
        }
        return this;
    }

    @Override
    public OrWrapper<T> or(String filterCode, Object value) {
        try {
            if (value instanceof List) {
                or(filterCode, (List) value);
            } else {
                orConditions.put(FilterEnum.getByCode(filterCode).getClazz(), value);
            }
        } catch (Exception e) {
            log.error("Add condition value error, filterCode={}", filterCode);
        }
        return this;
    }

    @Override
    public OrWrapper<T> or(String filterCode, @NonNull List<Object> values) {
        try {
            Class<AbstractFilter<T>> filterClass = FilterEnum.getByCode(filterCode).getClazz();
            for (Object o : values) {
                or(filterClass, o);
            }
        } catch (Exception e) {
            log.error("Add condition value error, filterCode={}", filterCode);
        }
        return this;
    }

    @Override
    public OrWrapper<T> or(Class<AbstractFilter<T>> filterClass, Object value) {
        if (value instanceof List) {
            or(filterClass, (List) value);
        } else {
            orConditions.put(filterClass, value);
        }
        return this;
    }

    @Override
    public OrWrapper<T> or(Class<AbstractFilter<T>> filterClass, @NonNull List<Object> values) {
        for (Object o : values) {
            or(filterClass, o);
        }
        return this;
    }

    @Override
    public Set<Class<AbstractFilter<T>>> getFilters() {
        Set<Class<AbstractFilter<T>>> filterEnums = Sets.newConcurrentHashSet();
        filterEnums.addAll(eqConditions.keySet());
        filterEnums.addAll(orConditions.keySet());
        filterEnums.addAll(inConditions.keySet());
        filterEnums.addAll(ninConditions.keySet());
        filterEnums.addAll(betweenConditions.keySet());
        filterEnums.addAll(rangeConditions.keySet());
        return filterEnums;
    }

    @Override
    public Object getEqConditions(Class filterClass) {
        return eqConditions.get(filterClass);
    }

    @Override
    public List getInConditions(Class filterClass) {
        return (List) inConditions.get(filterClass);
    }

    @Override
    public List getNinConditions(Class filterClass) {
        return (List) ninConditions.get(filterClass);
    }

    @Override
    public List getOrConditions(Class filterClass) {
        return (List) orConditions.get(filterClass);
    }

    @Override
    public BetweenWrapper lt(String filterCode, Object value) {
        lt(filterCode, "default", value);
        return this;
    }

    @Override
    public BetweenWrapper<T> lt(String filterCode, String groupName, Object value) {
        try {
            Class<AbstractFilter<T>> filterClass = FilterEnum.getByCode(filterCode).getClazz();
            lt(filterClass, groupName, value);
        } catch (Exception e) {
            log.error("Add condition value error, filterCode={}", filterCode);
        }
        return this;
    }

    @Override
    public BetweenWrapper<T> lt(Class<AbstractFilter<T>> filterClass, Object value) {
        lt(filterClass, "default", value);
        return this;
    }

    @Override
    public BetweenWrapper<T> lt(Class<AbstractFilter<T>> filterClass, String groupName, Object value) {
        try {
            Map<String, BetweenCondition<T>> betweenConditionMap = betweenConditions.get(filterClass);
            if (Objects.isNull(betweenConditionMap)) {
                betweenConditionMap = Maps.newHashMap();
            }
            BetweenCondition<T> betweenCondition = betweenConditionMap.get(groupName);
            if (Objects.isNull(betweenCondition)) {
                betweenCondition = new BetweenCondition<T>();
            }
            betweenCondition.setGroupName(groupName);
            betweenCondition.setLtValue((T) value);

            betweenConditionMap.put(betweenCondition.getGroupName(), betweenCondition);
            betweenConditions.put(filterClass, betweenConditionMap);
        } catch (Exception e) {
            log.error("Add condition value error, filter={}", filterClass.getCanonicalName());
        }
        return this;
    }

    @Override
    public BetweenWrapper<T> gt(String filterCode, Object value) {
        gt(filterCode, "default", value);
        return this;
    }

    @Override
    public BetweenWrapper<T> gt(String filterCode, String groupName, Object value) {
        try {
            Class<AbstractFilter<T>> filterClass = FilterEnum.getByCode(filterCode).getClazz();
            gt(filterClass, groupName, value);
        } catch (Exception e) {
            log.error("Add condition value error, filterCode={}", filterCode);
        }
        return this;
    }

    @Override
    public BetweenWrapper<T> gt(Class<AbstractFilter<T>> filterClass, Object value) {
        gt(filterClass, "default", value);
        return this;
    }

    @Override
    public BetweenWrapper<T> gt(Class<AbstractFilter<T>> filterClass, String groupName, Object value) {
        try {
            Map<String, BetweenCondition<T>> betweenConditionMap = betweenConditions.get(filterClass);
            if (Objects.isNull(betweenConditionMap)) {
                betweenConditionMap = Maps.newHashMap();
            }
            BetweenCondition<T> betweenCondition = betweenConditionMap.get(groupName);
            if (Objects.isNull(betweenCondition)) {
                betweenCondition = new BetweenCondition();
            }
            betweenCondition.setGroupName(groupName);
            betweenCondition.setGtValue((T) value);

            betweenConditionMap.put(betweenCondition.getGroupName(), betweenCondition);
            betweenConditions.put(filterClass, betweenConditionMap);
        } catch (Exception e) {
            log.error("Add condition value error, filter={}", filterClass.getCanonicalName());
        }
        return this;
    }

    @Override
    public BetweenWrapper<T> between(String filterCode, BetweenCondition<T> value) {
        Class<AbstractFilter<T>> filterClass = FilterEnum.getByCode(filterCode).getClazz();
        between(filterClass, value);
        return this;
    }

    @Override
    public BetweenWrapper<T> between(Class<AbstractFilter<T>> filterClass, BetweenCondition<T> value) {
        try {
            Map<String, BetweenCondition<T>> betweenConditionMap = betweenConditions.get(filterClass);
            if (Objects.isNull(betweenConditionMap)) {
                betweenConditionMap = Maps.newHashMap();
            }
            betweenConditionMap.put(value.getGroupName(), value);
            betweenConditions.put(filterClass, betweenConditionMap);
        } catch (Exception e) {
            log.error("Add condition value error, filter={}", filterClass.getCanonicalName());
        }
        return this;
    }

    @Override
    public Map<String, BetweenCondition<T>> getBetweenConditions(Class filterClass) {
        return betweenConditions.get(filterClass);
    }

    @Override
    public BetweenWrapper<T> range(String filterCode, RangeCondition<T> value) {
        Class<AbstractFilter<T>> filterClass = FilterEnum.getByCode(filterCode).getClazz();
        range(filterClass, value);
        return this;
    }

    @Override
    public BetweenWrapper<T> range(Class<AbstractFilter<T>> filterClass, RangeCondition<T> value) {
        try {
            Map<String, RangeCondition<T>> rangeConditionMap = rangeConditions.get(filterClass);
            if (Objects.isNull(rangeConditionMap)) {
                rangeConditionMap = Maps.newHashMap();
            }
            rangeConditionMap.put(value.getGroupName(), value);
            rangeConditions.put(filterClass, rangeConditionMap);
        } catch (Exception e) {
            log.error("Add condition value error, filter={}", filterClass.getCanonicalName());
        }
        return this;
    }

    @Override
    public Map<String, RangeCondition<T>> getRangeConditions(Class filterClass) {
        return rangeConditions.get(filterClass);
    }
}
