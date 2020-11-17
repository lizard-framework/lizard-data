package io.lizardframework.data.admin.service.resources;

import io.lizardframework.data.admin.commons.pageable.PageResult;
import io.lizardframework.data.admin.controller.resourcesManager.database.params.DataBaseAddParam;
import io.lizardframework.data.admin.controller.resourcesManager.database.params.DataBaseListParam;
import io.lizardframework.data.admin.model.resources.DatabaseInfoModel;

/**
 * @author xueqi
 * @date 2020-11-15
 */
public interface DatabaseResourceService {

	/**
	 * 分页查询数据库资源信息
	 *
	 * @param param
	 * @return
	 */
	PageResult<DatabaseInfoModel> queryPage(DataBaseListParam param);

	/**
	 * 保存数据库信息
	 *
	 * @param param
	 */
	void save(DataBaseAddParam param);

	/**
	 * 根据id查询数据库基础信息（不包括用户名密码）
	 *
	 * @param id
	 * @return
	 */
	DatabaseInfoModel queryBasicInfo(Long id);

	/**
	 * 根据id查询数据库连接用户名密码信息
	 *
	 * @param id
	 * @return
	 */
	DatabaseInfoModel queryAuthInfo(Long id);
}
