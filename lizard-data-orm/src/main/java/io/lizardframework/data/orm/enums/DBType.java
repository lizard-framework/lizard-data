package io.lizardframework.data.orm.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * DBType Enum
 *
 * @author xueqi
 * @date 2020-09-07
 */
public enum DBType {
	MYSQL("mysql");

	private String value;

	DBType(String value) {
		this.value = value;
	}

	public static DBType convert(String value) {
		for (DBType dbType : DBType.values()) {
			if (StringUtils.equalsIgnoreCase(dbType.value, value))
				return dbType;
		}

		return null;
	}

	public String getValue() {
		return value;
	}
}
