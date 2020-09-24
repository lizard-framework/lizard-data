package io.lizardframework.data.orm.annotation;

import io.lizardframework.data.orm.enums.MasterSlaveType;

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
public @interface MasterSlave {

	/**
	 * 读写类型
	 *
	 * @return
	 */
	MasterSlaveType type() default MasterSlaveType.MASTER;

}
