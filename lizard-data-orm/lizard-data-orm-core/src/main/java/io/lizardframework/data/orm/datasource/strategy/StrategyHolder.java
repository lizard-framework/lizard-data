package io.lizardframework.data.orm.datasource.strategy;

import io.lizardframework.data.utils.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Stack;

/**
 * DataSource Selector Strategy ThreadLocal Holder
 *
 * @author xueqi
 * @date 2020-09-21
 */
@Slf4j
public class StrategyHolder {

	// save thread DataSourceStrategy stack
	private static final ThreadLocal<Stack<DataSourceStrategy>>  DATASOURCE_STRATEGY_STACK     = new ThreadLocal<>();
	// save thread Table Sharding stack
	private static final ThreadLocal<Stack<Map<String, String>>> TABLE_SHARDING_STRATEGY_STACK = new ThreadLocal<>();

	public static Stack<DataSourceStrategy> getStrategyStack() {
		return DATASOURCE_STRATEGY_STACK.get();
	}

	public static void addDataSourceStrategy(DataSourceStrategy strategy) {
		Stack<DataSourceStrategy> stack = DATASOURCE_STRATEGY_STACK.get();
		if (stack == null) {
			stack = new Stack<>();
		}

		stack.push(strategy);
		DATASOURCE_STRATEGY_STACK.set(stack);
	}

	public static void removeDataSourceStrategy() {
		Stack<DataSourceStrategy> stack = DATASOURCE_STRATEGY_STACK.get();
		if (CollectionUtils.isEmpty(stack)) return;

		DataSourceStrategy strategy = stack.pop();
		log.debug("DataSource strategy: '{}' has been clean from thread locak stack.", strategy);

		if (CollectionUtils.isEmpty(stack)) {
			log.debug("Datasource strategy stack has been clean from thread local.");
			DATASOURCE_STRATEGY_STACK.remove();
		}
	}

	/**
	 * check ccurrent thread in transaction
	 *
	 * <p>if in transaction，connection not change，needn't add DataSourceStrategy in thread stack</p>
	 *
	 * @return
	 */
	public static boolean hasTransactional() {
		Stack<DataSourceStrategy> stack = DATASOURCE_STRATEGY_STACK.get();
		if (CollectionUtils.isEmpty(stack)) {
			return false;
		}

		DataSourceStrategy strategy = stack.peek();
		return strategy.isTransaction();
	}


	public static DataSourceStrategy getDataSourceStrategy() {
		Stack<DataSourceStrategy> stack = DATASOURCE_STRATEGY_STACK.get();
		if (CollectionUtils.isEmpty(stack)) {
			return null;
		}

		return stack.peek();
	}

	// ------- Table Sharding -------- //

	public static Map<String, String> getTableShardingStrategy() {
		Stack<Map<String, String>> stack = TABLE_SHARDING_STRATEGY_STACK.get();
		if (CollectionUtils.isEmpty(stack)) {
			return null;
		}

		return stack.peek();
	}

	public static void addTableShardingStrategy(Map<String, String> strategy) {
		Stack<Map<String, String>> stack = TABLE_SHARDING_STRATEGY_STACK.get();
		if (stack == null) {
			stack = new Stack<>();
		}

		stack.push(strategy);
		TABLE_SHARDING_STRATEGY_STACK.set(stack);
	}

	public static void removeTableShardingStrategy() {
		Stack<Map<String, String>> stack = TABLE_SHARDING_STRATEGY_STACK.get();
		if (CollectionUtils.isEmpty(stack)) return;

		Map<String, String> strategy = stack.pop();
		if (log.isDebugEnabled())
			log.debug("TableSharding strategy: '{}' has been clean from thread locak stack.", JSONUtils.toJSONString(strategy));

		if (CollectionUtils.isEmpty(stack)) {
			log.debug("TableSharding strategy stack has been clean from thread local.");

			TABLE_SHARDING_STRATEGY_STACK.remove();
		}
	}
}
