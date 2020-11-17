package io.lizardframework.data.admin.controller.resourcesManager.application;

import io.lizardframework.data.admin.commons.Resp;
import io.lizardframework.data.admin.commons.pageable.PageResult;
import io.lizardframework.data.admin.commons.pageable.PageableResp;
import io.lizardframework.data.admin.controller.resourcesManager.application.params.ApplicationAddParam;
import io.lizardframework.data.admin.controller.resourcesManager.application.params.ApplicationListParam;
import io.lizardframework.data.admin.model.resources.ApplicationInfoModel;
import io.lizardframework.data.admin.service.resources.ApplicationResourceService;
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
	private ApplicationResourceService applicationResourceService;

	/**
	 * 查询应用列表
	 *
	 * @param param
	 * @return
	 */
	@GetMapping(value = "list")
	public PageableResp<List<ApplicationInfoModel>> list(ApplicationListParam param) {
		PageResult<ApplicationInfoModel>         result   = applicationResourceService.queryPage(param);
		PageableResp<List<ApplicationInfoModel>> response = new PageableResp<>(result.getCount(), result.getData());

		return response;
	}

	/**
	 * 添加应用
	 *
	 * @param param
	 * @return
	 */
	@PutMapping(value = "save")
	public Resp save(@RequestBody ApplicationAddParam param) {
		applicationResourceService.save(param);
		return new Resp();
	}
}
