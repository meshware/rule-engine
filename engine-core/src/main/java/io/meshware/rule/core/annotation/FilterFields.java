package io.meshware.rule.core.annotation;

import java.lang.annotation.*;

/**
 * Filter Fields Annotation
 *
 * @author Zhiguo.Chen
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FilterFields {

    FilterField[] value();
}
