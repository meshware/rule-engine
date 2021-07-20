package io.meshware.rule.core.filter;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.meshware.rule.core.annotation.FilterField;
import io.meshware.rule.core.enums.FilterEnum;
import io.meshware.rule.core.wrapper.BetweenWrapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Template impl abstract class
 *
 * @author Zhiguo.Chen
 */
@Slf4j
@Data
public abstract class AbstractFilter<T> implements Predicate<T> {

    private Object eqConditions;

    private List<Object> inConditions;

    private List<Object> ninConditions;

    private List<Object> orConditions;

    private Map<String, BetweenWrapper.BetweenCondition<T>> betweenConditions;

    private Map<String, BetweenWrapper.RangeCondition<T>> rangeConditions;

    private Map<String, Object> fieldValues;

    private AbstractFilter nextFilter;

    private Object request;

    private int order = 5;

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param o the input argument
     * @return {@code true} if the input argument matches the predicate,
     * otherwise {@code false}
     */
    @Override
    public boolean test(T o) {
        extractValue(o);
        return validate(o);
    }

    /**
     * Verify the result and return true or false
     *
     * @param o
     * @return
     */
    public abstract boolean validate(T o);

    /**
     * Extract value by annotation from entity
     *
     * @param object
     */
    private void extractValue(T object) {
        Set<Field> fieldList = getAllFields(object.getClass());
        for (Field field : fieldList) {
            if (field.getAnnotationsByType(FilterField.class).length > 0) {
                if (Objects.isNull(fieldValues)) {
                    fieldValues = Maps.newConcurrentMap();
                }
                try {
                    field.setAccessible(true);
                    FilterField filterField = field.getDeclaredAnnotation(FilterField.class);
                    Object value = field.get(object);
                    if (Objects.isNull(value)) {
                        continue;
                    }
                    if (!Objects.isNull(filterField) && filterField.value() != FilterEnum.OTHER) {
                        fieldValues.put(filterField.value().getCode(), value);
                    } else {
                        fieldValues.put(field.getName(), value);
                    }
                } catch (Exception e) {
                    log.error("[定向规则引擎]Get values from entity error:{}", e.getMessage(), e);
                }
            }
        }

        if (!Objects.isNull(request)) {
            Set<Field> fieldList2 = getAllFields(request.getClass());
            for (Field field : fieldList2) {
                if (field.getAnnotationsByType(FilterField.class).length > 0) {
                    if (Objects.isNull(fieldValues)) {
                        fieldValues = Maps.newConcurrentMap();
                    }
                    try {
                        field.setAccessible(true);
                        FilterField filterField = field.getDeclaredAnnotation(FilterField.class);
                        Object value = field.get(request);
                        if (Objects.isNull(value)) {
                            continue;
                        }
                        if (!Objects.isNull(filterField) && filterField.value() != FilterEnum.OTHER) {
                            fieldValues.put(filterField.value().getCode(), value);
                        } else {
                            fieldValues.put(field.getName(), value);
                        }
                    } catch (Exception e) {
                        log.error("[定向规则引擎]Get values from request error:{}", e.getMessage(), e);
                    }
                }
            }
        }
        //FilterField[] filters = object.getClass().getAnnotationsByType(FilterField.class);
        //if (Objects.isNull(filters) || filters.length == 0) {
        //    return;
        //}
        //fieldValues = Maps.newConcurrentMap();
        //for (FilterField filterAnnotation : filters) {
        //    try {
        //        Field field = object.getClass().getDeclaredField(filterAnnotation.value());
        //        field.setAccessible(true);
        //        fieldValues.put(filterAnnotation.value(), field.get(object));
        //    } catch (Exception e) {
        //        log.error("Get values from entity error:{}", e.getMessage(), e);
        //    }
        //}
    }

    /**
     * Get all fields include it's parent class
     *
     * @param clazz
     * @return
     */
    private Set<Field> getAllFields(Class clazz) {
        Set fields = Sets.newHashSet();
        ReflectionUtils.doWithFields(clazz, field -> {
            fields.add(field);
        });
        return fields;
    }

    /**
     * Get value from request or entity
     *
     * @param fieldName
     * @return
     */
    public <R> R getValue(String fieldName) {
        Object result = null;
        if (!Objects.isNull(getFieldValues())) {
            result = getFieldValues().get(fieldName);
        }
        if (!Objects.isNull(request)) {
            if (request instanceof Map) {
                result = ((Map) request).get(fieldName);
            } else {
                try {
                    Field field = ReflectionUtils.findField(request.getClass(), fieldName);
                    if (!Objects.isNull(field)) {
                        field.setAccessible(true);
                        result = field.get(request);
                    }
                } catch (Exception ex) {
                    log.error("Can't find filed from the request! filedName={}", fieldName);
                }
            }
        }
        return result == null ? null : (R) result;
    }

}
