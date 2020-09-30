package io.lizardframework.data.orm.datasource;

import lombok.extern.slf4j.Slf4j;

/**
 * Repository Sharding DataSource
 *
 * @author xueqi
 * @date 2020-09-08
 */
@Slf4j
public class RepositoryShardingDataSource extends BaseRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		String shardingkey = super.dataSourceKey.getDataSourceKey();
		if (log.isDebugEnabled()) {
			log.debug("Select respository datasource key is : {}", shardingkey);
		}

		return shardingkey;
	}

}
