package io.lizardframework.data.orm.hint.impl;

import io.lizardframework.data.orm.datasource.strategy.StrategyHolder;
import io.lizardframework.data.orm.hint.HintSupport;
import io.lizardframework.data.utils.JSONUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author xueqi
 * @date 2020-09-30
 */
@Slf4j
public class HintTableShardingManager implements HintSupport<Map<String, String>> {
	private HintTableShardingManager() {
	}

	@Override
	public void addStrategy(Map<String, String> strategy) {
		if (log.isDebugEnabled()) {
			log.debug("Adding hint table sharding strategy: '{}'", JSONUtils.toJSONString(strategy));
		}

		StrategyHolder.addTableShardingStrategy(strategy);
	}

	@Override
	public void clear() {
		StrategyHolder.removeTableShardingStrategy();
	}

	private static class InstanceHolder {
		private static final HintTableShardingManager INSTANCE = new HintTableShardingManager();
	}

	public static HintTableShardingManager getInstance() {
		return InstanceHolder.INSTANCE;
	}
}
