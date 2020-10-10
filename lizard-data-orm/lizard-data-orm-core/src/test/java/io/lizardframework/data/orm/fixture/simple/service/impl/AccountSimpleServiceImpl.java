package io.lizardframework.data.orm.fixture.simple.service.impl;

import io.lizardframework.data.orm.fixture.simple.repository.AccountSimpleDAO;
import io.lizardframework.data.orm.fixture.simple.repository.entity.AccountSimpleEntity;
import io.lizardframework.data.orm.fixture.simple.service.AccountSimpleService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;

/**
 * @author xueqi
 * @date 2020-10-10
 */
@Service("AccountSimpleService")
public class AccountSimpleServiceImpl implements AccountSimpleService, ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Autowired
	private AccountSimpleDAO accountSimpleDAO;

	@Override
	public int saveOneAccount() {
		AccountSimpleEntity entity = this.genAccount();
		return accountSimpleDAO.insertSelective(entity);
	}

	@Override
	@Transactional("TestMixedDataSourceTx") // must be use tx manager belong to datasource
	public int saveOneAccountWithTransactional() {
		AccountSimpleEntity entity = this.genAccount();
		return accountSimpleDAO.insertSelective(entity);
	}

	@Override
	@Transactional("TestMixedDataSourceTx")
	public void saveOneAccountWithTxAndError() {
		AccountSimpleEntity entity = this.genAccount();
		accountSimpleDAO.insertSelective(entity);

		int i = 1 / 0;
	}

	@Override
	@Transactional("TestMixedDataSourceTx")
	public int saveTwoAccountWithTx() {
		AccountSimpleEntity entity = this.genAccount();
		int                 count  = accountSimpleDAO.insertSelective(entity);

		AccountSimpleService accountSimpleService = (AccountSimpleService) applicationContext.getBean("AccountSimpleService");
		count = count + accountSimpleService.saveOneAccountWithTransactional();

		return count;
	}

	private AccountSimpleEntity genAccount() {
		Date date = new Date();

		AccountSimpleEntity entity = new AccountSimpleEntity();
		entity.setAccountNo("AC" + Instant.now().toEpochMilli());
		entity.setUserName("USER_" + RandomUtils.nextInt());
		entity.setCreateTime(date);
		entity.setUpdateTime(date);

		return entity;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
