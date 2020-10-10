package io.lizardframework.data.orm.fixture.simple.repository;

import io.lizardframework.data.orm.fixture.simple.repository.entity.AccountSimpleEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountSimpleDAO {
	int deleteByPrimaryKey(Long id);

	int insert(AccountSimpleEntity record);

	int insertSelective(AccountSimpleEntity record);

	AccountSimpleEntity selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(AccountSimpleEntity record);

	int updateByPrimaryKey(AccountSimpleEntity record);
}