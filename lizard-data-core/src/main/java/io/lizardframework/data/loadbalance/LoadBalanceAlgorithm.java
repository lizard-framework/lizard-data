package io.lizardframework.data.loadbalance;

import io.lizardframework.data.enums.LoadBalanceType;

import java.util.List;

/**
 * @author xueqi
 * @date 2020-09-28
 */
public interface LoadBalanceAlgorithm {


	<T> T selector(String name, List<T> targets);

	LoadBalanceType type();
}
