package io.lizardframework.data.orm.fixture.sharding.strategy;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * order table sharding strategy, use java static method model
 *
 * @author xueqi
 * @date 2020-10-12
 */
public class OrderTableShardingStrategy {

	private static final String T_ORDER       = "t_order";
	private static final String T_TRANSACTION = "t_transaction";

	// calc table index
	public static String calcIndex(String orderNo) {
		int hashCode = orderNo.hashCode();
		int index    = (Math.abs(hashCode) % 3) + 1;
		return String.format("%02d", index);
	}

	/**
	 * use last two num
	 *
	 * @param orderNo
	 * @return key: table sharding placeholder value: replace value
	 */
	public static Map<String, String> strategy(String orderNo) {
		Map<String, String> strategy = new HashMap<>();

		String tableIndex = StringUtils.substring(orderNo, orderNo.length() - 2, orderNo.length());
		strategy.put(T_ORDER, T_ORDER + "_" + tableIndex);
		strategy.put(T_TRANSACTION, T_TRANSACTION + "_" + tableIndex);

		return strategy;
	}
}
