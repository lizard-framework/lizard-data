package io.lizardframework.data.admin.service.mixed;

import io.lizardframework.data.admin.commons.pageable.PageResult;
import io.lizardframework.data.admin.controller.api.params.ORMGetMixedConfigParams;
import io.lizardframework.data.admin.controller.applicationConfig.datasource.params.MixedDataSourceListParam;
import io.lizardframework.data.admin.model.mixed.MixedDataSourceDetailModel;
import io.lizardframework.data.admin.model.mixed.MixedDataSourceInfoModel;
import io.lizardframework.data.orm.model.MixedDataSourceModel;

/**
 * @author xueqi
 * @date 2020-11-15
 */
public interface MixedDataSourceService {

	/**
	 * 分页查询数据源配置列表
	 *
	 * @param param
	 * @return
	 */
	PageResult<MixedDataSourceInfoModel> queryPage(MixedDataSourceListParam param);

	/**
	 * 根据MixedName查询详情信息
	 *
	 * @param mixedName
	 * @return
	 */
	MixedDataSourceDetailModel queryDetailByMixedName(String mixedName);

	/**
	 * 查询MixedDataSource配置
	 *
	 * @param params
	 * @return
	 */
	MixedDataSourceModel queryMixedDataSource(ORMGetMixedConfigParams params);
}
