package io.lizardframework.data.orm.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * DataSource Pool Type
 *
 * @author xueqi
 * @date 2020-09-21
 */
public enum DataSourcePoolType {
	DRUID("druid"),
	HikariCP("HikariCP");

	private String value;

	DataSourcePoolType(String value) {
		this.value = value;
	}

	public static DataSourcePoolType convert(String value) {
		for (DataSourcePoolType type : DataSourcePoolType.values()) {
			if (StringUtils.equalsIgnoreCase(type.value, value))
				return type;
		}

		return null;
	}

	public String getValue() {
		return value;
	}
}
