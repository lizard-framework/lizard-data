package io.lizardframework.data.orm.fixture.sharding.repository;

import io.lizardframework.data.orm.annotation.TableSharding;
import io.lizardframework.data.orm.fixture.sharding.repository.entity.TransactionEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDAO {
	int deleteByPrimaryKey(Long id);

	int insert(TransactionEntity record);

	@TableSharding(strategy = "T(io.lizardframework.data.orm.fixture.sharding.strategy.OrderTableShardingStrategy.strategy(#record.orderNo))")
	int insertSelective(@Param("record") TransactionEntity record);

	TransactionEntity selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(TransactionEntity record);

	int updateByPrimaryKey(TransactionEntity record);
}