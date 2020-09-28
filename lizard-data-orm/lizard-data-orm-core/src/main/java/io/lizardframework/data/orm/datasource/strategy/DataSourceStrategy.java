package io.lizardframework.data.orm.datasource.strategy;

import io.lizardframework.data.orm.enums.MasterSlaveType;
import lombok.Data;

/**
 * DataSource Selector Strategy
 *
 * @author xueqi
 * @date 2020-09-13
 */
@Data
public class DataSourceStrategy {
	/**
	 * master slave type
	 */
	private MasterSlaveType masterSlaveType;
	/**
	 * repository sharding key
	 */
	private String          repositoryShardingKey;
	/**
	 * actual datasource bean name
	 */
	private String          dataSourceKey;
	/**
	 * in transaction flag
	 */
	private boolean         transaction = false;

	public DataSourceStrategy(MasterSlaveType masterSlaveType, String repositoryShardingKey, boolean transaction) {
		this.masterSlaveType = masterSlaveType;
		this.repositoryShardingKey = repositoryShardingKey;
		this.transaction = transaction;
	}
}
