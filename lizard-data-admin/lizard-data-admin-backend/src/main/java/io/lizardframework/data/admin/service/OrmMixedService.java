package io.lizardframework.data.admin.service;

import io.lizardframework.data.admin.commons.pageable.PageableResp;
import io.lizardframework.data.admin.controller.api.params.ORMGetMixedConfigParams;
import io.lizardframework.data.admin.controller.applicationConfig.datasource.params.OrmMixedListParam;
import io.lizardframework.data.admin.model.OrmMixedDetailModel;
import io.lizardframework.data.admin.model.OrmMixedInfoModel;
import io.lizardframework.data.orm.model.MixedDataSourceModel;

import java.util.List;

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


	PageableResp<List<OrmMixedInfoModel>> queryPage(OrmMixedListParam param);

	OrmMixedDetailModel queryDetailByMixedName(String mixedName);
}
