package io.lizardframework.data.admin.controller.operator.application.params;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xueqi
 * @date 2020-10-20
 */
@Data
public class OrmMixedListParam {

	private String mixedName;

	private Integer pageIndex = 1;
	private Integer pageSize  = 10;

	public Map<String, Object> toMapper() {
		Map<String, Object> params = new HashMap<>();
		if (StringUtils.isNotEmpty(mixedName))
			params.put("mixedName", "%" + mixedName + "%");

		return params;
	}

	public PageRequest toPageRequest() {
		PageRequest pageable = PageRequest.of(pageIndex - 1, pageSize);
		return pageable;
	}
}
