package io.lizardframework.data.admin.controller.api;

import io.lizardframework.data.admin.service.mixed.MixedDataSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xueqi
 * @date 2020-11-18
 */
@RestController
@RequestMapping("/api/mixed/")
@Slf4j
public class MixedApiController {

	@Autowired
	private MixedDataSourceService mixedDataSourceService;
}
