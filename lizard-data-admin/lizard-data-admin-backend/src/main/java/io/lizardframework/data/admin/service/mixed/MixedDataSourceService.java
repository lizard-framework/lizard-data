package io.lizardframework.data.admin.service.mixed;

import io.lizardframework.data.admin.commons.pageable.PageResult;
import io.lizardframework.data.admin.controller.applicationConfig.datasource.params.MixedDataSourceListParam;
import io.lizardframework.data.admin.model.mixed.MixedDataSourceInfoModel;

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

}
