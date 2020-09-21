package io.lizardframework.data.orm.datasource.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.Stack;

/**
 * 数据源选择策略ThreadLocal Holder
 *
 * @author xueqi
 * @date 2020-09-21
 */
@Slf4j
public class StrategyHolder {

	// 保存线程DataSourceStrategy栈
	private static final ThreadLocal<Stack<DataSourceStrategy>> DATASOURCE_STRATEGY_STACK = new ThreadLocal<>();

	/**
	 * 获取线程DataSourceStrategy栈
	 *
	 * @return
	 */
	public static Stack<DataSourceStrategy> getStrategyStack() {
		return DATASOURCE_STRATEGY_STACK.get();
	}

	/**
	 * 将DataSourceStrategy加入到当前线程ThreadLocal
	 *
	 * @param strategy
	 */
	public static void addDataSourceStrategy(DataSourceStrategy strategy) {
		Stack<DataSourceStrategy> stack = DATASOURCE_STRATEGY_STACK.get();
		if (stack == null) {
			stack = new Stack<>();
		}

		stack.push(strategy);
		DATASOURCE_STRATEGY_STACK.set(stack);
	}

	/**
	 * 移除DataSourceStrategy
	 */
	public static void removeDataSourceStrategy() {
		Stack<DataSourceStrategy> stack = DATASOURCE_STRATEGY_STACK.get();
		if (CollectionUtils.isEmpty(stack)) return;

		stack.pop();
		// 判断当前栈是否为空，如果为空从ThreadLocal中移除
		if (CollectionUtils.isEmpty(stack)) {
			log.debug("Remove datasource key from thread local.");
			DATASOURCE_STRATEGY_STACK.remove();
		}
	}

	/**
	 * 当前线程是否在事务中
	 *
	 * <p>如果在事务中，根据事务的特性连接是不会再次改变，所以无需addDataSourceStrategy到当前线程栈</p>
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

	/**
	 * 获取当前ThreadLocal中的数据源策略
	 *
	 * @return
	 */
	public static DataSourceStrategy getDataSourceStrategy() {
		Stack<DataSourceStrategy> stack = DATASOURCE_STRATEGY_STACK.get();
		if (CollectionUtils.isEmpty(stack)) {
			return null;
		}

		return stack.peek();
	}
}
