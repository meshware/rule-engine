package io.meshware.rule.core;

import io.meshware.rule.core.filter.AbstractFilter;
import io.meshware.rule.core.wrapper.AbstractWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Filter Executor
 *
 * @author Zhiguo.Chen
 */
@Slf4j
public class FilterExecutor {

    private volatile static FilterExecutor instance;

    private FilterExecutor() {
    }

    public static FilterExecutor getInstance() {
        if (instance == null) {
            synchronized (FilterExecutor.class) {
                if (instance == null) {
                    instance = new FilterExecutor();
                }
            }
        }
        return instance;
    }

    /**
     * Do impl logic
     *
     * @param wrapper wrapper
     * @return data
     */
    public <T> boolean doValidate(AbstractWrapper<T> wrapper) {
        return doValidate(wrapper, null);
    }

    /**
     * Do impl logic
     *
     * @param wrapper wrapper
     * @param <T>     request
     * @return data
     */
    public <T> boolean doValidate(AbstractWrapper<T> wrapper, Object request) {
        List<T> result = doFilter(wrapper, request);
        if (log.isDebugEnabled()) {
            log.debug("[规则引擎]执行规则结果，原数据个数：{}，规则个数：{}，剩余数据个数：{}", wrapper.getEntities().size(), wrapper.getFilters().size(), result.size());
        }
        return !CollectionUtils.isEmpty(result);
    }

    /**
     * Do impl logic
     *
     * @param wrapper wrapper
     * @return data
     */
    public <T> List<T> doFilter(AbstractWrapper<T> wrapper) {
        return doFilter(wrapper, null);
    }

    /**
     * Do impl logic
     *
     * @param wrapper wrapper
     * @param <T>     request
     * @return data
     */
    public <T> List<T> doFilter(AbstractWrapper<T> wrapper, Object request) {
        List<T> resources = wrapper.getEntities();
        if (CollectionUtils.isEmpty(resources) || CollectionUtils.isEmpty(wrapper.getFilters())) {
            return resources;
        }
        Stream<T> stream = resources.stream();
        //List<AbstractFilter> filters = Lists.newArrayList();
        for (Class<AbstractFilter<T>> filterClass : wrapper.getFilters()) {
            if (log.isDebugEnabled()) {
                log.debug("[规则引擎]加载规则：{}", filterClass.getCanonicalName());
            }
            try {
                AbstractFilter<T> filter = null;
                if (!Objects.isNull(filterClass.getAnnotation(Component.class))) {
                    // Don't use singleton pattern in multithread
                    // filter = ComponentUtil.getBean(filterClass);
                }
                if (Objects.isNull(filter)) {
                    filter = filterClass.newInstance();
                }
                filter.setRequest(request);
                filter.setEqConditions(wrapper.getEqConditions(filterClass));
                filter.setInConditions(wrapper.getInConditions(filterClass));
                filter.setNinConditions(wrapper.getNinConditions(filterClass));
                filter.setOrConditions(wrapper.getOrConditions(filterClass));
                filter.setBetweenConditions(wrapper.getBetweenConditions(filterClass));
                filter.setRangeConditions(wrapper.getRangeConditions(filterClass));
                //filters.add(impl);
                stream = stream.filter(filter);
            } catch (Exception e) {
                log.error("[定向规则引擎]Do impl error:{}", e.getMessage(), e);
            }
        }

        //filters.sort(Comparator.comparing(AbstractFilter::getOrder));
        //for (AbstractFilter impl : filters) {
        //    stream = stream.impl(impl);
        //}
        return stream.collect(Collectors.toList());
    }

    /**
     * Do paging impl logic
     *
     * @param wrapper
     * @param <T>
     * @return
     */
    // public <T> PageEntity<T> doPageFilter(AbstractWrapper<T, AbstractWrapper> wrapper, int pageNum, int pageSize) {
    //     List<T> resources = wrapper.getEntities();
    //     Stream<T> stream = resources.stream();
    //     //List<AbstractFilter> filters = Lists.newArrayList();
    //     for (Class<AbstractFilter> filterClass : wrapper.getFilters()) {
    //         try {
    //             AbstractFilter filter = null;
    //             if (!Objects.isNull(filterClass.getAnnotation(Component.class))) {
    //                 // Don't use singleton pattern in multithread
    //                 filter = ComponentUtil.getBean(filterClass);
    //             }
    //             if (Objects.isNull(filter)) {
    //                 filter = filterClass.newInstance();
    //             }
    //             filter.setEqConditions(wrapper.getEqConditions(filterClass));
    //             filter.setInConditions(wrapper.getInConditions(filterClass));
    //             filter.setNinConditions(wrapper.getNinConditions(filterClass));
    //             filter.setOrConditions(wrapper.getOrConditions(filterClass));
    //             filter.setBetweenConditions(wrapper.getBetweenConditions(filterClass));
    //             filter.setRangeConditions(wrapper.getRangeConditions(filterClass));
    //             //filters.add(impl);
    //             stream = stream.filter(filter);
    //         } catch (Exception e) {
    //             log.error("[定向规则引擎]Do impl error:{}", e.getMessage(), e);
    //         }
    //     }
    //
    //     List<T> records = stream.limit(pageNum * pageSize).collect(Collectors.toList());
    //     List<T> subRecords = Lists.newArrayList(records.subList((pageNum - 1) * pageSize, records.size()));
    //     return new PageEntity<T>().setCurrent(pageNum).setSize(pageSize).setRecords(subRecords);
    // }

    //public <T> List<T> doFilter(Wrapper<T> wrapper) {
    //    List<T> resources = wrapper.getEntities();
    //    List<AbstractFilter> filters = Lists.newArrayListWithExpectedSize(wrapper.getFilters().size());
    //    for (FilterEnum filterEnum : wrapper.getFilters()) {
    //        try {
    //            AbstractFilter impl = (AbstractFilter) filterEnum.getClazz().newInstance();
    //            impl.setEqConditions(wrapper.getEqConditions(filterEnum.getCode()));
    //            impl.setInConditions(wrapper.getInConditions(filterEnum.getCode()));
    //            filters.add(impl);
    //            //stream = stream.impl(impl);
    //        } catch (Exception e) {
    //            log.error("Do impl error:{}", e.getMessage(), e);
    //        }
    //
    //    }
    //    filters.sort(Comparator.comparing(AbstractFilter::getOrder));
    //    AbstractFilter startFilter = FilterChainBuilder.buildFilterChain(filters);
    //    return (List<T>) resources.stream().impl(startFilter).collect(Collectors.toList());
    //}
}
