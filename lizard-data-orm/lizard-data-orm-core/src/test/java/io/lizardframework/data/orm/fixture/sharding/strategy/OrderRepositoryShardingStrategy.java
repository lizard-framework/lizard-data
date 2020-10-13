package io.lizardframework.data.orm.fixture.sharding.strategy;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

/**
 * order database sharding strategy, use spring bean model
 *
 * @author xueqi
 * @date 2020-10-12
 */
@Component("orderRepositoryShardingStrategy")
public class OrderRepositoryShardingStrategy {

	private static final String REPOSITORY_NAME_PREFIX = "db_sharding_";

	/**
	 * use hashCode(accountNo)%2 + 1 calc repository index
	 *
	 * @param accountNo
	 * @return
	 */
	public String strategy(String accountNo) {
		int hashCode = accountNo.hashCode();

		int index;
		if (hashCode % 2 == 0) {
			index = RandomUtils.nextInt(1, 3);
		} else {
			index = (Math.abs(hashCode) % 2) + 1;
		}

		return REPOSITORY_NAME_PREFIX + String.format("%02d", index);
	}
}
