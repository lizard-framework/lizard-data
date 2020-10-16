package io.lizardframework.data.admin.controller.operator;

import io.lizardframework.data.admin.commons.PageableResp;
import io.lizardframework.data.admin.controller.operator.params.DataBaseListParam;
import io.lizardframework.data.admin.model.DataBaseInfoModel;
import io.lizardframework.data.admin.service.DataBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xueqi
 * @date 2020-10-16
 */
@RestController
@RequestMapping("/operator/database")
public class DataBaseOperApiController {

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

}
