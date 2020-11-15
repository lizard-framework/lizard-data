package io.lizardframework.data.admin.controller.resourcesManager.application;

import io.lizardframework.data.admin.commons.pageable.PageableResp;
import io.lizardframework.data.admin.commons.Resp;
import io.lizardframework.data.admin.controller.resourcesManager.application.params.ApplicationAddParam;
import io.lizardframework.data.admin.controller.resourcesManager.application.params.ApplicationListParam;
import io.lizardframework.data.admin.model.ApplicationInfoModel;
import io.lizardframework.data.admin.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 资源管理 - 应用管理 api controller
 *
 * @author xueqi
 * @date 2020-11-07
 */
@RestController
@RequestMapping("/api/resources-manager/application")
public class ApplicationManagerApiController {

	@Autowired
	private ApplicationService applicationService;

	/**
	 * 查询应用列表
	 *
	 * @param param
	 * @return
	 */
	@GetMapping(value = "list")
	public PageableResp<List<ApplicationInfoModel>> list(ApplicationListParam param) {
		PageableResp<List<ApplicationInfoModel>> data = applicationService.queryPage(param);
		return data;
	}

	/**
	 * 添加应用
	 *
	 * @param param
	 * @return
	 */
	@PutMapping(value = "save")
	public Resp save(@RequestBody ApplicationAddParam param) {
		applicationService.save(param);
		return new Resp();
	}
}
