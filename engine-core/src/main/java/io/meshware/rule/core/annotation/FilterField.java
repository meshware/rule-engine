package io.meshware.rule.core.annotation;

import io.meshware.rule.core.enums.FilterEnum;

import java.lang.annotation.*;

/**
 * Filter Field Annotation
 *
 * @author Zhiguo.Chen
 */
@Documented
@Inherited
@Repeatable(FilterFields.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FilterField {

    /**
     * value
     *
     * @return
     */
    FilterEnum value() default FilterEnum.OTHER;

}
