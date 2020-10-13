package io.lizardframework.data.orm.datasource;

import io.lizardframework.data.enums.LoadBalanceType;
import io.lizardframework.data.enums.MasterSlaveType;
import io.lizardframework.data.loadbalance.LoadBalanceAlgorithm;
import io.lizardframework.data.loadbalance.LoadBalanceAlgorithmFactory;
import io.lizardframework.data.orm.datasource.meta.DataSourceMBean;
import io.lizardframework.data.orm.datasource.strategy.DataSourceStrategy;
import io.lizardframework.data.orm.datasource.strategy.StrategyHolder;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
@AllArgsConstructor
public class DataSourceKey {
	// 数据源所属的MixedDataSource名称
	@Setter
	private String                             mixedDataSourceName;
	// repository与写数据源Bean-Name列表映射,每个group必须有且仅有一个有效的写数据源
	@Setter
	private Map<String, List<DataSourceMBean>> repositoryMasterAtomDsMapper;
	// repository与读数据源Bean-Name列表映射,每个group不一定要有多个读数据源
	@Setter
	private Map<String, List<DataSourceMBean>> repositorySlaveAtomDsMapper;
	// repository loadbalance mapper
	@Setter
	private Map<String, LoadBalanceType>       repositoryLoadBalanceMapper;

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
				log.debug("MixedDataSource: '{}' get datasource key from threadlocal stack head: '{}'", mixedDataSourceName, dataSourceKey);
				return dataSourceKey;
			}
		}

		// .... 栈为空或datasource key不存在场景 .... //

		// 如果分库sharding key不存在，必须只能有一个repository，单库读写分离的场景
		if (StringUtils.isEmpty(shardingKey)) {

			// 获取读写类型
			MasterSlaveType masterSlaveType = (strategy == null) ? MasterSlaveType.MASTER : strategy.getMasterSlaveType();
			if (MasterSlaveType.MASTER.equals(masterSlaveType)) {
				dataSourceKey = this.selectorOneMasterDataSource().getBeanName();
			} else {
				dataSourceKey = this.selectorOneSlaveDataSource().getBeanName();
			}

			// strategy的创建在拦截器中完成，如果strategy为null，直接返回dataSourceKey
			if (strategy != null) {
				// 从当前栈顶获取的策略，将datasource key写入
				strategy.setDataSourceKey(dataSourceKey);
				log.debug("MixedDataSource: '{}' sharding key is null, use strategy: '{}'", mixedDataSourceName, strategy);
			} else {
				log.debug("MixedDataSource: '{}' sharding key is null, non strategy, datasource key: '{}'", mixedDataSourceName, dataSourceKey);
			}

			return dataSourceKey;
		}

		// 如果分库sharding key存在（一定是从stack中获取的），从group的ds mapper中获取数据源bean name
		MasterSlaveType masterSlaveType = strategy.getMasterSlaveType();
		if (masterSlaveType == null || MasterSlaveType.MASTER.equals(masterSlaveType)) {
			dataSourceKey = this.selectorMasterDataSource(shardingKey).getBeanName();

			// 针对readWriteType==null的情况，即只有@RepositorySharding注解，默认ReadWriteType.WRITE，并写入到strategy中
			log.debug("MixedDataSource: '{}' sharding key exist, setting master/slave type:'{}' to '{}'", mixedDataSourceName, MasterSlaveType.MASTER, strategy);

			strategy.setMasterSlaveType(MasterSlaveType.MASTER);
		} else {
			dataSourceKey = this.selectorSlaveDataSource(shardingKey).getBeanName();
		}
		strategy.setDataSourceKey(dataSourceKey);

		log.debug("MixedDataSource: '{}' sharding key exist, use strategy: '{}'", mixedDataSourceName, dataSourceKey);
		return dataSourceKey;
	}

	// --------//

	/**
	 * get master DataSource by sharding key
	 * <p>
	 * default selector first master datasource
	 *
	 * @param shardingKey
	 * @return
	 */
	private DataSourceMBean selectorMasterDataSource(String shardingKey) {
		List<DataSourceMBean> masterList = this.repositoryMasterAtomDsMapper.get(shardingKey);
		if (CollectionUtils.isEmpty(masterList)) {
			throw new IllegalArgumentException("master datasource list is empty. repository: " + shardingKey);
		}

		DataSourceMBean dataSourceMBean = masterList.get(0);
		log.debug("MixedDataSource: '{}' select master datasource: '{}'", mixedDataSourceName, dataSourceMBean);
		return dataSourceMBean;
	}

	/**
	 * get one master DataSource
	 * <p>
	 * default selector first repositoryMasterAtomDsMapper repository
	 *
	 * @return
	 */
	private DataSourceMBean selectorOneMasterDataSource() {
		if (CollectionUtils.isEmpty(this.repositoryMasterAtomDsMapper) || this.repositoryMasterAtomDsMapper.size() != 1) {
			throw new IllegalArgumentException("repository master datasource mapper size must be one.");
		}

		String shardingKey = this.repositoryMasterAtomDsMapper.keySet().iterator().next();
		return selectorMasterDataSource(shardingKey);
	}

	/**
	 * get slave DataSource by sharding key
	 *
	 * @param shardingKey
	 * @return
	 */
	private DataSourceMBean selectorSlaveDataSource(String shardingKey) {
		List<DataSourceMBean> slaveList = this.repositorySlaveAtomDsMapper.get(shardingKey);
		if (CollectionUtils.isEmpty(slaveList)) {
			throw new IllegalArgumentException("slave datasource list is empty. repository: " + shardingKey);
		}

		LoadBalanceType      loadBalanceType = this.repositoryLoadBalanceMapper.get(shardingKey);
		LoadBalanceAlgorithm algorithm       = LoadBalanceAlgorithmFactory.getAlgorithm(loadBalanceType);

		DataSourceMBean dataSourceMBean = algorithm.selector(shardingKey, slaveList);

		log.debug("MixedDataSource: '{}' select slave datasource: '{}', load_balance: '{}'", mixedDataSourceName, dataSourceMBean, loadBalanceType);
		return dataSourceMBean;
	}

	/**
	 * get one slave DataSource
	 * <p>
	 * default selector first repositorySlaveAtomDsMapper repository
	 *
	 * @return
	 */
	private DataSourceMBean selectorOneSlaveDataSource() {
		if (CollectionUtils.isEmpty(repositorySlaveAtomDsMapper) || this.repositorySlaveAtomDsMapper.size() != 1) {
			throw new IllegalArgumentException("repository slave datasource mapper size must be one.");
		}

		String shardingKey = this.repositorySlaveAtomDsMapper.keySet().iterator().next();
		return selectorSlaveDataSource(shardingKey);
	}
}
