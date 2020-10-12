package io.lizardframework.data.orm.fixture.ms;

import io.lizardframework.data.orm.AbstractSpringTest;
import io.lizardframework.data.orm.fixture.ms.repository.entity.AccountMasterSlaveEntity;
import io.lizardframework.data.orm.fixture.ms.service.AccountMasterSlaveService;
import io.lizardframework.data.utils.JSONUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.BadSqlGrammarException;

/**
 * @author xueqi
 * @date 2020-10-11
 */
public class AccountMasterSlaveServiceTest extends AbstractSpringTest {

	@Test
	public void saveOneAccountTest() {
		AccountMasterSlaveService service = super.getBean("AccountMasterSlaveService");

		int count = service.saveOneAccount();
		Assert.assertEquals(1, count);
	}

	@Test
	public void saveOneAccountByHintMasterTest() {
		AccountMasterSlaveService service = super.getBean("AccountMasterSlaveService");

		int count = service.saveOneAccountByHintMaster();
		Assert.assertEquals(1, count);
	}

	@Test(expected = BadSqlGrammarException.class)
	public void saveOneAccountByHintSlaveTest() {
		AccountMasterSlaveService service = super.getBean("AccountMasterSlaveService");

		service.saveOneAccountByHintSlave();
	}

	@Test
	public void saveOneAccountByHintSlaveAndTxTest() {
		AccountMasterSlaveService service = super.getBean("AccountMasterSlaveService");

		int count = service.saveOneAccountByHintSlaveAndTx();
		Assert.assertEquals(1, count);
	}

	@Test
	public void saveOneAccountByMasterTest() {
		AccountMasterSlaveService service = super.getBean("AccountMasterSlaveService");

		int count = service.saveOneAccountByMaster();
		Assert.assertEquals(1, count);
	}

	@Test(expected = BadSqlGrammarException.class)
	public void saveOneAccountBySlaveTest() {
		AccountMasterSlaveService service = super.getBean("AccountMasterSlaveService");

		int count = service.saveOneAccountBySlave();
		Assert.assertEquals(1, count);
	}

	@Test(expected = BadSqlGrammarException.class)
	public void saveAccountByNestTest() {
		AccountMasterSlaveService service = super.getBean("AccountMasterSlaveService");
		service.saveAccountByHintNest();
	}

	@Test
	public void selectOneByTxAndSlaveTest() {
		AccountMasterSlaveService service = super.getBean("AccountMasterSlaveService");

		for (int i = 0; i < 3; i++) {
			AccountMasterSlaveEntity entity = service.selectOneByTxAndSlave("AC1602311211195");
			System.out.println(JSONUtils.toJSONString(entity));
		}
	}

	@Test
	public void saveAndSelectOneByTxNestTest() {
		AccountMasterSlaveService service = super.getBean("AccountMasterSlaveService");

		for (int i = 0; i < 3; i++) {
			System.out.println("------------------------------------------");

			AccountMasterSlaveEntity entity = service.saveAndSelectOneByTxNest("AC1602311211195");
			System.out.println(JSONUtils.toJSONString(entity));

			System.out.println("------------------------------------------");
		}
	}
}
