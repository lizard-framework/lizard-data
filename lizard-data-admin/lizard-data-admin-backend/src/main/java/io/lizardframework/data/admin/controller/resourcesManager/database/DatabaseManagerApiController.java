package io.lizardframework.data.admin.controller.resourcesManager.database;

import io.lizardframework.data.admin.commons.Resp;
import io.lizardframework.data.admin.commons.pageable.PageResult;
import io.lizardframework.data.admin.commons.pageable.PageableResp;
import io.lizardframework.data.admin.controller.resourcesManager.database.params.DataBaseAddParam;
import io.lizardframework.data.admin.controller.resourcesManager.database.params.DataBaseListParam;
import io.lizardframework.data.admin.model.resources.DatabaseInfoModel;
import io.lizardframework.data.admin.service.resources.DatabaseResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 资源管理 - 数据库管理 api controller
 *
 * @author xueqi
 * @date 2020-11-10
 */
@RestController
@RequestMapping("/api/resources-manager/database")
public class DatabaseManagerApiController {

	@Autowired
	private DatabaseResourceService databaseResourceService;

	/**
	 * 查询数据库资源列表
	 *
	 * @param param
	 * @return
	 */
	@GetMapping(value = "list")
	public PageableResp<List<DatabaseInfoModel>> list(DataBaseListParam param) {
		PageResult<DatabaseInfoModel>         result   = databaseResourceService.queryPage(param);
		PageableResp<List<DatabaseInfoModel>> response = new PageableResp<>(result.getCount(), result.getData());

		return response;
	}

	/**
	 * 添加数据库
	 *
	 * @param param
	 * @return
	 */
	@PutMapping(value = "save")
	public Resp add(@RequestBody DataBaseAddParam param) {
		databaseResourceService.save(param);

		return new Resp();
	}


	/**
	 * 根据数据库id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "detail/{id}")
	public Resp<DatabaseInfoModel> queryBasicInfo(@PathVariable Long id) {
		DatabaseInfoModel info = databaseResourceService.queryBasicInfo(id);

		return new Resp<>(info);
	}

	/**
	 * 根据数据库id查询密码信息
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "authinfo/{id}")
	public Resp<DatabaseInfoModel> queryAuthInfo(@PathVariable Long id) {
		DatabaseInfoModel info = databaseResourceService.queryAuthInfo(id);

		return new Resp<>(info);
	}
}
