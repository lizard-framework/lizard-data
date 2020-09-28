package io.lizardframework.data.loadbalance.impl;

import io.lizardframework.data.enums.LoadBalanceType;
import io.lizardframework.data.loadbalance.LoadBalanceAlgorithm;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author xueqi
 * @date 2020-09-28
 */
public class RandomLoadBalanceAlgorithm implements LoadBalanceAlgorithm {

	@Override
	public <T> T selector(String name, List<T> targets) {
		return targets.get(ThreadLocalRandom.current().nextInt(targets.size()));
	}

	@Override
	public LoadBalanceType type() {
		return LoadBalanceType.RANDOM;
	}
}
