package io.lizardframework.data.orm.annotation;

import java.lang.annotation.*;

/**
 * Table Sharding Annotation
 *
 * @author xueqi
 * @date 2020-09-28
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface TableSharding {

	String strategy();
}
