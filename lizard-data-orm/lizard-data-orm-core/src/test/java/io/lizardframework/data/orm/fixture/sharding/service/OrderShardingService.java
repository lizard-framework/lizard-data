package io.lizardframework.data.orm.fixture.sharding.service;

import io.lizardframework.data.orm.fixture.sharding.repository.entity.OrderEntity;

/**
 * @author xueqi
 * @date 2020-10-12
 */
public interface OrderShardingService {

	/**
	 * save order and tx without transactional
	 *
	 * @param order
	 */
	void saveOrderAndTx(OrderEntity order);

	/**
	 * save order and tx with transaction
	 *
	 * @param order
	 */
	void saveOrderAndTxWithTransaction(OrderEntity order);

	/**
	 * save order and tx with hint and non transactional
	 *
	 * @param order
	 */
	void saveOrderAndTxWithRepositoryHint(OrderEntity order);
}
