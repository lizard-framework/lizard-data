package io.lizardframework.data.admin.controller.resourcesManager.database;

import io.lizardframework.data.admin.commons.PageableResp;
import io.lizardframework.data.admin.commons.Resp;
import io.lizardframework.data.admin.controller.resourcesManager.database.params.DataBaseAddParam;
import io.lizardframework.data.admin.controller.resourcesManager.database.params.DataBaseListParam;
import io.lizardframework.data.admin.model.DataBaseInfoModel;
import io.lizardframework.data.admin.service.DatabaseService;
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
	private DatabaseService databaseService;

	/**
	 * 查询数据库资源列表
	 *
	 * @param param
	 * @return
	 */
	@GetMapping(value = "list")
	public PageableResp<List<DataBaseInfoModel>> list(DataBaseListParam param) {
		PageableResp<List<DataBaseInfoModel>> data = databaseService.queryPage(param);
		return data;
	}

	/**
	 * 添加数据库列表
	 *
	 * @param param
	 * @return
	 */
	@PutMapping(value = "save")
	public Resp add(@RequestBody DataBaseAddParam param) {
		databaseService.save(param);
		return new Resp();
	}


	/**
	 * 根据数据库id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "detail/{id}")
	public Resp<DataBaseInfoModel> queryBasicInfo(@PathVariable Long id) {
		DataBaseInfoModel info = databaseService.queryBasicById(id);
		return new Resp<>(info);
	}

	/**
	 * 根据数据库id查询密码信息
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "authinfo/{id}")
	public Resp<DataBaseInfoModel> queryAuthInfo(@PathVariable Long id) {
		DataBaseInfoModel info = databaseService.queryAuthInfoById(id);

		return new Resp<>(info);
	}
}
