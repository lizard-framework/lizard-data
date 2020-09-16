package io.lizardframework.data.orm.annotation;

import java.lang.annotation.*;

/**
 * 分库注解
 *
 * @author xueqi
 * @date 2020-09-16
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RepositorySharding {

	/**
	 * 分库策略Bean-Name
	 *
	 * @return
	 */
	String strategy();

	/**
	 * 分库参数
	 *
	 * @return
	 */
	String param();
}
