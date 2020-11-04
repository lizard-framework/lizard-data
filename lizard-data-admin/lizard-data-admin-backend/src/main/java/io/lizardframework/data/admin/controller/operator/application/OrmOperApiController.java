package io.lizardframework.data.admin.controller.operator.application;

import io.lizardframework.data.admin.commons.PageableResp;
import io.lizardframework.data.admin.commons.Resp;
import io.lizardframework.data.admin.controller.operator.application.params.OrmMixedAddStep1Param;
import io.lizardframework.data.admin.controller.operator.application.params.OrmMixedListParam;
import io.lizardframework.data.admin.model.OrmMixedInfoModel;
import io.lizardframework.data.admin.service.OrmMixedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xueqi
 * @date 2020-10-19
 */
@RestController
@RequestMapping("/operator/application-config/orm")
public class OrmOperApiController {

	@Autowired
	private OrmMixedService ormMixedService;

	@GetMapping(value = "list")
	public PageableResp<List<OrmMixedInfoModel>> list(OrmMixedListParam param) {
		PageableResp<List<OrmMixedInfoModel>> resp = ormMixedService.queryPage(param);

		return resp;
	}

	/**
	 * 添加应用配置第一步提交
	 *
	 * @return
	 */
	@PutMapping(value = "add_step_1")
	public Resp addStepOne(@RequestBody OrmMixedAddStep1Param param) {
		return null;
	}
}
