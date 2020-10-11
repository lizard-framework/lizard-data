package io.lizardframework.data.orm.fixture.ms.service.impl;

import io.lizardframework.data.orm.fixture.ms.repository.AccountMasterSlaveDAO;
import io.lizardframework.data.orm.fixture.ms.service.AccountMasterSlaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xueqi
 * @date 2020-10-11
 */
@Service
@Slf4j
public class AccountMasterSlaveServiceImpl implements AccountMasterSlaveService {

	@Autowired
	private AccountMasterSlaveDAO accountMasterSlaveDAO;

}
