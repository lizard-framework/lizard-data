package io.lizardframework.data.orm.fixture.ms.service.impl;

import io.lizardframework.data.enums.MasterSlaveType;
import io.lizardframework.data.orm.annotation.MasterSlave;
import io.lizardframework.data.orm.fixture.ms.repository.AccountMasterSlaveDAO;
import io.lizardframework.data.orm.fixture.ms.repository.entity.AccountMasterSlaveEntity;
import io.lizardframework.data.orm.fixture.ms.service.AccountMasterSlaveService;
import io.lizardframework.data.orm.fixture.simple.repository.entity.AccountSimpleEntity;
import io.lizardframework.data.orm.hint.impl.HintDataSourceManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;

/**
 * @author xueqi
 * @date 2020-10-11
 */
@Service("AccountMasterSlaveService")
@Slf4j
public class AccountMasterSlaveServiceImpl implements AccountMasterSlaveService, ApplicationContextAware {
	private ApplicationContext applicationContext;

	@Autowired
	private AccountMasterSlaveDAO accountMasterSlaveDAO;

	@Override
	public int saveOneAccount() {
		AccountMasterSlaveEntity entity = genAccount();

		return accountMasterSlaveDAO.insertSelective(entity);
	}

	@Override
	public int saveOneAccountByHintMaster() {
		try {
			AccountMasterSlaveEntity entity = genAccount();

			HintDataSourceManager.getInstance().forceMasterSlave(MasterSlaveType.MASTER);

			return accountMasterSlaveDAO.insertSelective(entity);
		} finally {
			// must be manual clear
			HintDataSourceManager.getInstance().clear();
		}
	}

	@Override
	public int saveOneAccountByHintSlave() {
		try {
			AccountMasterSlaveEntity entity = genAccount();

			HintDataSourceManager.getInstance().forceMasterSlave(MasterSlaveType.SLAVE);

			return accountMasterSlaveDAO.insertSelective(entity);
		} finally {
			// must be manual clear
			HintDataSourceManager.getInstance().clear();
		}
	}

	@Override
	@Transactional(value = "TestMixedMSDataSourceTx")
	@MasterSlave(type = MasterSlaveType.MASTER)
	public int saveOneAccountByHintSlaveAndTx() {
		return this.saveOneAccountByHintSlave();
	}

	@Override
	@MasterSlave(type = MasterSlaveType.MASTER)
	public int saveOneAccountByMaster() {
		AccountMasterSlaveEntity entity = genAccount();

		return accountMasterSlaveDAO.insertSelective(entity);
	}

	@Override
	@MasterSlave(type = MasterSlaveType.SLAVE)
	public int saveOneAccountBySlave() {
		AccountMasterSlaveEntity entity = genAccount();

		return accountMasterSlaveDAO.insertSelective(entity);
	}

	@Override
	@MasterSlave(type = MasterSlaveType.MASTER)
	public void saveAccountByHintNest() {
		AccountMasterSlaveEntity entity = genAccount();
		accountMasterSlaveDAO.insertSelective(entity);

		AccountMasterSlaveService service = (AccountMasterSlaveService) applicationContext.getBean("AccountMasterSlaveService");
		service.saveOneAccountBySlave();

	}

	@Override
	//@Transactional(value = "TestMixedMSDataSourceTx")
	@Transactional(value = "TestMixedMSDataSourceTx", propagation = Propagation.REQUIRES_NEW)
	@MasterSlave(type = MasterSlaveType.SLAVE)
	public AccountMasterSlaveEntity selectOneByTxAndSlave(String accountNo) {
		return accountMasterSlaveDAO.selectOne(accountNo);
	}

	@Override
	@Transactional(value = "TestMixedMSDataSourceTx")
	@MasterSlave(type = MasterSlaveType.MASTER)
	public AccountMasterSlaveEntity saveAndSelectOneByTxNest(String accountNo) {
		int count = this.saveOneAccount();
		log.info("================ insert count:{}", count);

		AccountMasterSlaveService service = (AccountMasterSlaveService) applicationContext.getBean("AccountMasterSlaveService");
		return service.selectOneByTxAndSlave(accountNo);
	}


	private AccountMasterSlaveEntity genAccount() {
		Date date = new Date();

		AccountMasterSlaveEntity entity = new AccountMasterSlaveEntity();
		entity.setAccountNo("AC" + Instant.now().toEpochMilli());
		entity.setUserName("USER_MS_" + RandomUtils.nextInt());
		entity.setCreateTime(date);
		entity.setUpdateTime(date);

		return entity;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
