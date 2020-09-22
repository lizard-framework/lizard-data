package io.lizardframework.data.orm.spring.register;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

/**
 * Mixed DataSource Spring Bean Register
 *
 * @author xueqi
 * @date 2020-09-22
 */
@Slf4j
public class MixedDataSourceBeanRegister {

	/**
	 * do bean registry processor
	 *
	 * @param beanDefinitionRegistry
	 */
	public void doRegistry(BeanDefinitionRegistry beanDefinitionRegistry) {
		// 1. get mixed-data config
		// 2. registry mixed datasource
		// 3. registry repository sharding and read write interceptor
		// 4. registry transaction manager
		// 5. registry mybatis bean and table sharding plugin
		// 6. register jdbcTemplate table sharding plugin
		// 7. report framework version and metric info
	}

}
