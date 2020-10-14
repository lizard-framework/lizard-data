package io.lizardframework.data.orm.fixture.sharding;

import io.lizardframework.data.orm.AbstractSpringTest;
import io.lizardframework.data.orm.fixture.sharding.repository.entity.OrderEntity;
import io.lizardframework.data.orm.fixture.sharding.service.OrderShardingService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xueqi
 * @date 2020-10-12
 */
public class OrderShardingServiceTest extends AbstractSpringTest {

	@Test
	public void saveOrderAndTxTest() {
		OrderShardingService service = super.getBean("OrderShardingService");

		OrderEntity order = new OrderEntity();
		order.setTxDate(DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS"));
		order.setAmount(BigDecimal.TEN);
		order.setAccountNo("AC1602490505857");

		service.saveOrderAndTx(order);
	}

	@Test
	public void saveOrderAndTxWithTransactionTest() {
		OrderShardingService service = super.getBean("OrderShardingService");

		OrderEntity order = new OrderEntity();
		order.setTxDate(DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS"));
		order.setAmount(BigDecimal.TEN);
		order.setAccountNo("AC1602490505857");

		service.saveOrderAndTxWithTransaction(order);
	}

	@Test
	public void saveOrderAndTxWithRepositoryHintTest() {
		OrderShardingService service = super.getBean("OrderShardingService");

		OrderEntity order = new OrderEntity();
		order.setTxDate(DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS"));
		order.setAmount(BigDecimal.TEN);
		order.setAccountNo("AC1602490505857");

		service.saveOrderAndTxWithRepositoryHint(order);
	}

	@Test
	public void saveOrderAndTxWithNestTransactionTest() {
		OrderShardingService service = super.getBean("OrderShardingService");

		OrderEntity order = new OrderEntity();
		order.setTxDate(DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS"));
		order.setAmount(BigDecimal.TEN);
		order.setAccountNo("AC1602490505851");

		service.saveOrderAndTxWithNestTransaction(order);
	}
}
