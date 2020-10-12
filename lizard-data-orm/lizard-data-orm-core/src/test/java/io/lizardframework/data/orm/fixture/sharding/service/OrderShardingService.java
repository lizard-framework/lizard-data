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

}
