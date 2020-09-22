package io.lizardframework.data.orm.spring.register.pool.impl;

import com.zaxxer.hikari.HikariDataSource;
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
public class HikariCPDataSourcePoolRegister extends IDataSourcePoolRegister {

	@Override
	public void doRegistry(String beanName, DataSourcePoolMBean dataSourcePoolMBean, BeanDefinitionRegistry beanDefinitionRegistry) {
		log.info("Begin registry HikariCP datasource pool bean:{} in spring context.", beanName);

		// pool config properties
		Properties properties = new Properties();
		properties.putAll(dataSourcePoolMBean.getPoolConfigMapper());

		RootBeanDefinition dsRootBeanDefinition = new RootBeanDefinition(HikariDataSource.class);
		dsRootBeanDefinition.getPropertyValues().add("driverClassName", dataSourcePoolMBean.getDbType().getJdbcDriver());
		dsRootBeanDefinition.getPropertyValues().add("jdbcUrl", buildJdbcUrl(dataSourcePoolMBean));
		dsRootBeanDefinition.getPropertyValues().add("username", dataSourcePoolMBean.getUsername());
		dsRootBeanDefinition.getPropertyValues().add("password", dataSourcePoolMBean.getPassword());
		dsRootBeanDefinition.getPropertyValues().add("dataSourceProperties", properties);

		log.info("Finished registry druid datasource pool bean:{} in spring context.", beanName);
	}

	@Override
	public DataSourcePoolType support() {
		return DataSourcePoolType.HikariCP;
	}
}
