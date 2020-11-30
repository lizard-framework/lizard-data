package io.lizardframework.data.admin.controller.api;

import io.lizardframework.data.admin.commons.Resp;
import io.lizardframework.data.admin.controller.api.params.ORMGetMixedConfigParams;
import io.lizardframework.data.admin.service.mixed.MixedDataSourceService;
import io.lizardframework.data.orm.model.MixedDataSourceModel;
import io.lizardframework.data.utils.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据源/缓存 配置 OPEN API
 *
 * @author xueqi
 * @date 2020-11-18
 */
@RestController
@RequestMapping("/api/mixed/")
@Slf4j
public class MixedApiController {

	@Autowired
	private MixedDataSourceService mixedDataSourceService;

	/**
	 * 查询MixedDataSource配置
	 *
	 * @param params
	 * @return
	 */
	@GetMapping(value = "datasource/config", consumes = "application/json")
	public Resp<MixedDataSourceModel> datasourceConfig(@RequestBody ORMGetMixedConfigParams params) {
		log.info("[/api/mixed/datasource/config] request params:{}", JSONUtils.toJSONString(params));

		MixedDataSourceModel model = mixedDataSourceService.queryMixedDataSource(params);

		return new Resp<>(model);
	}
}
