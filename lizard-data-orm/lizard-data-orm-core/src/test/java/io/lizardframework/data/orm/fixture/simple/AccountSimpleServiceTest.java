package io.lizardframework.data.orm.fixture.simple;

import io.lizardframework.data.orm.AbstractSpringTest;
import io.lizardframework.data.orm.fixture.simple.service.AccountSimpleService;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author xueqi
 * @date 2020-10-10
 */
public class AccountSimpleServiceTest extends AbstractSpringTest {

	@Test
	public void saveOneAccountTest() {
		AccountSimpleService accountSimpleService = super.getBean("AccountSimpleService");

		int count = accountSimpleService.saveOneAccount();
		Assert.assertEquals(1, count);
	}

	@Test
	public void saveOneAccountWithTransactionalTest() {
		AccountSimpleService accountSimpleService = super.getBean("AccountSimpleService");

		int count = accountSimpleService.saveOneAccountWithTransactional();
		Assert.assertEquals(1, count);
	}

	@Test(expected = Exception.class)
	public void saveOneAccountWithTxAndErrorTest() {
		AccountSimpleService accountSimpleService = super.getBean("AccountSimpleService");

		accountSimpleService.saveOneAccountWithTxAndError();
	}

	@Test
	public void saveTwoAccountWithTxTest() {
		AccountSimpleService accountSimpleService = super.getBean("AccountSimpleService");

		int count = accountSimpleService.saveTwoAccountWithTx();
		Assert.assertEquals(2, count);
	}
}
