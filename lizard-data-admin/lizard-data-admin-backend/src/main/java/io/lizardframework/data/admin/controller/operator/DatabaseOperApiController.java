package io.lizardframework.data.admin.controller.operator;

import io.lizardframework.data.admin.commons.PageableResp;
import io.lizardframework.data.admin.commons.Resp;
import io.lizardframework.data.admin.controller.operator.params.DataBaseAddParam;
import io.lizardframework.data.admin.controller.operator.params.DataBaseListParam;
import io.lizardframework.data.admin.model.DataBaseInfoModel;
import io.lizardframework.data.admin.service.DataBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author xueqi
 * @date 2020-10-16
 */
@RestController
@RequestMapping("/operator/database")
public class DatabaseOperApiController {


	@Autowired
	private DataBaseService dataBaseService;

	/**
	 * list query
	 *
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public PageableResp<List<DataBaseInfoModel>> list(DataBaseListParam param) {
		PageableResp<List<DataBaseInfoModel>> data = dataBaseService.queryPage(param);
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
		dataBaseService.save(param);
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
		DataBaseInfoModel info = dataBaseService.queryBasicById(id);
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
		DataBaseInfoModel info = dataBaseService.queryAuthInfoById(id);

		return new Resp<>(info);
	}
}
