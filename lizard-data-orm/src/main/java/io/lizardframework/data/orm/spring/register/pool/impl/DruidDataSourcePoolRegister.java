package io.lizardframework.data.orm.spring.register.pool.impl;

import com.alibaba.druid.pool.DruidDataSource;
import io.lizardframework.data.orm.enums.DataSourcePoolType;
import io.lizardframework.data.orm.spring.register.meta.DataSourcePoolMBean;
import io.lizardframework.data.orm.spring.register.pool.IDataSourcePoolRegister;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;

import java.util.Properties;

/**
 * @author xueqi
 * @date 2020-09-22
 */
@Slf4j
public class DruidDataSourcePoolRegister extends IDataSourcePoolRegister {

	@Override
	public void doRegistry(String beanName, DataSourcePoolMBean dataSourcePoolMBean, BeanDefinitionRegistry beanDefinitionRegistry) {
		log.info("Begin registry druid datasource pool bean:{} in spring context.", beanName);

		// pool config properties
		Properties properties = new Properties();
		properties.putAll(dataSourcePoolMBean.getPoolConfigMapper());

		RootBeanDefinition dsBeanDefinition = new RootBeanDefinition(DruidDataSource.class);
		dsBeanDefinition.getPropertyValues().add("url", buildJdbcUrl(dataSourcePoolMBean));
		dsBeanDefinition.getPropertyValues().add("username", dataSourcePoolMBean.getUsername());
		dsBeanDefinition.getPropertyValues().add("password", dataSourcePoolMBean.getPassword());
		dsBeanDefinition.getPropertyValues().add("connectProperties", properties);
		beanDefinitionRegistry.registerBeanDefinition(beanName, dsBeanDefinition);

		log.info("Finished registry druid datasource pool bean:{} in spring context.", beanName);
	}

	@Override
	public DataSourcePoolType support() {
		return DataSourcePoolType.DRUID;
	}
}
