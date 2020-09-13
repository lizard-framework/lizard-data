package io.lizardframework.data.orm.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Repository Sharding DataSource
 *
 * @author xueqi
 * @date 2020-09-08
 */
public class RepositoryShardingDataSource extends RoutingDataSourceMBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryShardingDataSource.class);

	@Override
	protected Object determineCurrentLookupKey() {
		String shardingkey = super.dataSourceKey.getRepositoryShardingKey();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Select respository datasource key is : {}", shardingkey);
		}

		return shardingkey;
	}

}
