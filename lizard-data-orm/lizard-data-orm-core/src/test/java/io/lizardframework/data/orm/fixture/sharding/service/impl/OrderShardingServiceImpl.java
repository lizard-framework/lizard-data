package io.lizardframework.data.orm.fixture.sharding.service.impl;

import io.lizardframework.data.enums.MasterSlaveType;
import io.lizardframework.data.orm.annotation.RepositorySharding;
import io.lizardframework.data.orm.fixture.sharding.repository.OrderDAO;
import io.lizardframework.data.orm.fixture.sharding.repository.TransactionDAO;
import io.lizardframework.data.orm.fixture.sharding.repository.entity.OrderEntity;
import io.lizardframework.data.orm.fixture.sharding.repository.entity.TransactionEntity;
import io.lizardframework.data.orm.fixture.sharding.service.OrderShardingService;
import io.lizardframework.data.orm.fixture.sharding.strategy.OrderTableShardingStrategy;
import io.lizardframework.data.orm.hint.impl.HintDataSourceManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author xueqi
 * @date 2020-10-12
 */
@Service("OrderShardingService")
@Slf4j
public class OrderShardingServiceImpl implements OrderShardingService {

	@Autowired
	private OrderDAO       orderDAO;
	@Autowired
	private TransactionDAO transactionDAO;

	@Override
	@RepositorySharding(strategy = "@orderRepositoryShardingStrategy.strategy(#order.accountNo)")
	public void saveOrderAndTx(OrderEntity order) {
		Date date = new Date();

		String orderNo    = RandomStringUtils.randomNumeric(12);
		String tableIndex = OrderTableShardingStrategy.calcIndex(orderNo);
		orderNo = orderNo + tableIndex;

		order.setOrderNo(orderNo);
		order.setCreateTime(date);
		order.setUpdateTime(date);
		orderDAO.insertSelective(order);


		TransactionEntity transaction = new TransactionEntity();
		transaction.setOrderNo(orderNo);
		transaction.setTxNo(RandomStringUtils.randomNumeric(12) + tableIndex);
		transaction.setAmount(order.getAmount());
		transaction.setCreateTime(date);
		transaction.setUpdateTime(date);
		transactionDAO.insertSelective(transaction);
	}

	@Override
	@RepositorySharding(strategy = "@orderRepositoryShardingStrategy.strategy(#order.accountNo)")
	@Transactional(value = "TestMixedShardingMSDataSourceTx")
	public void saveOrderAndTxWithTransaction(OrderEntity order) {
		this.saveOrderAndTx(order);
	}

	@Override
	public void saveOrderAndTxWithRepositoryHint(OrderEntity order) {
		HintDataSourceManager hint = HintDataSourceManager.getInstance();
		try {
			hint.forceRepositorySharding("db_sharding_01", MasterSlaveType.MASTER);

			this.saveOrderAndTx(order);
		} finally {
			hint.clear();
		}
	}
}
