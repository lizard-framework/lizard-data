package io.lizardframework.data.admin.controller.applicationConfig.datasource;

import io.lizardframework.data.admin.commons.pageable.PageableResp;
import io.lizardframework.data.admin.controller.applicationConfig.datasource.params.OrmMixedListParam;
import io.lizardframework.data.admin.model.OrmMixedInfoModel;
import io.lizardframework.data.admin.service.OrmMixedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 应用配置 - 数据源配置 api controller
 *
 * @author xueqi
 * @date 2020-11-10
 */
@RestController
@RequestMapping("/api/application-config/datasource")
public class DataSourceManagerApiController {

	@Autowired
	private OrmMixedService ormMixedService;

	/**
	 * 查询应用配置列表
	 *
	 * @param param
	 * @return
	 */
	@GetMapping(value = "list")
	public PageableResp<List<OrmMixedInfoModel>> list(OrmMixedListParam param) {
		PageableResp<List<OrmMixedInfoModel>> resp = ormMixedService.queryPage(param);

		return resp;
	}
}
