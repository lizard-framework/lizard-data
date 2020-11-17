package io.lizardframework.data.admin.service;

import io.lizardframework.data.admin.controller.api.params.ORMGetMixedConfigParams;
import io.lizardframework.data.admin.model.mixed.MixedDataSourceDetailModel;
import io.lizardframework.data.orm.model.MixedDataSourceModel;

/**
 * @author xueqi
 * @date 2020-09-25
 */
public interface OrmMixedService {

	/**
	 * query mixed-datasource config
	 *
	 * @param params
	 * @return
	 */
	MixedDataSourceModel queryMixedDataSource(ORMGetMixedConfigParams params);



	MixedDataSourceDetailModel queryDetailByMixedName(String mixedName);
}
