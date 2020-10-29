package io.lizardframework.data.admin.controller.operator.resources;

import io.lizardframework.data.admin.commons.PageableResp;
import io.lizardframework.data.admin.commons.Resp;
import io.lizardframework.data.admin.controller.operator.resources.params.DataBaseAddParam;
import io.lizardframework.data.admin.controller.operator.resources.params.DataBaseListParam;
import io.lizardframework.data.admin.model.DataBaseInfoModel;
import io.lizardframework.data.admin.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xueqi
 * @date 2020-10-16
 */
@RestController
@RequestMapping("/operator/resources/database")
public class DatabaseOperApiController {


	@Autowired
	private DatabaseService databaseService;

	/**
	 * list query
	 *
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public PageableResp<List<DataBaseInfoModel>> list(DataBaseListParam param) {
		PageableResp<List<DataBaseInfoModel>> data = databaseService.queryPage(param);
		return data;
	}

	/**
	 * add database
	 *
	 * @param param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public Resp add(@RequestBody DataBaseAddParam param) {
		databaseService.save(param);
		return new Resp();
	}

	/**
	 * query by id
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public Resp<DataBaseInfoModel> queryBasicInfo(@PathVariable Long id) {
		DataBaseInfoModel info = databaseService.queryBasicById(id);
		return new Resp<>(info);
	}

	/**
	 * query auth info by id
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "authinfo/{id}", method = RequestMethod.GET)
	public Resp<DataBaseInfoModel> queryAuthInfo(@PathVariable Long id) {
		DataBaseInfoModel info = databaseService.queryAuthInfoById(id);

		return new Resp<>(info);
	}
}
