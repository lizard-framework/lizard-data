package io.lizardframework.data.orm.datasource.strategy;

import io.lizardframework.data.enums.MasterSlaveType;
import io.lizardframework.data.utils.concurrent.AtomicIntegerEnhance;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * DataSource Selector Strategy
 *
 * @author xueqi
 * @date 2020-09-13
 */
public class DataSourceStrategy {
	private static final AtomicIntegerEnhance ATOMIC_INTEGER_ENHANCE = new AtomicIntegerEnhance(0);

	private int             id;
	/**
	 * master slave type
	 */
	@Getter
	@Setter
	private MasterSlaveType masterSlaveType;
	/**
	 * repository sharding key
	 */
	@Getter
	@Setter
	private String          repositoryShardingKey;
	/**
	 * actual datasource bean name
	 */
	@Getter
	@Setter
	private String          dataSourceKey;
	/**
	 * in transaction flag
	 */
	@Getter
	@Setter
	private boolean         transaction = false;

	public DataSourceStrategy() {
		this.id = ATOMIC_INTEGER_ENHANCE.getAndUpdate();
	}

	public DataSourceStrategy(MasterSlaveType masterSlaveType, String repositoryShardingKey, boolean transaction) {
		this();

		this.masterSlaveType = masterSlaveType;
		this.repositoryShardingKey = repositoryShardingKey;
		this.transaction = transaction;
	}

	public DataSourceStrategy(DataSourceStrategy strategy) {
		this();

		this.masterSlaveType = strategy.masterSlaveType;
		this.repositoryShardingKey = strategy.repositoryShardingKey;
		this.dataSourceKey = strategy.dataSourceKey;
		this.transaction = strategy.transaction;
	}

	@Override
	public String toString() {
		return "DataSourceStrategy{" +
				"id=" + id +
				", masterSlaveType=" + masterSlaveType +
				", repositoryShardingKey='" + repositoryShardingKey + '\'' +
				", dataSourceKey='" + dataSourceKey + '\'' +
				", transaction=" + transaction +
				'}';
	}
}
