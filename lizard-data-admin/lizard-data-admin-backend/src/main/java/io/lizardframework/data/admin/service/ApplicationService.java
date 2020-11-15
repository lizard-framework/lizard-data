package io.lizardframework.data.admin.service;

import io.lizardframework.data.admin.commons.pageable.PageableResp;
import io.lizardframework.data.admin.controller.resourcesManager.application.params.ApplicationAddParam;
import io.lizardframework.data.admin.controller.resourcesManager.application.params.ApplicationListParam;
import io.lizardframework.data.admin.model.ApplicationInfoModel;

import java.util.List;

/**
 * @author xueqi
 * @date 2020-10-29
 */
public interface ApplicationService {

	PageableResp<List<ApplicationInfoModel>> queryPage(ApplicationListParam param);

	void save(ApplicationAddParam param);

	List<ApplicationInfoModel> queryAll();
}
