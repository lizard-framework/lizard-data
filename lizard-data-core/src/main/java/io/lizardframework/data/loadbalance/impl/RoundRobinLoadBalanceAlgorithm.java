package io.lizardframework.data.loadbalance.impl;

import io.lizardframework.data.enums.LoadBalanceType;
import io.lizardframework.data.loadbalance.LoadBalanceAlgorithm;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xueqi
 * @date 2020-09-28
 */
public class RoundRobinLoadBalanceAlgorithm implements LoadBalanceAlgorithm {

	private static final Map<String, AtomicInteger> COUNTS = new ConcurrentHashMap<>();

	@Override
	public <T> T selector(String name, List<T> targets) {
		AtomicInteger counts = COUNTS.containsKey(name) ? COUNTS.get(name) : new AtomicInteger(0);
		COUNTS.putIfAbsent(name, counts);

		// if count == targets.size, set zero
		counts.compareAndSet(targets.size(), 0);

		return targets.get(Math.abs(counts.getAndIncrement() % targets.size()));
	}

	@Override
	public LoadBalanceType type() {
		return LoadBalanceType.ROUND_ROBIN;
	}
}
