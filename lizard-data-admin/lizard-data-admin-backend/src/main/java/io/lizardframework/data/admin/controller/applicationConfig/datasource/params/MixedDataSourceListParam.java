package io.lizardframework.data.admin.controller.applicationConfig.datasource.params;

import io.lizardframework.data.admin.commons.pageable.PageableParam;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xueqi
 * @date 2020-10-20
 */
@Data
public class MixedDataSourceListParam extends PageableParam {

	/**
	 * 数据源名称
	 */
	private String mixedName;

	@Override
	public Map<String, Object> toMapper() {
		Map<String, Object> params = new HashMap<>();
		if (StringUtils.isNotEmpty(mixedName))
			params.put("mixedName", mixedName + "%");

		return params;
	}
}
