package io.lizardframework.data.admin.controller.api;

import io.lizardframework.data.admin.commons.Resp;
import io.lizardframework.data.admin.controller.api.params.ORMGetMixedConfigParams;
import io.lizardframework.data.admin.service.mixed.MixedDataSourceService;
import io.lizardframework.data.orm.model.MixedDataSourceModel;
import io.lizardframework.data.utils.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xueqi
 * @date 2020-09-27
 */
@RestController
@RequestMapping("/api/orm/")
@Slf4j
public class ORMApiController {

	@Autowired
	private MixedDataSourceService mixedDataSourceService;

	/**
	 * get orm mixed config
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "getMixedDataSourceConfig", consumes = "application/json")
	public Resp<MixedDataSourceModel> getMixedConfig(@RequestBody ORMGetMixedConfigParams params) {
		log.info("[/api/orm/getMixedConfig] request params:{}", JSONUtils.toJSONString(params));

		MixedDataSourceModel model = mixedDataSourceService.queryMixedDataSource(params);

		return new Resp<>(model);
	}

}
