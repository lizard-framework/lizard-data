package io.lizardframework.data.admin.controller.resourcesManager.database.params;

import io.lizardframework.data.admin.commons.pageable.PageableParam;
import io.lizardframework.data.orm.enums.DBType;
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

	/**
	 * 数据库类型
	 *
	 * @see DBType#getValue()
	 */
	private String dbType;
	/**
	 * 数据库名称
	 */
	private String dbName;

	@Override
	public Map<String, Object> toMapper() {
		Map<String, Object> params = new HashMap<>();
		if (StringUtils.isNotEmpty(dbType))
			params.put("dbType", dbType);

		if (StringUtils.isNotEmpty(dbName))
			params.put("dbName", "%" + dbName + "%");

		return params;
	}

}
