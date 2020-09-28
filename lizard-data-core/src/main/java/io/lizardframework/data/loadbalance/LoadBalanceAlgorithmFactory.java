package io.lizardframework.data.loadbalance;

import io.lizardframework.data.enums.LoadBalanceType;
import io.lizardframework.data.loadbalance.impl.RandomLoadBalanceAlgorithm;
import io.lizardframework.data.loadbalance.impl.RoundRobinLoadBalanceAlgorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xueqi
 * @date 2020-09-28
 */
public class LoadBalanceAlgorithmFactory {
	private static final Map<LoadBalanceType, LoadBalanceAlgorithm> ALGORITHM_MAP = new HashMap<>();

	static {
		ALGORITHM_MAP.put(LoadBalanceType.RANDOM, new RandomLoadBalanceAlgorithm());
		ALGORITHM_MAP.put(LoadBalanceType.ROUND_ROBIN, new RoundRobinLoadBalanceAlgorithm());
	}

	private LoadBalanceAlgorithmFactory() {
	}

	public static LoadBalanceAlgorithm getAlgorithm(LoadBalanceType loadBalanceType) {
		LoadBalanceAlgorithm loadBalanceAlgorithm = ALGORITHM_MAP.get(loadBalanceType);
		if (loadBalanceAlgorithm == null) {
			throw new IllegalArgumentException("not support load balance algorithm. type: " + loadBalanceType);
		}

		return loadBalanceAlgorithm;
	}
}
