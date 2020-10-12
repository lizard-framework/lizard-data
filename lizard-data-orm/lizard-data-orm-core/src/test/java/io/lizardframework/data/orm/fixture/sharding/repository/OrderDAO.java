package io.lizardframework.data.orm.fixture.sharding.repository;

import io.lizardframework.data.orm.annotation.TableSharding;
import io.lizardframework.data.orm.fixture.sharding.repository.entity.OrderEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDAO {
	int deleteByPrimaryKey(Long id);

	int insert(OrderEntity record);

	@TableSharding(strategy = "T(io.lizardframework.data.orm.fixture.sharding.strategy.OrderTableShardingStrategy.strategy(#record.orderNo))")
	int insertSelective(@Param("record") OrderEntity record);

	OrderEntity selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(OrderEntity record);

	int updateByPrimaryKey(OrderEntity record);
}