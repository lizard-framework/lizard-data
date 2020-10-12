package io.lizardframework.data.orm.fixture.ms.service;

import io.lizardframework.data.orm.fixture.ms.repository.entity.AccountMasterSlaveEntity;

/**
 * @author xueqi
 * @date 2020-10-11
 */
public interface AccountMasterSlaveService {

	/**
	 * save one account without transactional
	 */
	int saveOneAccount();

	/**
	 * save one account without transactional and use hint master model
	 *
	 * @return
	 */
	int saveOneAccountByHintMaster();

	/**
	 * save one account without transactional and use hint slave model
	 *
	 * @return
	 */
	int saveOneAccountByHintSlave();

	/**
	 * save one account with transactional and use hit slave
	 *
	 * @return
	 */
	int saveOneAccountByHintSlaveAndTx();

	/**
	 * save one account without transactional and use @MasterSlave(MASTER)
	 *
	 * @return
	 */
	int saveOneAccountByMaster();

	/**
	 * save one account without transactional and use @MasterSlave(SLAVE)
	 *
	 * @return
	 */
	int saveOneAccountBySlave();

	/**
	 * <code>
	 *
	 * @MasterSlave(MasterSlaveType.Master) method1() {
	 * doSave();
	 * @MasterSlave(MasterSlaveType.Slave) service.method2();
	 * }
	 * </code>
	 */
	void saveAccountByHintNest();

	/**
	 * select one with transactional and slave
	 *
	 * @param accountNo
	 * @return
	 */
	AccountMasterSlaveEntity selectOneByTxAndSlave(String accountNo);

	/**
	 * save one account and select one in transactional
	 *
	 * @param accountNo
	 * @return
	 */
	AccountMasterSlaveEntity saveAndSelectOneByTxNest(String accountNo);

}
