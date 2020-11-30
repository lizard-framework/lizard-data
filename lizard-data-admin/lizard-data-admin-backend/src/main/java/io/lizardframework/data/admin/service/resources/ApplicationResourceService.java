package io.lizardframework.data.admin.service.resources;

import io.lizardframework.data.admin.commons.pageable.PageResult;
import io.lizardframework.data.admin.controller.resourcesManager.application.params.ApplicationAddParam;
import io.lizardframework.data.admin.controller.resourcesManager.application.params.ApplicationListParam;
import io.lizardframework.data.admin.model.resources.ApplicationInfoModel;

import java.util.List;

/**
 * @author xueqi
 * @date 2020-11-15
 */
public interface ApplicationResourceService {

	/**
	 * 分页查询应用列表
	 *
	 * @param param
	 * @return
	 */
	PageResult<ApplicationInfoModel> queryPage(ApplicationListParam param);

	/**
	 * 根据应用名称模糊查询
	 *
	 * @param applicationName
	 * @return
	 */
	List<String> queryNameByLikeFuzzy(String applicationName);

	/**
	 * 保存应用记录
	 *
	 * @param param
	 */
	void save(ApplicationAddParam param);
}
