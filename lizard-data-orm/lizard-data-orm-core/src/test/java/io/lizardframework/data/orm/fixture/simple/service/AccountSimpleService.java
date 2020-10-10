package io.lizardframework.data.orm.fixture.simple.service;

/**
 * @author xueqi
 * @date 2020-10-10
 */
public interface AccountSimpleService {

	/**
	 * save one account without transactional
	 *
	 * @return
	 */
	int saveOneAccount();

	/**
	 * save one account with transactional
	 *
	 * @return
	 */
	int saveOneAccountWithTransactional();

	/**
	 * save one account with transactional and throw exception. The tx must be rollback
	 */
	void saveOneAccountWithTxAndError();

	/**
	 * save two account with transactional
	 */
	int saveTwoAccountWithTx();
}
