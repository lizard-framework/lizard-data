package io.lizardframework.data.orm.hint.impl;

import io.lizardframework.data.enums.MasterSlaveType;
import io.lizardframework.data.orm.datasource.strategy.DataSourceStrategy;
import io.lizardframework.data.orm.datasource.strategy.StrategyHolder;
import io.lizardframework.data.orm.hint.HintSupport;
import io.lizardframework.data.utils.JSONUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xueqi
 * @date 2020-09-30
 */
@Slf4j
public class HintDataSourceManager implements HintSupport<DataSourceStrategy> {
	private HintDataSourceManager() {
	}

	@Override
	public void addStrategy(DataSourceStrategy strategy) {
		log.debug("Adding hint datasource strategy: '{}'", strategy);
		StrategyHolder.addDataSourceStrategy(strategy);
	}

	@Override
	public void clear() {
		StrategyHolder.removeDataSourceStrategy();
	}

	/**
	 * force set MasterSlave type
	 *
	 * @param type
	 */
	public void forceMasterSlave(MasterSlaveType type) {
		DataSourceStrategy strategy = null;

		DataSourceStrategy currenctStrategy = StrategyHolder.getDataSourceStrategy();
		if (currenctStrategy == null) {
			strategy = new DataSourceStrategy();
			strategy.setMasterSlaveType(type);
		} else if (currenctStrategy.isTransaction()) {
			// current thread in transaction, not change datasource, because connection already confirm
			strategy = new DataSourceStrategy(currenctStrategy);
		} else {
			strategy = new DataSourceStrategy(currenctStrategy);
			strategy.setMasterSlaveType(type);
		}
		addStrategy(strategy);
	}

	/**
	 * force set repository sharding
	 *
	 * @param shardingKey
	 */
	public void forceRepositorySharding(String shardingKey) {
		DataSourceStrategy strategy = null;

		DataSourceStrategy currenctStrategy = StrategyHolder.getDataSourceStrategy();
		if (currenctStrategy == null) {
			strategy = new DataSourceStrategy();
			strategy.setRepositoryShardingKey(shardingKey);
		} else if (currenctStrategy.isTransaction()) {
			strategy = new DataSourceStrategy(currenctStrategy);
		} else {
			strategy = new DataSourceStrategy(currenctStrategy);
			strategy.setRepositoryShardingKey(shardingKey);
		}

		addStrategy(strategy);
	}

	/**
	 * force set repository sharding and master slave type
	 *
	 * @param shardingKey
	 * @param type
	 */
	public void forceRepositorySharding(String shardingKey, MasterSlaveType type) {
		DataSourceStrategy strategy = null;

		DataSourceStrategy currenctStrategy = StrategyHolder.getDataSourceStrategy();
		if (currenctStrategy == null) {
			strategy = new DataSourceStrategy();
			strategy.setMasterSlaveType(type);
			strategy.setRepositoryShardingKey(shardingKey);
		} else if (currenctStrategy.isTransaction()) {
			strategy = new DataSourceStrategy(currenctStrategy);
		} else {
			strategy = new DataSourceStrategy(currenctStrategy);
			strategy.setMasterSlaveType(type);
			strategy.setRepositoryShardingKey(shardingKey);
		}

		addStrategy(strategy);
	}

	private static class InstanceHolder {
		private static final HintDataSourceManager INSTANCE = new HintDataSourceManager();
	}

	public static HintDataSourceManager getInstance() {
		return InstanceHolder.INSTANCE;
	}
}
