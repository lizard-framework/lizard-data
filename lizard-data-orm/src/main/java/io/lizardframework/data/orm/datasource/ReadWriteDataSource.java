package io.lizardframework.data.orm.datasource;

import lombok.extern.slf4j.Slf4j;

/**
 * Read Write DataSource
 *
 * @author xueqi
 * @date 2020-09-09
 */
@Slf4j
public class ReadWriteDataSource extends RoutingDataSourceMBean {

	@Override
	protected Object determineCurrentLookupKey() {
		String dskey = super.dataSourceKey.getDataSourceKey();
		if (log.isDebugEnabled()) {
			log.debug("Select read/write atom datasource key is : {}", dskey);
		}

		return dskey;
	}
}
