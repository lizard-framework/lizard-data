package io.lizardframework.data.admin.controller.operator.params;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xueqi
 * @date 2020-10-16
 */
@Data
public class DataBaseListParam {

	private String dbType;
	private String dbName;

	private Integer pageIndex = 1;
	private Integer pageSize  = 10;

	public Map<String, Object> toMapper() {
		Map<String, Object> params = new HashMap<>();
		if (StringUtils.isNotEmpty(dbType))
			params.put("db_type", dbType);

		if (StringUtils.isNotEmpty(dbName))
			params.put("db_name", dbName);

		return params;
	}

	public PageRequest toPageRequest() {
		PageRequest pageable = PageRequest.of(pageIndex - 1, pageSize);
		return pageable;
	}
}
