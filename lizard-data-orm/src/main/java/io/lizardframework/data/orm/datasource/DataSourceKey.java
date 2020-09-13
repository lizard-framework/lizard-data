package io.lizardframework.data.orm.datasource;

import io.lizardframework.data.orm.datasource.strategy.DataSourceStrategy;
import io.lizardframework.data.orm.enums.ReadWriteType;
import lombok.Setter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * DataSource Key : 获取数据源Key工具类、LB
 *
 * @author xueqi
 * @date 2020-09-10
 */
public class DataSourceKey {
	// 保存线程DataSourceStrategy栈
	private static final ThreadLocal<Stack<DataSourceStrategy>> DATASOURCE_STRATEGY_STACK = new ThreadLocal<>();

	// 数据源所属的mix-data名称
	@Setter
	private String                    mixDataName;
	// group与写数据源Bean-Name列表映射,每个group必须有且仅有一个有效的写数据源
	@Setter
	private Map<String, List<String>> groupWriteAtomDsMapper = new HashMap<>();
	// group与读数据源Bean-Name列表映射,每个group不一定要有多个读数据源
	@Setter
	private Map<String, List<String>> groupReadAtomDsMapper  = new HashMap<>();

	/**
	 * 获取读写数据源key值
	 *
	 * @return
	 */
	public String getDataSourceKey() {
		Stack<DataSourceStrategy> strategyStack = DATASOURCE_STRATEGY_STACK.get();

		// 从ThreadLocal中获取strategy栈不为空（注解拦截场景和使用Hint的方式）
		DataSourceStrategy strategy      = null;
		String             shardingKey   = null;
		String             dataSourceKey = null;
		if (!CollectionUtils.isEmpty(strategyStack)) {
			strategy = strategyStack.peek();
			shardingKey = strategy.getRepositoryShardingKey();

			dataSourceKey = strategy.getDataSourceKey();
			if (!StringUtils.isEmpty(dataSourceKey)) {
				return dataSourceKey;
			}
		}

		// .... 栈为空或datasource key不存在场景 .... //

		// 如果分库sharding key不存在，那边必须只能有一个group，单库读写分离的场景
		if (shardingKey == null) {
			if (this.groupWriteAtomDsMapper.size() > 1) {
				throw new IllegalArgumentException("Can not select read/write key. because mix-data have more than 1 group.");
			}

			// 获取读写类型
			ReadWriteType readWriteType = (strategy == null) ? ReadWriteType.WRITE : strategy.getReadWriteType();
			if (ReadWriteType.WRITE.equals(readWriteType)) {
				// 默认获取第一个主库atom datasource bean name
				dataSourceKey = groupWriteAtomDsMapper.entrySet().iterator().next().getValue().get(0);
			} else {
				// todo: loadbalance
				dataSourceKey = groupReadAtomDsMapper.entrySet().iterator().next().getValue().get(0);
			}

			if (strategy != null) {
				// 从当前栈顶获取的策略，将datasource key写入
				strategy.setDataSourceKey(dataSourceKey);
			} else {
				// 如果 strategy 为null，栈为空，单库且读取主库的场景；只有不使用@ReadWrite和Hint的场景会出现该问题，由于没有配置拦截器，该场景的datasource key直接返回，否则写入ThreadLocal中是无法回收的
				return dataSourceKey;
			}
		}

		// 如果分库sharding key存在（一定是从stack中获取的），从group的ds mapper中获取数据源bean name
		ReadWriteType readWriteType = strategy.getReadWriteType();
		if (ReadWriteType.WRITE.equals(readWriteType)) {
			// 默认获取第一个主库atom datasource bean name
			dataSourceKey = groupWriteAtomDsMapper.get(shardingKey).get(0);
		} else {
			// todo: loadbalance
			dataSourceKey = groupReadAtomDsMapper.get(shardingKey).get(0);
		}
		strategy.setDataSourceKey(dataSourceKey);
		return dataSourceKey;
	}

	/**
	 * 获取分库数据源key值
	 *
	 * @return
	 */
	public String getRepositoryShardingKey() {
		Stack<DataSourceStrategy> strategyStack = DATASOURCE_STRATEGY_STACK.get();
		if (!CollectionUtils.isEmpty(strategyStack)) {
			DataSourceStrategy strategy = strategyStack.peek();
			return strategy.getRepositoryShardingKey();
		}
		return null;
	}
}
