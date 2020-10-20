package io.lizardframework.data.admin.service;

import io.lizardframework.data.admin.commons.PageableResp;
import io.lizardframework.data.admin.controller.operator.application.params.OrmMixedListParam;
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
	 * @param mixedDataSourceName
	 * @return
	 */
	MixedDataSourceModel queryMixedDataSource(String mixedDataSourceName);


	PageableResp<List<OrmMixedInfoModel>> queryPage(OrmMixedListParam param);
}
