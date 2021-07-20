package io.meshware.rule.core.annotation;

import java.lang.annotation.*;

/**
 * Enable Filter Annotation
 *
 * @author Zhiguo.Chen
 * @since 2019-05-07
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
// @Import(FilterConfiguration.class)
public @interface EnableFilter {
}
