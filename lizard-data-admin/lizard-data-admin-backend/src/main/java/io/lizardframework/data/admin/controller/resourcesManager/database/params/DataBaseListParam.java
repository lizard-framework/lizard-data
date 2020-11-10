package io.lizardframework.data.admin.controller.resourcesManager.database.params;

import io.lizardframework.data.admin.commons.PageableParam;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xueqi
 * @date 2020-10-16
 */
@Data
public class DataBaseListParam extends PageableParam {

	private String dbType;
	private String dbName;

	public Map<String, Object> toMapper() {
		Map<String, Object> params = new HashMap<>();
		if (StringUtils.isNotEmpty(dbType))
			params.put("db_type", dbType);

		if (StringUtils.isNotEmpty(dbName))
			params.put("db_name", dbName);

		return params;
	}

}
