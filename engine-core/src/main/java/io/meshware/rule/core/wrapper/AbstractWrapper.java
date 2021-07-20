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
public abstract class AbstractWrapper<T, E extends AbstractWrapper> implements Wrapper<T, AbstractWrapper>,
        EqualWrapper<T>, InWrapper<T>, OrWrapper<T>, NinWrapper<T>, BetweenWrapper<T> {

    /**
     * Entities instance
     */
    protected List<T> entities;

    private Map<Class<AbstractFilter>, Object> eqConditions = Maps.newConcurrentMap();

    private Multimap<Class<AbstractFilter>, Object> inConditions = ArrayListMultimap.create();

    private Multimap<Class<AbstractFilter>, Object> ninConditions = ArrayListMultimap.create();

    private Multimap<Class<AbstractFilter>, Object> orConditions = ArrayListMultimap.create();

    private Map<Class<AbstractFilter>, Map<String, BetweenCondition>> betweenConditions = Maps.newConcurrentMap();

    private Map<Class<AbstractFilter>, Map<String, RangeCondition>> rangeConditions = Maps.newConcurrentMap();

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
    public void loadCondition(AbstractWrapper wrapper) {
        this.eqConditions = wrapper.getEqConditions();
        this.inConditions = wrapper.getInConditions();
        this.ninConditions = wrapper.getNinConditions();
        this.orConditions = wrapper.getOrConditions();
        this.betweenConditions = wrapper.getBetweenConditions();
        this.rangeConditions = wrapper.getRangeConditions();
    }

    @Override
    public E and() {
        return (E) this;
    }

    @Override
    public EqualWrapper eq(String filterCode, Object value) {
        try {
            eqConditions.put(FilterEnum.getByCode(filterCode).getClazz(), value);
        } catch (Exception e) {
            log.error("Add condition value error, filterCode={}", filterCode);
        }
        return this;
    }

    @Override
    public EqualWrapper eq(Class<AbstractFilter> filterClass, Object value) {
        eqConditions.put(filterClass, value);
        return this;
    }

    @Override
    public InWrapper in(String filterCode, Object value) {
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
    public InWrapper in(String filterCode, @NonNull List<Object> values) {
        try {
            Class<AbstractFilter> filterClass = FilterEnum.getByCode(filterCode).getClazz();
            for (Object o : values) {
                in(filterClass, o);
            }
        } catch (Exception e) {
            log.error("Add condition value error, filterCode={}", filterCode);
        }
        return this;
    }

    @Override
    public InWrapper in(Class<AbstractFilter> filterClass, Object value) {
        if (value instanceof List) {
            in(filterClass, (List) value);
        } else {
            inConditions.put(filterClass, value);
        }
        return this;
    }

    @Override
    public InWrapper in(Class<AbstractFilter> filterClass, @NonNull List<Object> values) {
        for (Object o : values) {
            in(filterClass, o);
        }
        return this;
    }

    @Override
    public NinWrapper nin(String filterCode, Object value) {
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
    public NinWrapper nin(String filterCode, @NonNull List<Object> values) {
        try {
            Class<AbstractFilter> filterClass = FilterEnum.getByCode(filterCode).getClazz();
            for (Object o : values) {
                nin(filterClass, o);
            }
        } catch (Exception e) {
            log.error("Add condition value error, filterCode={}", filterCode);
        }
        return this;
    }

    @Override
    public NinWrapper nin(Class<AbstractFilter> filterClass, Object value) {
        if (value instanceof List) {
            nin(filterClass, (List) value);
        } else {
            ninConditions.put(filterClass, value);
        }
        return this;
    }

    @Override
    public NinWrapper nin(Class<AbstractFilter> filterClass, @NonNull List<Object> values) {
        for (Object o : values) {
            nin(filterClass, o);
        }
        return this;
    }

    @Override
    public OrWrapper or(String filterCode, Object value) {
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
    public OrWrapper or(String filterCode, @NonNull List<Object> values) {
        try {
            Class<AbstractFilter> filterClass = FilterEnum.getByCode(filterCode).getClazz();
            for (Object o : values) {
                or(filterClass, o);
            }
        } catch (Exception e) {
            log.error("Add condition value error, filterCode={}", filterCode);
        }
        return this;
    }

    @Override
    public OrWrapper or(Class<AbstractFilter> filterClass, Object value) {
        if (value instanceof List) {
            or(filterClass, (List) value);
        } else {
            orConditions.put(filterClass, value);
        }
        return this;
    }

    @Override
    public OrWrapper or(Class<AbstractFilter> filterClass, @NonNull List<Object> values) {
        for (Object o : values) {
            or(filterClass, o);
        }
        return this;
    }

    @Override
    public Set<Class<AbstractFilter>> getFilters() {
        Set<Class<AbstractFilter>> filterEnums = Sets.newConcurrentHashSet();
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
    public BetweenWrapper lt(String filterCode, String groupName, Object value) {
        try {
            Class<AbstractFilter> filterClass = FilterEnum.getByCode(filterCode).getClazz();
            lt(filterClass, groupName, value);
        } catch (Exception e) {
            log.error("Add condition value error, filterCode={}", filterCode);
        }
        return this;
    }

    @Override
    public BetweenWrapper lt(Class<AbstractFilter> filterClass, Object value) {
        lt(filterClass, "default", value);
        return this;
    }

    @Override
    public BetweenWrapper lt(Class<AbstractFilter> filterClass, String groupName, Object value) {
        try {
            Map<String, BetweenCondition> betweenConditionMap = betweenConditions.get(filterClass);
            if (Objects.isNull(betweenConditionMap)) {
                betweenConditionMap = Maps.newHashMap();
            }
            BetweenCondition betweenCondition = betweenConditionMap.get(groupName);
            if (Objects.isNull(betweenCondition)) {
                betweenCondition = new BetweenCondition();
            }
            betweenCondition.setGroupName(groupName);
            betweenCondition.setLtValue(value);

            betweenConditionMap.put(betweenCondition.getGroupName(), betweenCondition);
            betweenConditions.put(filterClass, betweenConditionMap);
        } catch (Exception e) {
            log.error("Add condition value error, filter={}", filterClass.getCanonicalName());
        }
        return this;
    }

    @Override
    public BetweenWrapper gt(String filterCode, Object value) {
        gt(filterCode, "default", value);
        return this;
    }

    @Override
    public BetweenWrapper gt(String filterCode, String groupName, Object value) {
        try {
            Class<AbstractFilter> filterClass = FilterEnum.getByCode(filterCode).getClazz();
            gt(filterClass, groupName, value);
        } catch (Exception e) {
            log.error("Add condition value error, filterCode={}", filterCode);
        }
        return this;
    }

    @Override
    public BetweenWrapper gt(Class<AbstractFilter> filterClass, Object value) {
        gt(filterClass, "default", value);
        return this;
    }

    @Override
    public BetweenWrapper gt(Class<AbstractFilter> filterClass, String groupName, Object value) {
        try {
            Map<String, BetweenCondition> betweenConditionMap = betweenConditions.get(filterClass);
            if (Objects.isNull(betweenConditionMap)) {
                betweenConditionMap = Maps.newHashMap();
            }
            BetweenCondition betweenCondition = betweenConditionMap.get(groupName);
            if (Objects.isNull(betweenCondition)) {
                betweenCondition = new BetweenCondition();
            }
            betweenCondition.setGroupName(groupName);
            betweenCondition.setGtValue(value);

            betweenConditionMap.put(betweenCondition.getGroupName(), betweenCondition);
            betweenConditions.put(filterClass, betweenConditionMap);
        } catch (Exception e) {
            log.error("Add condition value error, filter={}", filterClass.getCanonicalName());
        }
        return this;
    }

    @Override
    public BetweenWrapper between(String filterCode, BetweenCondition value) {
        Class<AbstractFilter> filterClass = FilterEnum.getByCode(filterCode).getClazz();
        between(filterClass, value);
        return this;
    }

    @Override
    public BetweenWrapper between(Class<AbstractFilter> filterClass, BetweenCondition value) {
        try {
            Map<String, BetweenCondition> betweenConditionMap = betweenConditions.get(filterClass);
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
    public Map<String, BetweenCondition> getBetweenConditions(Class filterClass) {
        return betweenConditions.get(filterClass);
    }

    @Override
    public BetweenWrapper range(String filterCode, RangeCondition value) {
        Class<AbstractFilter> filterClass = FilterEnum.getByCode(filterCode).getClazz();
        range(filterClass, value);
        return this;
    }

    @Override
    public BetweenWrapper range(Class<AbstractFilter> filterClass, RangeCondition value) {
        try {
            Map<String, RangeCondition> rangeConditionMap = rangeConditions.get(filterClass);
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
    public Map<String, RangeCondition> getRangeConditions(Class filterClass) {
        return rangeConditions.get(filterClass);
    }
}
