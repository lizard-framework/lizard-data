package io.lizardframework.data.orm.hint;

/**
 * Manual set DataSourceStrategy or TableShardingStrategy
 * <p>
 * if used this function，you must manual remove it
 *
 * @author xueqi
 * @date 2020-09-30
 */
public interface HintSupport<T> {

	/**
	 * set Strategy in thread local
	 *
	 * @param strategy
	 */
	void addStrategy(T strategy);

	/**
	 * clear Strategy in thread local
	 */
	void clear();
}
