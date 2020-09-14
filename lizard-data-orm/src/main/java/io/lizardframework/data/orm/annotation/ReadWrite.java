package io.lizardframework.data.orm.annotation;

import io.lizardframework.data.orm.enums.ReadWriteType;

import java.lang.annotation.*;

/**
 * 数据源读写分离注解
 *
 * @author xueqi
 * @date 2020-09-14
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ReadWrite {

	/**
	 * 读写类型
	 *
	 * @return
	 */
	ReadWriteType type() default ReadWriteType.WRITE;

}
