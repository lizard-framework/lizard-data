package io.lizardframework.data.orm.fixture.sharding;

import io.lizardframework.data.orm.AbstractSpringTest;
import io.lizardframework.data.orm.fixture.sharding.repository.entity.OrderEntity;
import io.lizardframework.data.orm.fixture.sharding.service.OrderShardingService;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * @author xueqi
 * @date 2020-10-12
 */
public class OrderShardingServiceTest extends AbstractSpringTest {

	@Test
	public void saveOrderAndTxTest() {
		OrderShardingService service = super.getBean("OrderShardingService");

		OrderEntity order = new OrderEntity();
		order.setAmount(BigDecimal.TEN);
		order.setAccountNo("AC1602490506081");

		service.saveOrderAndTx(order);
	}

}
