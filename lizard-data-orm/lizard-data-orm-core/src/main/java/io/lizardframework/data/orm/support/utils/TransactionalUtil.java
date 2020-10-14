package io.lizardframework.data.orm.support.utils;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xueqi
 * @date 2020-10-14
 */
public class TransactionalUtil {

	/**
	 * check transaction with spring @Transactional
	 *
	 * @param transactional
	 * @param currentHasTx
	 * @return
	 */
	public static boolean hasTx(Transactional transactional, boolean currentHasTx) {
		if (transactional == null) return false;

		Propagation propagation = transactional.propagation();
		// NOT_SUPPORTED: 当前方法不应该有事务，如果有事务存在，将它挂起，当前方法以无事务状态运行
		// NEVER: 当前方法不应该运行在事物中,如果有事务就抛出异常
		if (Propagation.NOT_SUPPORTED.equals(propagation) || Propagation.NEVER.equals(propagation)) {
			return false;
		}

		// SUPPORTS
		if (Propagation.SUPPORTS.equals(propagation)) {
			return currentHasTx;
		}

		// REQUIRED | NESTED | REQUIRES_NEW | MANDATORY
		return true;
	}

	public static boolean allowChangeConnection(Transactional transactional) {
		if (transactional == null) return true;

		Propagation propagation = transactional.propagation();
		if (Propagation.NOT_SUPPORTED.equals(propagation) || Propagation.REQUIRES_NEW.equals(propagation))
			return true;

		return false;
	}
}
