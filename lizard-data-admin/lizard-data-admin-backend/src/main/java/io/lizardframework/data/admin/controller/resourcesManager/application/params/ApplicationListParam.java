package io.lizardframework.data.admin.controller.resourcesManager.application.params;

import io.lizardframework.data.admin.commons.PageableParam;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xueqi
 * @date 2020-10-29
 */
@Data
public class ApplicationListParam extends PageableParam {

	private String applicationName;

	public Map<String, Object> toMapper() {
		Map<String, Object> params = new HashMap<>();
		if (StringUtils.isNotEmpty(applicationName))
			params.put("applicationName", "%" + applicationName + "%");

		return params;
	}
}
