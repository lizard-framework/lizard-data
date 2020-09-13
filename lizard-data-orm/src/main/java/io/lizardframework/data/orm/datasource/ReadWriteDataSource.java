package io.lizardframework.data.orm.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Read Write DataSource
 *
 * @author xueqi
 * @date 2020-09-09
 */
public class ReadWriteDataSource extends RoutingDataSourceMBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReadWriteDataSource.class);

	@Override
	protected Object determineCurrentLookupKey() {
		String dskey = super.dataSourceKey.getDataSourceKey();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Select read/write atom datasource key is : {}", dskey);
		}

		return dskey;
	}
}
