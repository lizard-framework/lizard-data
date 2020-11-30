package io.lizardframework.data.admin.controller.applicationConfig.datasource;

import io.lizardframework.data.admin.commons.Resp;
import io.lizardframework.data.admin.commons.pageable.PageResult;
import io.lizardframework.data.admin.commons.pageable.PageableResp;
import io.lizardframework.data.admin.controller.applicationConfig.datasource.params.MixedDataSourceListParam;
import io.lizardframework.data.admin.model.mixed.MixedDataSourceInfoModel;
import io.lizardframework.data.admin.service.mixed.MixedDataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	private MixedDataSourceService mixedDataSourceService;

	/**
	 * 查询应用配置列表
	 *
	 * @param param
	 * @return
	 */
	@GetMapping(value = "list")
	public PageableResp<List<MixedDataSourceInfoModel>> list(MixedDataSourceListParam param) {
		PageResult<MixedDataSourceInfoModel>         result   = mixedDataSourceService.queryPage(param);
		PageableResp<List<MixedDataSourceInfoModel>> response = new PageableResp<>(result.getCount(), result.getData());

		return response;
	}

	/**
	 * 保存基本配置信息到缓存中
	 *
	 * @return
	 */
	@PutMapping(value = "addition/basic-info")
	public Resp<Object> additionBasicConfig() {
		return null;
	}
}
