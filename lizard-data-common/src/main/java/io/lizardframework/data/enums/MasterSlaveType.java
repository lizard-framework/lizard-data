package io.lizardframework.data.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author xueqi
 * @date 2020-09-23
 */
public enum MasterSlaveType {
	MASTER("master"),
	SLAVE("slave");

	private String value;

	MasterSlaveType(String value) {
		this.value = value;
	}

	public static MasterSlaveType convert(String value) {
		for (MasterSlaveType masterSlaveType : MasterSlaveType.values()) {
			if (StringUtils.equalsIgnoreCase(masterSlaveType.value, value))
				return masterSlaveType;
		}

		return null;
	}

	public String getValue() {
		return value;
	}
}
