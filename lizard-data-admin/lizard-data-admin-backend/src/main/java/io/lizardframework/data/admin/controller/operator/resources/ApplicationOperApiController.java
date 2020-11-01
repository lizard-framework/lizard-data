package io.lizardframework.data.admin.controller.operator.resources;

import io.lizardframework.data.admin.commons.PageableResp;
import io.lizardframework.data.admin.commons.Resp;
import io.lizardframework.data.admin.controller.operator.resources.params.ApplicationAddParam;
import io.lizardframework.data.admin.controller.operator.resources.params.ApplicationListParam;
import io.lizardframework.data.admin.model.ApplicationInfoModel;
import io.lizardframework.data.admin.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xueqi
 * @date 2020-10-29
 */
@RestController
@RequestMapping("/operator/resources/application")
public class ApplicationOperApiController {

	@Autowired
	private ApplicationService applicationService;

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public PageableResp<List<ApplicationInfoModel>> list(ApplicationListParam param) {
		PageableResp<List<ApplicationInfoModel>> data = applicationService.queryPage(param);
		return data;
	}

	@PutMapping
	public Resp add(@RequestBody ApplicationAddParam param) {
		applicationService.save(param);
		return new Resp();
	}
}
