package io.lizardframework.data.admin.service;

import io.lizardframework.data.orm.model.MixedDataSourceModel;

/**
 * @author xueqi
 * @date 2020-09-25
 */
public interface ORMDataService {

	/**
	 * query mixed-datasource config
	 *
	 * @param mixedDataSourceName
	 * @return
	 */
	MixedDataSourceModel queryMixedDataSource(String mixedDataSourceName);

}
