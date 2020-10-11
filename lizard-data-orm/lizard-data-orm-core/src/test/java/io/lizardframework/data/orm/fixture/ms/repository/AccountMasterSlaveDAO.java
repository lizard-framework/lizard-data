package io.lizardframework.data.orm.fixture.ms.repository;

import io.lizardframework.data.orm.fixture.ms.repository.entity.AccountMasterSlaveEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountMasterSlaveDAO {
	int deleteByPrimaryKey(Long id);

	int insert(AccountMasterSlaveEntity record);

	int insertSelective(AccountMasterSlaveEntity record);

	AccountMasterSlaveEntity selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(AccountMasterSlaveEntity record);

	int updateByPrimaryKey(AccountMasterSlaveEntity record);
}