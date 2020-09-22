package io.lizardframework.data.orm.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * DBType Enum
 *
 * @author xueqi
 * @date 2020-09-07
 */
public enum DBType {
	MYSQL("mysql", "jdbc:mysql://", "com.mysql.cj.jdbc.Driver");

	private String value;
	private String jdbcPrefix;
	private String jdbcDriver;

	DBType(String value, String jdbcPrefix, String jdbcDriver) {
		this.value = value;
		this.jdbcPrefix = jdbcPrefix;
		this.jdbcDriver = jdbcDriver;
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

	public String getJdbcPrefix() {
		return jdbcPrefix;
	}

	public String getJdbcDriver() {
		return jdbcDriver;
	}
}
