package io.lizardframework.data.admin.controller.operator.application;

import io.lizardframework.data.admin.commons.PageableResp;
import io.lizardframework.data.admin.controller.operator.application.params.OrmMixedListParam;
import io.lizardframework.data.admin.model.OrmMixedInfoModel;
import io.lizardframework.data.admin.service.OrmMixedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public PageableResp<List<OrmMixedInfoModel>> list(OrmMixedListParam param) {
		PageableResp<List<OrmMixedInfoModel>> resp = ormMixedService.queryPage(param);

		return resp;
	}
}
