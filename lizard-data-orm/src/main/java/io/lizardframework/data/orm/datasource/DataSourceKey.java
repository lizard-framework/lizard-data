package io.lizardframework.data.orm.datasource;

import io.lizardframework.data.orm.datasource.strategy.DataSourceStrategy;
import io.lizardframework.data.orm.datasource.strategy.StrategyHolder;
import io.lizardframework.data.orm.enums.ReadWriteType;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * DataSource Key
 *
 * @author xueqi
 * @date 2020-09-10
 */
@Slf4j
public class DataSourceKey {
	// 数据源所属的mix-data名称
	@Setter
	private String                             mixDataName;
	// repository与写数据源Bean-Name列表映射,每个group必须有且仅有一个有效的写数据源
	@Setter
	private Map<String, List<DataSourceMBean>> repositoryWriteAtomDsMapper = new HashMap<>();
	// repository与读数据源Bean-Name列表映射,每个group不一定要有多个读数据源
	@Setter
	private Map<String, List<DataSourceMBean>> repositoryReadAtomDsMapper  = new HashMap<>();

	public DataSourceKey(String mixDataName) {
		this.mixDataName = mixDataName;
	}

	/**
	 * 获取读写数据源key值
	 *
	 * @return
	 */
	public String getDataSourceKey() {
		Stack<DataSourceStrategy> strategyStack = StrategyHolder.getStrategyStack();

		// 从ThreadLocal中获取strategy栈不为空（注解拦截场景和使用Hint的方式）
		DataSourceStrategy strategy      = null;
		String             shardingKey   = null;
		String             dataSourceKey = null;
		if (!CollectionUtils.isEmpty(strategyStack)) {
			strategy = strategyStack.peek();
			shardingKey = strategy.getRepositoryShardingKey();

			dataSourceKey = strategy.getDataSourceKey();
			if (!StringUtils.isEmpty(dataSourceKey)) {
				log.debug("Mix-Data:{} get datasource key from threadlocal stack head:{}", mixDataName, dataSourceKey);
				return dataSourceKey;
			}
		}

		// .... 栈为空或datasource key不存在场景 .... //

		// 如果分库sharding key不存在，必须只能有一个repository，单库读写分离的场景
		if (StringUtils.isEmpty(shardingKey)) {
			if (this.repositoryWriteAtomDsMapper.size() > 1) {
				throw new IllegalArgumentException("Can not select read/write key. because mix-data have more than 1 group.");
			}

			// 获取读写类型
			ReadWriteType readWriteType = (strategy == null) ? ReadWriteType.WRITE : strategy.getReadWriteType();
			if (ReadWriteType.WRITE.equals(readWriteType)) {
				// 默认获取第一个主库atom datasource bean name
				dataSourceKey = this.getOneWriteDataSource().getBeanName();
			} else {
				// todo: loadbalance
				dataSourceKey = repositoryReadAtomDsMapper.entrySet().iterator().next().getValue().get(0).getBeanName();
			}

			// strategy的创建在拦截器中完成，如果strategy为null，直接返回dataSourceKey
			if (strategy != null) {
				// 从当前栈顶获取的策略，将datasource key写入
				strategy.setDataSourceKey(dataSourceKey);
			}

			log.debug("Mix-Data:{} sharding key is null. Single repository read/write datasource key:{}", mixDataName, dataSourceKey);
			return dataSourceKey;
		}

		// 如果分库sharding key存在（一定是从stack中获取的），从group的ds mapper中获取数据源bean name
		ReadWriteType readWriteType = strategy.getReadWriteType();
		if (readWriteType == null || ReadWriteType.WRITE.equals(readWriteType)) {
			// 默认获取第一个主库atom datasource bean name
			dataSourceKey = this.getOneWriteDataSource().getBeanName();
			// 针对readWriteType==null的情况，即只有@RepositorySharding注解，默认ReadWriteType.WRITE，并写入到strategy中
			strategy.setReadWriteType(ReadWriteType.WRITE);
		} else {
			// todo: loadbalance
			dataSourceKey = repositoryReadAtomDsMapper.get(shardingKey).get(0).getBeanName();
		}
		strategy.setDataSourceKey(dataSourceKey);

		log.debug("Mix-Data:{} sharding key is not empty. Sharding repository and read/write datasource key:{}", mixDataName, dataSourceKey);
		return dataSourceKey;
	}

	/**
	 * get repository sharding key
	 *
	 * @return
	 */
	public String getRepositoryShardingKey() {
		Stack<DataSourceStrategy> stack = StrategyHolder.getStrategyStack();
		if (CollectionUtils.isEmpty(stack)) {
			return null;
		}

		DataSourceStrategy strategy = stack.peek();
		return strategy.getRepositoryShardingKey();
	}

	/**
	 * get one write datasource from repositoryWriteAtomDsMapper
	 *
	 * @return
	 */
	private DataSourceMBean getOneWriteDataSource() {
		return repositoryWriteAtomDsMapper.entrySet().iterator().next().getValue().get(0);
	}
}
