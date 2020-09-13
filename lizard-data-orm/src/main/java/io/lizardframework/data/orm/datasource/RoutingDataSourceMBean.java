package io.lizardframework.data.orm.datasource;

import lombok.Setter;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Map;

/**
 * @author xueqi
 * @date 2020-09-13
 */
public abstract class RoutingDataSourceMBean extends AbstractRoutingDataSource {

	/**
	 * 数据源选择工具，将数据源选择逻辑抽象到DataSourceKey，不在DataSource中进行负载等操作
	 */
	@Setter
	protected DataSourceKey       dataSourceKey;
	/**
	 * 数据源集合，分库：groupName - ReadWriteDataSource; 读写分离：atomName - DataSource
	 */
	@Setter
	protected Map<Object, Object> dataSources;

	@Override
	public void afterPropertiesSet() {
		super.setTargetDataSources(dataSources);
		super.afterPropertiesSet();
	}
}
